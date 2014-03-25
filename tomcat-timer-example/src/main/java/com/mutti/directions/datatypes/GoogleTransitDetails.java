package com.mutti.directions.datatypes;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * A mapper for Transit details from Google.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleTransitDetails {

    /** The line. */
    private GoogleLine line;

    /** The arrival time. */
    @JsonProperty(value = "arrival_time")
    private GoogleTime arrival_time;

    /** The departure time. */
    @JsonProperty(value = "departure_time")
    private GoogleTime departure_time;

    public synchronized GoogleLine getLine() {
        return line;
    }

    public synchronized void setLine(GoogleLine line) {
        this.line = line;
    }

    public synchronized GoogleTime getArrival_time() {
        return arrival_time;
    }

    public synchronized void setArrival_time(GoogleTime arrival_time) {
        this.arrival_time = arrival_time;
    }

    public synchronized GoogleTime getDeparture_time() {
        return departure_time;
    }

    public synchronized void setDeparture_time(GoogleTime departure_time) {
        this.departure_time = departure_time;
    }


}
