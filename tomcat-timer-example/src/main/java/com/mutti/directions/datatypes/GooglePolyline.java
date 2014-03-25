package com.mutti.directions.datatypes;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * A mapper for the polyline data type from Google API.
 *
 * @author ANDADA
 */
public class GooglePolyline {

    /** The points. */
    private String points;

    /**
     * constructor.
     */
    public GooglePolyline() {
        super();
    }

    /**
     * constructor with points.
     *
     * @param points            the String with points
     */
    public GooglePolyline(String points) {
        super();
        this.points = points;
    }

    /**
     * Sets the points.
     *
     * @param points the new points
     */
    public void setPoints(String points) {
        this.points = points;
    }

    /**
     * Gets the points.
     *
     * @return the points
     */
    public String getPoints() {
        return points;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("points", points).toString();
    }

}
