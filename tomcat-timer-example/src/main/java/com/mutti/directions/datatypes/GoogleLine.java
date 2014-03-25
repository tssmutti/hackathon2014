package com.mutti.directions.datatypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.daimler.moovel.domain.Agency;


/**
 * A mapper for the Line data type from Google.
 *
 * @author ANDADA
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleLine {

    /** The name. */
    @JsonProperty(value = "short_name")
    private String name;
    
    /** The vehicle. */
    private GoogleVehicle vehicle;
    
    /** The agencies. */
    private List<Agency> agencies = new ArrayList<Agency>();

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
     * Gets the vehicle.
     *
     * @return the vehicle
     */
    public GoogleVehicle getVehicle() {
        return vehicle;
    }

    /**
     * Sets the vehicle.
     *
     * @param vehicle the new vehicle
     */
    public void setVehicle(GoogleVehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Gets the agencies.
     *
     * @return the agencies
     */
    public List<Agency> getAgencies() {
        return Collections.unmodifiableList(agencies);
    }

    /**
     * Sets the agencies.
     *
     * @param agencies the new agencies
     */
    public void setAgencies(List<Agency> agencies) {
        this.agencies.clear();
        this.agencies.addAll(agencies);
    }

}
