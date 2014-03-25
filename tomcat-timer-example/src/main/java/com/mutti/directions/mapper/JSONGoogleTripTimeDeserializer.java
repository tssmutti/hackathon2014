/**
 * 
 */
package com.mutti.directions.mapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;


/**
 * this class is for deserializing google´s arrival & departure timestamps into {@link Calendar}. This deserializer is
 * necessary because google timestamps uses seconds and the instantiation of {@link Calendar} needs millis.
 * 
 * @author TOBSTIX
 */
public class JSONGoogleTripTimeDeserializer extends JsonDeserializer<Calendar> implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3665161926433838667L;

    /** The Constant MULTIPLICATOR_SEC_MILLIS. */
    private static final long MULTIPLICATOR_SEC_MILLIS = 1000L;


    /**
     * * for deserializing a timestamp in seconds into a {@link Calendar} object.
     * 
     * @param jsonParser
     *            the JsonParser
     * @param context
     *            the {@link DeserializationContext}
     * @return a {@link Calendar} object of the deserialized seconds
     * @throws IOException
     *             when an IO error occure
     * @throws JsonProcessingException
     *             when JSON Processing went wrong
     */
    @Override
    public Calendar deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException,
            JsonProcessingException {
        String jsonText = jsonParser.getText();
        Calendar calendar = null;
        try {
            int seconds = Integer.parseInt(jsonText);
            long millis = seconds * MULTIPLICATOR_SEC_MILLIS;
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
        } catch (NumberFormatException ex) {
           ex.printStackTrace();
        }
        return calendar;
    }
}
