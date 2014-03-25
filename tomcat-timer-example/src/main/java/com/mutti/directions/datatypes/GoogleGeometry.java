package com.mutti.directions.datatypes;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * A mapper class for data type geometry from Google API.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeometry {

    /** The bounds. */
    private GoogleBounds bounds;
    
    /** The location. */
    private GoogleLocation location;

    /** The location type. */
    @JsonProperty(value = "location_type")
    private String location_type;
    
    /** The viewport. */
    private GoogleBounds viewport;

    /**
     * Gets the bounds.
     *
     * @return the bounds
     */
    public GoogleBounds getBounds() {
        return bounds;
    }

    /**
     * Sets the bounds.
     *
     * @param bounds the new bounds
     */
    public void setBounds(GoogleBounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public GoogleLocation getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    /**
     * Gets the location type.
     *
     * @return the location type
     */
    public String getLocation_type() {
        return location_type;
    }

    /**
     * Sets the location type.
     *
     * @param theLocationType the new location type
     */
    public void setLocation_type(String theLocationType) {
        this.location_type = theLocationType;
    }

    /**
     * Gets the viewport.
     *
     * @return the viewport
     */
    public GoogleBounds getViewport() {
        return viewport;
    }

    /**
     * Sets the viewport.
     *
     * @param viewport the new viewport
     */
    public void setViewport(GoogleBounds viewport) {
        this.viewport = viewport;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("bounds", bounds)
                .append("location", location).append("locationType", location_type).append("viewport", viewport)
                .toString();
    }

}
