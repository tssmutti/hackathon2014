package com.mutti.directions.datatypes;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * A mapper class for the bounds data type from Google API.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBounds {

    /** The northeast. */
    private GoogleLocation northeast;
    
    /** The southwest. */
    private GoogleLocation southwest;

    /**
     * Gets the northeast.
     *
     * @return the northeast
     */
    public GoogleLocation getNortheast() {
        return northeast;
    }

    /**
     * Sets the northeast.
     *
     * @param northeast the new northeast
     */
    public void setNortheast(GoogleLocation northeast) {
        this.northeast = northeast;
    }

    /**
     * Gets the southwest.
     *
     * @return the southwest
     */
    public GoogleLocation getSouthwest() {
        return southwest;
    }

    /**
     * Sets the southwest.
     *
     * @param southwest the new southwest
     */
    public void setSouthwest(GoogleLocation southwest) {
        this.southwest = southwest;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("northeast", northeast)
                .append("southwest", southwest).toString();
    }

}
