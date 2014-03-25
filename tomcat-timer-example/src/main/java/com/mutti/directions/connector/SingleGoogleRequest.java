package com.mutti.directions.connector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.daimler.moovel.domain.TravelMode;
import com.mutti.directions.datatypes.GoogleLocation;


/**
 * A class wrapping a request to Google Geocoding.
 *
 * @author Christian Bergs
 */
public class SingleGoogleRequest {

    /** The origin. */
    private GoogleLocation origin;
    
    /** The destination. */
    private GoogleLocation destination;
    
    /** The way points. */
    private List<GoogleLocation> wayPoints = new ArrayList<GoogleLocation>();
    
    /** The travel mode. */
    private TravelMode travelMode;
    
    /** The trip time. */
    private Calendar tripTime;
    
    /** The is departure. */
    private boolean isDeparture;

    /**
     * Constructor.
     *
     * @param origin            The origin
     * @param destination            The Destination
     * @param wayPoints            The way points
     * @param travelMode            The travel mode
     * @param provideAlternatives            Flag if alternatives should be provided
     * @param isDeparture            if triptime is departure [true] or arrival [false] time
     * @param tripTime            time for the trip
     */
    public SingleGoogleRequest(GoogleLocation origin, GoogleLocation destination, List<GoogleLocation> wayPoints,
            TravelMode travelMode, boolean provideAlternatives, boolean isDeparture, Calendar tripTime) {

        this.origin = origin;
        this.destination = destination;
        this.wayPoints = wayPoints;
        this.travelMode = travelMode;
        this.isDeparture = isDeparture;
        this.tripTime = tripTime;
    }

    /**
     * Returns the field origin.
     * 
     * @return The origin
     */
    public GoogleLocation getOrigin() {
        return origin;
    }

    /**
     * Returns the field origin.
     * 
     * @return The destination
     */
    public GoogleLocation getDestination() {
        return destination;
    }

    /**
     * Returns the field wayPoints.
     * 
     * @return The way points
     */
    public List<GoogleLocation> getWayPoints() {
        return Collections.unmodifiableList(wayPoints);
    }

    /**
     * Returns the field travelMode.
     * 
     * @return The travel mode
     */
    public TravelMode getTravelMode() {
        return travelMode;
    }

    /**
     * Returns the field tripTime.
     * 
     * @return The tripTime
     */
    public Calendar getTripTime() {
        return tripTime;
    }

    /**
     * Returns the field isDeparture.
     * 
     * @return isDeparture
     */
    public boolean isDeparture() {
        return isDeparture;
    }
}
