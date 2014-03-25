package com.mutti.google.connector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.daimler.moovel.domain.Costs;
import com.daimler.moovel.domain.HashCodeBuilderHelper;
import com.daimler.moovel.domain.Leg;
import com.daimler.moovel.domain.Route;


@XmlRootElement(name="route")
public class MuttiRoute implements Serializable {
 
    private static final long serialVersionUID = -7282979876400488058L;

    private List<Leg> legs = new ArrayList<Leg>();
    private Costs costs = new Costs();
    private String providerId;

    /**
     * This member contains the origin geographic point of the route request.
     */
    private MuttiRoutePoint origin;

    /**
     * This member contains the destination geographic point of the route request.
     */
    private MuttiRoutePoint destination;

    private boolean reservationPossible;
    
    /**
     * Default Constructor
     */
    public MuttiRoute() {

    }

    /**
     * A constructor to create a route from an existing route
     * 
     * @param route
     *            An input {@link Route}
     */
    public MuttiRoute(Route route) {
        Validate.notNull(route);
        if (route.getLegs() != null) {
            for (Leg leg : route.getLegs()) {
                Leg newLeg = leg != null ? new Leg(leg) : null;
                legs.add(newLeg);
            }
        }
        this.costs = route.getCosts();
        this.destination = new MuttiRoutePoint(route.getDestination());
        this.origin = new MuttiRoutePoint(route.getOrigin());
        this.providerId = route.getProviderId();
        this.reservationPossible = route.isReservationPossible();
    }

    public Costs getCosts() {
        return this.costs;
    }

    public void setCosts(Costs costs) {
        this.costs = costs;
    }

    public List<Leg> getLegs() {
        return Collections.unmodifiableList(this.legs);
    }

    /**
     * Adds the given {@link Leg}.
     * 
     * @param leg
     *            The {@link Leg} to add.
     */
    public void addLeg(Leg leg) {
        this.legs.add(leg);
    }

    /**
     * Setter for the {@link Leg}s
     * 
     * @param legs
     *            The legs
     */
    public void setLegs(List<Leg> legs) {
        this.legs.clear();
        this.legs.addAll(legs);
    }

    public String getProviderId() {
        return this.providerId;
    }

    public void setProviderId(String providerIdToSet) {
        this.providerId = providerIdToSet;
    }

    /**
     * Retrieves the first leg of the route.
     * 
     * @return the first leg or null if there are no legs.
     */
    @JsonIgnore
    @XmlTransient
    public Leg getFirstLeg() {
        if (!this.getLegs().isEmpty()) {
            return this.getLegs().get(0);
        }
        return null;
    }

    /**
     * Retrieves the last leg of the route.
     * 
     * @return the last leg or null if there are no legs.
     */
    @JsonIgnore
    @XmlTransient
    public Leg getLastLeg() {
        int legSize = this.getLegs().size();
        if (legSize > 0) {
            return this.getLegs().get(legSize - 1);
        }
        return null;
    }

    /**
     * Gets the overall departure time of the route.
     * 
     * @return the Calendar, null if no legs in route.
     */
    @JsonIgnore
    @XmlTransient
    public Calendar getDepartureTime() {
        Leg firstLeg = getFirstLeg();
        if (firstLeg != null) {
            return firstLeg.getDepartureTime();
        }
        return null;
    }

    /**
     * Gets the overall arrival time of the route.
     * 
     * @return the Calendar, null if no legs in route.
     */
    @JsonIgnore
    @XmlTransient
    public Calendar getArrivalTime() {
        Leg lastLeg = getLastLeg();
        if (lastLeg != null) {
            return lastLeg.getArrivalTime();
        }
        return null;
    }

    

    public  MuttiRoutePoint getOrigin() {
        return origin;
    }

    public  void setOrigin(MuttiRoutePoint origin) {
        this.origin = origin;
    }

    public  MuttiRoutePoint getDestination() {
        return destination;
    }

    public  void setDestination(MuttiRoutePoint destination) {
        this.destination = destination;
    }

    public boolean isReservationPossible() {
        return reservationPossible;
    }

    public void setReservationPossible(boolean reservationPossible) {
        this.reservationPossible = reservationPossible;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MuttiRoute)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        MuttiRoute a = (MuttiRoute) o;
        return new EqualsBuilder().append(this.legs, a.legs).append(this.costs, a.costs)
                .append(this.providerId, a.providerId).append(this.origin, a.origin)
                .append(reservationPossible, a.reservationPossible)
                .append(this.destination, a.destination).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeBuilderHelper.HASH_CODE_BUILDER_INITIAL_ODD_NUMBER,
                HashCodeBuilderHelper.HASH_CODE_BUILDER_MULTIPLIER_ODD_NUMBER).append(this.legs).append(this.costs)
                .append(this.providerId).append(this.origin)
                .append(this.reservationPossible)
                .append(this.destination).hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("legs", this.legs)
                .append("costs", this.costs).append("providerId", this.providerId).append("origins", this.origin)
                .append("reservationPossible", this.reservationPossible)
                .append("destinations", this.destination).toString();
    }

    
    
    
}
