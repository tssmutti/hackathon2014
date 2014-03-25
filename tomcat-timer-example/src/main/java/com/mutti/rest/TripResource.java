package com.mutti.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.mutti.google.connector.GoogleDirectionsConnector;
import com.mutti.google.connector.MuttiRoute;
import com.mutti.util.DateConverter;
import com.mutti.util.TripTime;

@Path("/trips")
public class TripResource {

    /**
     * Finds trips from the given start position to the given end position at the given departure time.
     * 
     * @param mode
     *            the vehicle chain to use for the trips. Possible values: FOOT_PUBLIC_TRANSPORT_FOOT (default),
     *            FOOT_CARSHARING_FOOT
     * @param start
     *            The start point as lat/lng pair separated by comma, e.g. 49.1234,10.9876
     * @param end
     *            The end point as lat/lng pair separated by comma, e.g. 49.1234,10.9876
     * @param startid
     *            The start point as id
     * @param endid
     *            The end point as id
     * @param departureTime
     *            The departure time in ISO 8601 format, e.g. 2013-06-10T16:50:00Z
     * @param arrivalTime
     *            The arrival time in ISO 8601 format, e.g. 2013-06-10T16:50:00Z. NOTE: departureTime and arrivalTime
     *            cannot be set at once.
     * @param carProfile
     *            the car profile. Possible values: [Case-sensitive] FASTEST (default), EASIEST, SHORTEST
     * @param pedestrianProfile
     *            the pedestrian profile. Possible values: [Case-sensitive] FASTEST (default), INDOOR, SHORTEST
     * @param publicTransportProfile
     *            the public transport profile. Possible values: [Case-sensitive] STANDARD (default), FOOT_SLOW,
     *            FOOT_FAST, WHEELCHAIR, BICYCLE, ROUTE_FARE
     * @param bikeProfile
     *            the bike profile. Possible values: [Case-sensitive] FASTEST (default), SHORTEST, SAFEST, OFFROAD
     * @param source
     *            The data source name. Possible values: GSHHS-crude, GSHHS-high, GSHHS-intermediate, GSHHS-low,
     *            GTFS-Austin, GTFS-Portland, GTFS-SanDiego, GTFS-Ulm, (default) GTFS-VBB, GTFS-Vancouver, Indoor, OSM,
     *            PGS
     * @return List of found trips.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MuttiRoute> findTrips(@QueryParam("mode") String mode, @QueryParam("start") String start,
            @QueryParam("startid") String startid, @QueryParam("end") String end, @QueryParam("endid") String endid,
            @QueryParam("departureTime") String departureTime, @QueryParam("arrivalTime") String arrivalTime) {

        GoogleDirectionsConnector googleDirectionsConnector = new GoogleDirectionsConnector();

        TripTime tripTime = new TripTime();
        if (!StringUtils.isEmpty(arrivalTime)) {
            tripTime.setTime(DateConverter.parseDateTime(arrivalTime));
        } else {
            tripTime.setTime(DateConverter.parseDateTime(departureTime));
            tripTime.setDeparture(true);
        }
        
        return googleDirectionsConnector.getGoogleRoutes(start, end, tripTime, mode);

    }

}
