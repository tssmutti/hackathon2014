package com.mutti.directions.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import com.daimler.moovel.domain.Agency;
import com.daimler.moovel.domain.Costs;
import com.daimler.moovel.domain.DistanceCost;
import com.daimler.moovel.domain.DurationCost;
import com.daimler.moovel.domain.Leg;
import com.daimler.moovel.domain.Line;
import com.daimler.moovel.domain.RouteTime;
import com.daimler.moovel.domain.TransportMode;
import com.daimler.moovel.domain.TravelMode;
import com.daimler.moovel.domain.geo.GeoPoint;
import com.daimler.moovel.domain.geo.Place;
import com.daimler.moovel.domain.routing.api.RouteRequest;
import com.daimler.moovel.domain.vehicle.Vehicle;
import com.daimler.moovel.domain.vehicle.VehicleType;
import com.mutti.directions.datatypes.GoogleLeg;
import com.mutti.directions.datatypes.GoogleLine;
import com.mutti.directions.datatypes.GoogleLocation;
import com.mutti.directions.datatypes.GoogleRoute;
import com.mutti.directions.datatypes.GoogleStep;
import com.mutti.directions.datatypes.GoogleTime;
import com.mutti.directions.datatypes.GoogleTransitDetails;
import com.mutti.directions.datatypes.GoogleVehicle;


/**
 * Converter for Google Directions Result with {@link TransportMode} TRANSIT Therefore {@link GoogleStep}s must be
 * mapped to {@link Leg}s because they are a short travel with one vehicle like Walking from A to B, then Transit from B
 * to C etc. That needs to be put into legs.
 * 
 * @author ANDADA
 */
public final class GoogleDirectionsLegTransitMapper {

    /**
     * Instantiates a new google directions leg transit mapper.
     */
    private GoogleDirectionsLegTransitMapper() {
        // empty block
    }

    /**
     * Converts the {@link GoogleStep}s of a {@link GoogleRoute} into {@link Leg}s.
     *
     * @param googleRoute            the route where the legs are taken from
     * @param routeRequest            the original request to the API
     * @param vehicleMappingService            The service used for mappings of msp vehicle types to internal vehicle types
     * @return a List of Legs
     */
    public static List<Leg> convertGoogleStepsToLegs(GoogleRoute googleRoute, RouteRequest routeRequest) {
        List<Leg> legs = new ArrayList<Leg>();
        List<GoogleLeg> googleLegs = googleRoute.getLegs();
        if (googleLegs != null) {
            // TODO note: only one googleLeg is returned when no way points used
            // using way points needs to add the googleLegs
            GoogleLeg googleLeg = googleLegs.get(0);
            List<GoogleStep> googleSteps = googleLeg.getSteps();
            for (GoogleStep googleStep : googleSteps) {
                Leg previousLeg = null;
                if (legs.size() > 0) {
                    previousLeg = legs.get(legs.size() - 1);
                }
                Leg leg = convertGoogleStepToLeg(googleLeg, previousLeg, googleStep, routeRequest);
                if (leg != null) {
                    legs.add(leg);
                }
            }
        }
        return legs;
    }

    /**
     * Convert google step to leg.
     *
     * @param googleLeg the google leg
     * @param previousLeg the previous leg
     * @param googleStep the google step
     * @param routeRequest the route request
     * @param vehicleMappingService the vehicle mapping service
     * @return the leg
     */
    private static Leg convertGoogleStepToLeg(GoogleLeg googleLeg, Leg previousLeg, GoogleStep googleStep,
            RouteRequest routeRequest) {
        Leg leg = new Leg();
        leg.setStart(convertToPlace(googleStep.getStart_location()));
        leg.setEnd(convertToPlace(googleStep.getEnd_location()));
        // leg.setSteps(convertGoogleSubStepsToSteps(googleStep));
        setTimes(routeRequest.getRouteTime(), previousLeg, googleLeg, googleStep, leg);
        setCosts(googleStep, leg);
        TravelMode travelMode = googleStep.getTravel_mode();
        leg.setMode(travelMode);
        if (travelMode != null && travelMode.equals(TravelMode.TRANSIT)) {
            setLineAndVehicle(googleStep, leg);
        } else if (travelMode.equals(TravelMode.WALKING)) {
            leg.setVehicle(new Vehicle.Builder().vehicleType(VehicleType.FOOT).id("").build());
        }
        return leg;
    }

