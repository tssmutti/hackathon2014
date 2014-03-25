package com.mutti.directions.datatypes;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * A mapper class for the location data type from Google.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleLocation {

    /** The lat. */
    private double lat;
    
    /** The lng. */
    private double lng;

    /**
     * standard constructor.
     */
    public GoogleLocation() {
        // empty block
    }

    /**
     * constructor.
     *
     * @param latitude            latitude
     * @param longitude            longitude
     */
    public GoogleLocation(double latitude, double longitude) {
        this.lat = latitude;
        this.lng = longitude;
    }

    /**
     * Gets the lat.
     *
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets the lat.
     *
     * @param lat the new lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Sets the latitude.
     *
     * @param latitude the new latitude
     */
    public void setLatitude(String latitude) {
        this.lat = Double.valueOf(latitude);
    }

    /**
     * Sets the longitude.
     *
     * @param longitude the new longitude
     */
    public void setLongitude(String longitude) {
        this.lng = Double.valueOf(longitude);
    }

    /**
     * Gets the lng.
     *
     * @return the lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * Sets the lng.
     *
     * @param lng the new lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("lat", lat).append("lng", lng)
                .toString();
    }

}
