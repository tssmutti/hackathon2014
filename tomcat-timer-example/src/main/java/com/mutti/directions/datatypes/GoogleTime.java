/**
 * 
 */
package com.mutti.directions.datatypes;

import java.util.Calendar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.daimler.moovel.domain.JsonDateTimeSerializer;
import com.mutti.directions.mapper.JSONGoogleTripTimeDeserializer;


/**
 * this class represents the time of a google leg.
 *
 * @author TOBSTIX
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleTime {

    /** The time. */
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JSONGoogleTripTimeDeserializer.class)
    @JsonProperty(value = "value")
    private Calendar value;

    public synchronized Calendar getValue() {
        return value;
    }

    public synchronized void setValue(Calendar value) {
        this.value = value;
    }
    
    

}
