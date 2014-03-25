package com.mutti.directions.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.daimler.moovel.domain.Costs;
import com.daimler.moovel.domain.DistanceCost;
import com.daimler.moovel.domain.DurationCost;
import com.daimler.moovel.domain.Leg;
import com.daimler.moovel.domain.RouteTime;
import com.daimler.moovel.domain.geo.GeoPoint;
import com.daimler.moovel.domain.geo.Place;
import com.daimler.moovel.domain.routing.api.RouteRequest;
import com.daimler.moovel.domain.vehicle.Vehicle;
import com.daimler.moovel.domain.vehicle.VehicleType;
import com.mutti.directions.datatypes.GoogleLeg;
import com.mutti.directions.datatypes.GoogleLocation;
import com.mutti.directions.datatypes.GoogleRoute;

/**
 * A class mapping GoogleLegs into Legs of the Domain model.
 * 
 * @author ANDADA
 * @author Anatoli Trockmann
 */
public final class GoogleDirectionsLegStandardMapper {

    /**
     * Instantiates a new google directions leg standard mapper.
     */
    private GoogleDirectionsLegStandardMapper() {
        // empty block
    }

    /**
     * Converts the legs of a {@link GoogleRoute} into {@link Leg}s.
     * 
     * @param googleRoute
     *            the route where the legs are taken from
     * @param routeRequest
     *            the original request to the API
     * @return a List of Legs
     */
    public static List<Leg> convertGoogleLegsToLegs(GoogleRoute googleRoute, RouteRequest routeRequest) {
        List<GoogleLeg> googleLegs = googleRoute.getLegs();
        List<Leg> legs = new ArrayList<Leg>();
        for (GoogleLeg googleLeg : googleLegs) {
            Leg leg = convertToLeg(googleRoute, googleLeg, routeRequest);
            if (leg != null) {
                legs.add(leg);
            }
        }
        return legs;
    }

    /**
     * Convert to leg.
     * 
     * @param googleRoute
     *            the google route
     * @param googleLeg
     *            the google leg
     * @param routeRequest
     *            the route request
     * @return the leg
     */
    private static Leg convertToLeg(GoogleRoute googleRoute, GoogleLeg googleLeg, RouteRequest routeRequest) {
        Leg leg = new Leg();
        leg.setStart(convertToPlace(googleLeg.getStart_location(), googleLeg.getStart_address()));
        leg.setEnd(convertToPlace(googleLeg.getEnd_location(), googleLeg.getEnd_address()));
        // TODO not used yet
        // leg.setSteps(convertToSteps(googleLeg.getSteps()));
        setTimes(routeRequest.getRouteTime(), googleLeg, leg);
        setCosts(googleLeg, leg);
        leg.setMode(routeRequest.getTravelMode());
        setVehicle(leg, routeRequest);
        return leg;
    }

    /**
     * Sets the vehicle.
     * 
     * @param leg
     *            the leg
     * @param routeRequest
     *            the route request
     */
    private static void setVehicle(Leg leg, RouteRequest routeRequest) {

        Vehicle.Builder vehicleBuilder = new Vehicle.Builder();
        // TODO no id know at the moment
        switch (routeRequest.getTravelMode()) {
        case DRIVING:
            vehicleBuilder.vehicleType(VehicleType.CAR);
            break;
        case BICYCLING:
            vehicleBuilder.vehicleType(VehicleType.BIKE);
            break;
        case WALKING:
            vehicleBuilder.vehicleType(VehicleType.FOOT);
            break;
        default:
            break;
        }
        vehicleBuilder.id("");
        leg.setVehicle(vehicleBuilder.build());
    }

    /**
     * Sets the costs.
     * 
     * @param googleLeg
     *            the google leg
     * @param leg
     *            the leg
     */
    private static void setCosts(GoogleLeg googleLeg, Leg leg) {
        // only duration and distance available
        Costs costs = new Costs();
        costs.addCost(new DistanceCost(googleLeg.getDistance().getValue()));
        costs.addCost(new DurationCost(googleLeg.getDuration().getValue()));
        leg.setCosts(costs);
    }

    /**
     * Sets the times.
     * 
     * @param routeTime
     *            the route time
     * @param googleLeg
     *            the google leg
     * @param leg
     *            the leg
     */
    private static void setTimes(RouteTime routeTime, GoogleLeg googleLeg, Leg leg) {
        int duration = (int) googleLeg.getDuration().getValue();
        Calendar arrivalTime;
        Calendar departureTime;
        Calendar time = routeTime.getTime();
        if (routeTime.isArrival()) {
            leg.setArrivalTime(time);
            departureTime = DateUtils.toCalendar(DateUtils.addSeconds(time.getTime(), -duration));
            leg.setDepartureTime(departureTime);
        } else {
            leg.setDepartureTime(time);
            arrivalTime = DateUtils.toCalendar(DateUtils.addSeconds(time.getTime(), duration));
            leg.setArrivalTime(arrivalTime);
        }
        leg.setScheduledArrivalTime(leg.getArrivalTime());
        leg.setScheduledDepartureTime(leg.getDepartureTime());
    }

    /**
     * Convert to place.
     * 
     * @param location
     *            the location
     * @param addressAsString
     *            the address as string
     * @return the place
     */
    private static Place convertToPlace(GoogleLocation location, String addressAsString) {
        Place place = new Place();
        place.setGeoPoint(new GeoPoint(location.getLat(), location.getLng()));
        // TODO map postAddress
        // place.setPostalAddress();
        place.setName(addressAsString);
        return place;
    }
}
