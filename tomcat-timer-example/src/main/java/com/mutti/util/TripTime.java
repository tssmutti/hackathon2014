package com.mutti.util;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * This objects encapsulates departure or arrival time of a trip.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripTime implements Serializable {

    private static final long serialVersionUID = 4628516846285928026L;

    private Calendar time;

    private boolean departure;

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public boolean isDeparture() {
        return departure;
    }

    public void setDeparture(boolean departure) {
        this.departure = departure;
    }

    public boolean equals(Object o) {
        if (!(o instanceof TripTime)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        TripTime tripTime = (TripTime) o;
        return new EqualsBuilder().append(time, tripTime.time).append(departure, tripTime.departure).isEquals();
    }

}
