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


/**
 * A class containing the entire response from Google Routing API.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleRoutingResponse {

    /** The status. */
    private String status;
    
    /** The routes. */
    private List<GoogleRoute> routes = new ArrayList<GoogleRoute>();

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the routes.
     *
     * @param routes the new routes
     */
    public void setRoutes(List<GoogleRoute> routes) {
        this.routes.clear();
        this.routes.addAll(routes);
    }

    /**
     * Gets the routes.
     *
     * @return the routes
     */
    public List<GoogleRoute> getRoutes() {
        return Collections.unmodifiableList(routes);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("status", status)
                .append("routes", routes).toString();
    }

}
