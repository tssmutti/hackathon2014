package com.mutti.directions.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daimler.moovel.domain.Agency;
import com.daimler.moovel.domain.CostType;
import com.daimler.moovel.domain.DurationCost;
import com.daimler.moovel.domain.Leg;
import com.daimler.moovel.domain.Line;
import com.daimler.moovel.domain.Route;
import com.daimler.moovel.domain.RoutePoint;
import com.daimler.moovel.domain.TravelMode;
import com.daimler.moovel.domain.geo.GeoRoutePoint;
import com.daimler.moovel.domain.routing.api.RouteFeedback;
import com.daimler.moovel.domain.routing.api.RouteRequest;
import com.daimler.moovel.domain.routing.api.RouteResponse;
import com.mutti.directions.datatypes.GoogleLocation;
import com.mutti.directions.datatypes.GoogleRoute;
import com.mutti.directions.datatypes.GoogleRouteWrapper;


/**
 * Converts google directions specific types to moovel domain model.
 * 
 * @author Anatoli Trockmann
 */
public final class GoogleDirectionsMapper {

    /** The Constant MAX_WAYPOINTS. */
    private static final int MAX_WAYPOINTS = 31;

    /**
     * Instantiates a new google directions mapper.
     */
    private GoogleDirectionsMapper() {
        // nothing to do
    }

    /**
     * Converts a {@link GeoRoutePoint} to a {@link GoogleLocation}.
     * 
     * @param point
     *            the point to convert
     * @return returns a {@link GoogleLocation}
     */
    public static GoogleLocation convertToGoogleLocation(GeoRoutePoint point) {
        GoogleLocation location = new GoogleLocation();
        if (point.getGeoPoint() != null) {
            location.setLat(point.getGeoPoint().getLat());
            location.setLatitude(String.valueOf(point.getGeoPoint().getLat()));
            location.setLng(point.getGeoPoint().getLng());
            location.setLongitude(String.valueOf(point.getGeoPoint().getLng()));
        }
        return location;
    }

    /**
     * Converts a {@link GeoRoutePoint}s to a {@link GoogleLocation}.
     * 
     * @param routePoints
     *            points to convert
     * @return returns a {@link GoogleLocation}
     */
    public static List<GoogleLocation> convertToGoogleLocations(List<RoutePoint> routePoints) {
        List<GoogleLocation> googleLocations = new ArrayList<GoogleLocation>();
        for (RoutePoint wayPoint : routePoints) {
            googleLocations.add(convertToGoogleLocation((GeoRoutePoint) wayPoint));
        }
        return googleLocations;
    }

    /**
     * Converts a {@link TravelMode}s to a string representation.
     * 
     * @param travelMode
     *            {@link TravelMode} to convert
     * @return returns string representation of the given transportMode
     */
    public static String convertToGoogleTravelMode(TravelMode travelMode) {
        return travelMode.getTravelModeAsString();
    }

    /**
     * Converts a {@link GoogleRoute}s to a {@link RouteResponse}.
     * 
     * @param googleRoutes
     *            the list with {@link RouteResponse}
     * @param routeRequest
     *            the original {@link RouteRequest}
     * @param vehicleMappingService
     *            The service used for mappings of msp vehicle types to internal vehicle types
     * @return returns {@link RouteResponse}
     */
    public static RouteResponse convertToRouteResponse(List<GoogleRouteWrapper> googleRoutes,
            RouteRequest routeRequest) {
        RouteResponse routeResponse = new RouteResponse();
        routeResponse.setRoutes(convertToRoutes(googleRoutes, routeRequest));
        Map<RouteFeedback, String> feedback = new HashMap<RouteFeedback, String>();
        List<RoutePoint> waypoints = routeRequest.getWayPoints();
        if (waypoints != null) {
            // TODO Aus XML WAY_POINT_MAX einlesen
            if (waypoints.size() > MAX_WAYPOINTS) {
                feedback.put(RouteFeedback.WAYPOINT_LIMIT_EXCEEDED, new String());
            }
        }
        routeResponse.setFeedback(feedback);
        return routeResponse;
    }

    /**
     * Converts a {@link GoogleRoute}s to a {@link Route}s.
     * 
     * @param googleRoutes
     *            the list with {@link RouteResponse}
     * @param routeRequest
     *            the original {@link RouteRequest}
     * @param vehicleMappingService
     *            The service used for mappings of msp vehicle types to internal vehicle types
     * @return returns {@link RouteResponse}
     */
    public static List<Route> convertToRoutes(List<GoogleRouteWrapper> googleRoutes, RouteRequest routeRequest) {
        List<Route> routes = new ArrayList<Route>();
        for (GoogleRouteWrapper googleRoute : googleRoutes) {
            Route route = convertToRoute(googleRoute, routeRequest);
            if (route != null) {
                routes.add(route);
            }
        }
        return routes;
    }

    /**
     * Convert to route.
     * 
     * @param wrappedGoogleRoute
     *            the wrapped google route
     * @param routeRequest
     *            the route request
     * @param vehicleMappingService
     *            the vehicle mapping service
     * @return the route
     */
    private static Route convertToRoute(GoogleRouteWrapper wrappedGoogleRoute, RouteRequest routeRequest) {
        Route route = new Route();
        // dispatch mapping depending on travel mode
        if (routeRequest.getTravelMode().equals(TravelMode.TRANSIT)) {
            route.setLegs(GoogleDirectionsLegTransitMapper.convertGoogleStepsToLegs(
                    wrappedGoogleRoute.getGoogleRoute(), routeRequest));
        } else {
            route.setLegs(GoogleDirectionsLegStandardMapper.convertGoogleLegsToLegs(
                    wrappedGoogleRoute.getGoogleRoute(), routeRequest));
        }
        route.setProviderId(getProviderIdForRoute(routeRequest, route.getLegs()));
        route.setOrigin(wrappedGoogleRoute.getOrigin());
        route.setDestination(wrappedGoogleRoute.getDestination());
        long totalDuration = calculateTotalDuration(route.getLegs());
        route.getCosts().addCost(new DurationCost(totalDuration ));
        return route;
    }

    private static long calculateTotalDuration(List<Leg> legs) {
        long duration = 0;
        for (Leg leg : legs) {
            duration += leg.getCosts().getCostsOfType(CostType.DURATION).getValueAsLong();
        }
        return duration;
    }

    /**
     * Gets the provider id for route.
     * 
     * @param routeRequest
     *            the route request
     * @param legs
     *            the legs
     * @return the provider id for route
     */
    private static String getProviderIdForRoute(RouteRequest routeRequest, List<Leg> legs) {
        String providerId = new String();
        if (routeRequest.getTravelMode().equals(TravelMode.DRIVING)
                || routeRequest.getTravelMode().equals(TravelMode.BICYCLING)) {
            providerId = routeRequest.getProviderId();
        } else if (routeRequest.getTravelMode().equals(TravelMode.TRANSIT)) {
            for (Leg leg : legs) {
                Line line = leg.getLine();
                if (line != null && line.getAgency() != null && line.getAgency().getName() != null) {
                    Agency agency = line.getAgency();
                    String name = agency.getName();
                    providerId = AgencyToProviderIdMapper.getProviderIdByAgency(name);
                    break;
                }
            }
        }
        return providerId;
    }
}