    /**
     * Sets the line and vehicle.
     *
     * @param googleStep the google step
     * @param leg the leg
     * @param vehicleMappingService the vehicle mapping service
     */
    private static void setLineAndVehicle(GoogleStep googleStep, Leg leg) {
        GoogleTransitDetails transitDetails = googleStep.getTransitDetails();
        GoogleLine googleLine = transitDetails.getLine();
        Line line = new Line();
        List<Agency> agencies = googleLine.getAgencies();
        if (agencies != null && agencies.size() > 0) {
            Agency agency = agencies.get(0);
            line.setAgency(agency);
            line.setName(googleLine.getName());
        }
        line.setName(googleLine.getName());

        GoogleVehicle googleVehicle = googleLine.getVehicle();
        if (googleVehicle != null) {
            if (googleVehicle.getType() != null) {
                Vehicle.Builder vehicleBuilder = new Vehicle.Builder();
                vehicleBuilder.id(googleVehicle.getName());
                vehicleBuilder.vehicleType(VehicleType.SUBWAY);
                Agency agency = line.getAgency();
                if (agency != null) {
                    vehicleBuilder.vehicleDetailsProviderId(AgencyToProviderIdMapper.getProviderIdByAgency(agency
                            .getName()));
                }
                Vehicle vehicle = vehicleBuilder.build();
                line.setVehicle(vehicle);
                leg.setVehicle(vehicle);
            }
        }
        leg.setLine(line);
    }

    /**
     * Sets the costs.
     *
     * @param googleStep the google step
     * @param leg the leg
     */
    private static void setCosts(GoogleStep googleStep, Leg leg) {
        // only duration and distance available
        Costs costs = new Costs();
        costs.addCost(new DistanceCost(googleStep.getDistance().getValue()));
        costs.addCost(new DurationCost(googleStep.getDuration().getValue()));
        leg.setCosts(costs);
    }

    /**
     * Sets the times.
     *
     * @param routeTime the route time
     * @param previousLeg the previous leg
     * @param googleLeg the google leg
     * @param googleStep the google step
     * @param leg the leg
     */
    private static void setTimes(RouteTime routeTime, Leg previousLeg, GoogleLeg googleLeg, GoogleStep googleStep,
            Leg leg) {
        Calendar arrivalTime;
        Calendar departureTime;
        GoogleTransitDetails googleTransitDetails = googleStep.getTransitDetails();
        if (googleTransitDetails != null) {
            calculateTimesFromTransitDetails(leg, googleTransitDetails);
        } else {
            // otherwise calculate times from duration and previous leg
            departureTime = getDepartureTimeForLeg(previousLeg, routeTime, googleLeg);
            leg.setDepartureTime(departureTime);
            int legDuration = (int) googleStep.getDuration().getValue();
            arrivalTime = DateUtils.toCalendar(DateUtils.addSeconds(departureTime.getTime(), legDuration));
            leg.setArrivalTime(arrivalTime);
        }
        leg.setScheduledArrivalTime(leg.getArrivalTime());
        leg.setScheduledDepartureTime(leg.getDepartureTime());
    }

    /**
     * if GoogleStep is a transit then the available times can be used.
     *
     * @param leg            the leg to edit
     * @param googleTransitDetails            the google Transit Details
     */
    private static void calculateTimesFromTransitDetails(Leg leg, GoogleTransitDetails googleTransitDetails) {
        GoogleTime arrival = googleTransitDetails.getArrival_time();
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(arrival.getValue().getTimeInMillis());
        leg.setArrivalTime(date);

        GoogleTime departure = googleTransitDetails.getDeparture_time();
        date = Calendar.getInstance();
        date.setTimeInMillis(departure.getValue().getTimeInMillis());
        leg.setDepartureTime(date);
    }

    /**
     * Gets the departure time for leg.
     *
     * @param previousLeg the previous leg
     * @param routeTime the route time
     * @param googleLeg the google leg
     * @return the departure time for leg
     */
    private static Calendar getDepartureTimeForLeg(Leg previousLeg, RouteTime routeTime, GoogleLeg googleLeg) {
        Calendar departureTime = null;
        if (previousLeg == null) {
            // first leg needs to retrieve Google Leg departure time
            departureTime = Calendar.getInstance();
            GoogleTime googleDepartureTime = googleLeg.getDeparture_time();
            if (googleDepartureTime != null) {
                departureTime.setTimeInMillis(googleDepartureTime.getValue().getTimeInMillis());
            } else {
                // if not available in first Leg (e.g. only walking), it must be calculated through routeTime
                departureTime = routeTime.getTime();
                if (routeTime.isArrival()) {
                    int duration = (int) googleLeg.getDuration().getValue();
                    departureTime = DateUtils.toCalendar(DateUtils.addSeconds(departureTime.getTime(), -duration));
                }
            }

        } else {
            departureTime = previousLeg.getArrivalTime();
        }
        return departureTime;
    }

    /**
     * Convert to place.
     *
     * @param location the location
     * @return the place
     */
    private static Place convertToPlace(GoogleLocation location) {
        Place place = new Place();
        place.setGeoPoint(new GeoPoint(location.getLat(), location.getLng()));
        return place;
    }

}
