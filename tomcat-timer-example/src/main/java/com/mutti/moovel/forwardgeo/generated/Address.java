
package com.mutti.moovel.forwardgeo.generated;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("com.googlecode.jsonschema2pojo")
public class Address {

    @Expose
    private String type;
    @Expose
    private String name;
    @Expose
    private List<Object> vehicleTypes = new ArrayList<Object>();
    @Expose
    private String city;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<Object> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
