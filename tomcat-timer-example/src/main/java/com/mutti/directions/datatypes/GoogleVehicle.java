package com.mutti.directions.datatypes;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * A mapper class for Vehicle data type from Google.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleVehicle {

    /** The name. */
    private String name;
    
    /** The type. */
    private String type;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }
}
