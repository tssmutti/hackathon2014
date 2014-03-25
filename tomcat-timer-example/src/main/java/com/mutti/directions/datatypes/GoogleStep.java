/*
 * Copyright © 2012 Daimler TSS. All Rights Reserved.
 *
 * Reproduction or transmission in whole or in part, in any form or by any
 * means, is prohibited without the prior written consent of the copyright
 * owner.
 * 
 * Created on: 19.07.2012
 * Created by: landsbs
 * Last modified on: $Date: 2008/10/01 09:06:27CEST $
 * Last modified by: $Author: Hardt, Dennis (dhardt7) landsbs $
 */
package com.mutti.directions.datatypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.daimler.moovel.domain.TravelMode;


/**
 * A mapper class for a step from Google Geocoding.
 *
 * @author ANDADA
 * @author Anatoli Trockmann
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleStep {

    /** The duration. */
    private GoogleDuration duration;
    
    /** The distance. */
    private GoogleDistance distance;

    /** The start location. */
    @JsonProperty(value = "start_location")
    private GoogleLocation start_location;

    /** The end location. */
    @JsonProperty(value = "end_location")
    private GoogleLocation end_location;

    /** The transit details. */
    @JsonProperty(value = "transit_details")
    private GoogleTransitDetails transit_details;

    /** The travel mode. */
    @JsonProperty(value = "travel_mode")
    private TravelMode travel_mode;
    
    /** The polyline. */
    private GooglePolyline polyline;
    
    /** The steps. */
    private List<GoogleStep> steps = new ArrayList<GoogleStep>();

   

    public synchronized GoogleDuration getDuration() {
        return duration;
    }

    public synchronized void setDuration(GoogleDuration duration) {
        this.duration = duration;
    }

    public synchronized GoogleDistance getDistance() {
        return distance;
    }

    public synchronized void setDistance(GoogleDistance distance) {
        this.distance = distance;
    }

    public synchronized GoogleLocation getStart_location() {
        return start_location;
    }

    public synchronized void setStart_location(GoogleLocation start_location) {
        this.start_location = start_location;
    }

    public synchronized GoogleLocation getEnd_location() {
        return end_location;
    }

    public synchronized void setEnd_location(GoogleLocation end_location) {
        this.end_location = end_location;
    }

    public synchronized GoogleTransitDetails getTransit_details() {
        return transit_details;
    }

    public synchronized void setTransit_details(GoogleTransitDetails transit_details) {
        this.transit_details = transit_details;
    }

    public synchronized TravelMode getTravel_mode() {
        return travel_mode;
    }

    public synchronized void setTravel_mode(TravelMode travel_mode) {
        this.travel_mode = travel_mode;
    }

    public synchronized GooglePolyline getPolyline() {
        return polyline;
    }

    public synchronized void setPolyline(GooglePolyline polyline) {
        this.polyline = polyline;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("startLocation", start_location)
                .append("endLocation", end_location).append("transitDetails", getTransitDetails()).toString();
    }

    /**
     * Gets the transit details.
     *
     * @return the transit details
     */
    public GoogleTransitDetails getTransitDetails() {
        return transit_details;
    }

    /**
     * Sets the transit details.
     *
     * @param transitDetails the new transit details
     */
    public void setTransitDetails(GoogleTransitDetails transitDetails) {
        this.transit_details = transitDetails;
    }

    /**
     * Gets the steps.
     *
     * @return the steps
     */
    public List<GoogleStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    /**
     * Sets the steps.
     *
     * @param steps the new steps
     */
    public void setSteps(List<GoogleStep> steps) {
        this.steps.clear();
        this.steps.addAll(steps);
    }

}
