/*
 * Copyright © 2012 Daimler TSS. All Rights Reserved.
 *
 * Reproduction or transmission in whole or in part, in any form or by any
 * means, is prohibited without the prior written consent of the copyright
 * owner.
 * 
 * Created on: 24.01.2012
 * Created by: LANDSBS
 * Last modified on: $Date: 2008/10/01 09:06:27CEST $
 * Last modified by: $Author: Hardt, Dennis (dhardt7) LANDSBS $
 */
package com.mutti.directions.datatypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * A Route in the form of responses from the Google Directions API.
 * 
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleRoute {

    /** The legs. */
    private List<GoogleLeg> legs = new ArrayList<GoogleLeg>();

    /** The overview polyline. */
    @JsonProperty(value = "overview_polyline")
    private GooglePolyline overview_polyline;

    /**
     * Sets the legs.
     * 
     * @param legs
     *            the new legs
     */
    public void setLegs(List<GoogleLeg> legs) {
        this.legs.clear();
        this.legs.addAll(legs);
    }

    /**
     * Gets the legs.
     * 
     * @return the legs
     */
    public List<GoogleLeg> getLegs() {
        return Collections.unmodifiableList(legs);
    }

    /**
     * Sets the overview polyline.
     * 
     * @param overviewPolyline
     *            the new overview polyline
     */
    public void setOverview_polyline(GooglePolyline overviewPolyline) {
        this.overview_polyline = overviewPolyline;
    }

    /**
     * Gets the overview polyline.
     * 
     * @return the overview polyline
     */
    public GooglePolyline getOverview_polyline() {
        return overview_polyline;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("legs", legs)
                .append("overviewPolyline", overview_polyline).toString();
    }

}
