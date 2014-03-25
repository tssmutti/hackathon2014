package com.mutti.directions.datatypes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.daimler.moovel.domain.HashCodeBuilderHelper;
import com.daimler.moovel.domain.RoutePoint;
import com.daimler.moovel.domain.vehicle.VehicleDetails;


/**
 * Wraps the class {@link GoogleRoute} adding origin and destination.
 * 
 * @author Christian Bergs
 */
public final class GoogleRouteWrapper {

    /** The google route. */
    private GoogleRoute googleRoute;
    
    /** The origin. */
    private RoutePoint origin;
    
    /** The destination. */
    private RoutePoint destination;

    /**
     * Instantiates a new google route wrapper.
     */
    private GoogleRouteWrapper() {
        // prevent from being called from outside class.
    }

    /**
     * Gets the google route.
     *
     * @return the google route
     */
    public GoogleRoute getGoogleRoute() {
        return googleRoute;
    }

    /**
     * Gets the origin.
     *
     * @return the origin
     */
    public RoutePoint getOrigin() {
        return origin;
    }

    /**
     * Gets the destination.
     *
     * @return the destination
     */
    public RoutePoint getDestination() {
        return destination;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GoogleRouteWrapper)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        GoogleRouteWrapper other = (GoogleRouteWrapper) o;
        return new EqualsBuilder().append(googleRoute, other.googleRoute).append(origin, other.origin)
                .append(destination, other.destination).isEquals();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeBuilderHelper.HASH_CODE_BUILDER_INITIAL_ODD_NUMBER,
                HashCodeBuilderHelper.HASH_CODE_BUILDER_MULTIPLIER_ODD_NUMBER).append(googleRoute).append(origin)
                .append(destination).hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("googleRoute", googleRoute)
                .append("origin", origin).append("destination", destination).toString();
    }

    /**
     * Builder that initializes the {@link GoogleRouteWrapper} object.
     * 
     * @author Christian Bergs
     */
    public static class Builder {

        /** The google route wrapper. */
        private GoogleRouteWrapper googleRouteWrapper = new GoogleRouteWrapper();

        /**
         * Sets the google route.
         *
         * @param googleRoute            The google route
         * @return The builder instance
         */
        public Builder googleRoute(GoogleRoute googleRoute) {
            this.googleRouteWrapper.googleRoute = googleRoute;
            return this;
        }

        /**
         * Sets the routes's origin.
         *
         * @param origin            The origin
         * @return The builder instance
         */
        public Builder origin(RoutePoint origin) {
            this.googleRouteWrapper.origin = origin;
            return this;
        }

        /**
         * Sets the routes's destination.
         *
         * @param destination            The destination
         * @return The builder instance
         */
        public Builder destination(RoutePoint destination) {
            this.googleRouteWrapper.destination = destination;
            return this;
        }

        /**
         * Builds the {@link GoogleRouteWrapper} object.
         * 
         * @return The ready built {@link VehicleDetails} object.
         */
        public GoogleRouteWrapper build() {

            if (this.googleRouteWrapper.googleRoute == null) {
                throw new IllegalArgumentException("GoogleRouteWrapper.googleRoute is not set.");
            }
            return this.googleRouteWrapper;
        }

    }

}
