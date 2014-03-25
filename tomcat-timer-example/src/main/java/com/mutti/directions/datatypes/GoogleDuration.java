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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * Class mapping for duration data type from Google.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDuration {

    // duration in sec
    /** The value. */
    private long value;

    /**
     * standard constructor.
     */
    public GoogleDuration() {}

    /**
     * constructor.
     *
     * @param value            for duration
     */
    public GoogleDuration(long value) {
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
