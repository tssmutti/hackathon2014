/*
 * Copyright © 2012 Daimler TSS. All Rights Reserved.
 *
 * Reproduction or transmission in whole or in part, in any form or by any
 * means, is prohibited without the prior written consent of the copyright
 * owner.
 * 
 * Created on: 24.01.2012
 * Created by: LANDSBS
 * Last modified on: $Date: 2008/10/01 09:06:27CEST $
 * Last modified by: $Author: Hardt, Dennis (dhardt7) LANDSBS $
 */
package com.mutti.directions.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * A class for the Google datatype leg from a routing response.
 * 
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleLeg {

    /** The duration. */
    private GoogleDuration duration;

    /** The distance. */
    private GoogleDistance distance;

    /** The start address. */
    @JsonProperty(value = "start_address")
    private String start_address;

    /** The end address. */
    @JsonProperty(value = "end_address")
    private String end_address;

    /** The start location. */
    @JsonProperty(value = "start_location")
    private GoogleLocation start_location;

    /** The end location. */
    @JsonProperty(value = "end_location")
    private GoogleLocation end_location;

    /** The arrival time. */
    @JsonProperty(value = "arrival_time")
    private GoogleTime arrival_time;

    /** The departure time. */
    @JsonProperty(value = "departure_time")
    private GoogleTime departure_time;

    /** The steps. */
    private List<GoogleStep> steps = new ArrayList<GoogleStep>();


    public GoogleDuration getDuration() {
        return duration;
    }


    public void setDuration(GoogleDuration duration) {
        this.duration = duration;
    }


    public GoogleDistance getDistance() {
        return distance;
    }


    public void setDistance(GoogleDistance distance) {
        this.distance = distance;
    }


    public String getStart_address() {
        return start_address;
    }


    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }


    public String getEnd_address() {
        return end_address;
    }


    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }


    public GoogleLocation getStart_location() {
        return start_location;
    }


    public void setStart_location(GoogleLocation start_location) {
        this.start_location = start_location;
    }


    public GoogleLocation getEnd_location() {
        return end_location;
    }


    public void setEnd_location(GoogleLocation end_location) {
        this.end_location = end_location;
    }


    public GoogleTime getArrival_time() {
        return arrival_time;
    }


    public void setArrival_time(GoogleTime arrival_time) {
        this.arrival_time = arrival_time;
    }


    public GoogleTime getDeparture_time() {
        return departure_time;
    }


    public void setDeparture_time(GoogleTime departure_time) {
        this.departure_time = departure_time;
    }


    public List<GoogleStep> getSteps() {
        return steps;
    }


    public void setSteps(List<GoogleStep> steps) {
        this.steps = steps;
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("duration", duration)
                .append("distance", distance).append("endAddress", end_address).append("startAddress", start_address)
                .append("startLocation", start_location).append("endLocation", end_location).append("steps", steps)
                .append("arrivalTime", arrival_time).append("departureTime", departure_time).toString();
    }

}
