package com.mutti.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

/**
 * Utilities to convert date and time values.
 */
public final class DateConverter implements Serializable {

    private static final long serialVersionUID = -2149533936332388588L;

    private DateConverter() {}

    /**
     * Converts a date string in ISO 8601 format to a {@link Calendar} instance.
     * 
     * @param value
     *            Date string in ISO 8601 format.
     * @return the {@link Calendar} instance.
     */
    public static Calendar parseDateTime(String value) {
        return DatatypeConverter.parseDateTime(value);
    }

    /**
     * Validates if the given dateTime String is in ISO 8601 format and can be parsed.
     * 
     * @param dateTime
     *            The value to validate:
     * @return true if is valid.
     */
    public static boolean isValidDateTime(String dateTime) {
        boolean result = true;
        try {
            if (dateTime != null) {
                DateConverter.parseDateTime(dateTime);
            } else {
                result = false;
            }
        } catch (IllegalArgumentException iae) {
            result = false;
        }

        return result;
    }

    /**
     * Converts a date into a string in ISO 8601 format.
     * 
     * @param date
     *            the date to convert
     * @return the string in ISO 8601 format
     */
    public static String convertToString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return DatatypeConverter.printDateTime(cal);
    }
}
