package com.mutti.directions.datatypes;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * A mapper class for data type distance from Google API.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDistance {

    // distance in meters
    /** The value. */
    private long value;

    /**
     * constructor.
     */
    public GoogleDistance() {
        super();
    }

    /**
     * constructor.
     *
     * @param value            the distance
     */
    public GoogleDistance(long value) {
        super();
        this.value = value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(long value) {
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public long getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("value", value).toString();
    }

}
