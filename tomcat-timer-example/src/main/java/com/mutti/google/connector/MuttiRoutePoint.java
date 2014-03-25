package com.mutti.google.connector;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.daimler.moovel.domain.HashCodeBuilderHelper;
import com.daimler.moovel.domain.RoutePoint;
import com.daimler.moovel.domain.geo.GeoPoint;

public class MuttiRoutePoint implements Serializable {
    // END_CHECKSTYLE_SUPPRESS

    private static final long serialVersionUID = 4002230873517361918L;

    /** Time to add/subtract to the desired time for the route calculation. */
    private long timeDeltaInSeconds;

    /**
     * Handicap to add a certain point, if the point is not desired, increasing this property will make it ill-favored
     */
    private long dislikeFactorInSeconds;

    /** The walking distance that has already been used a previous route. */
    private long consumedPedestrianDistanceInMeters;

    private GeoPoint geoPoint;

    public MuttiRoutePoint() {}

    public MuttiRoutePoint(RoutePoint routePoint) {
        if (routePoint != null) {
            this.geoPoint = routePoint.getGeoPoint();
        }
    }

    /**
     * Constructor
     * 
     * @param geoPoint
     *            The geo point
     */
    public MuttiRoutePoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public long getTimeDeltaInSeconds() {
        return timeDeltaInSeconds;
    }

    public void setTimeDeltaInSeconds(long timeDeltaInSeconds) {
        this.timeDeltaInSeconds = timeDeltaInSeconds;
    }

    public long getDislikeFactorInSeconds() {
        return dislikeFactorInSeconds;
    }

    public void setDislikeFactorInSeconds(long dislikeFactorInSeconds) {
        this.dislikeFactorInSeconds = dislikeFactorInSeconds;
    }

    public long getConsumedPedestrianDistanceInMeters() {
        return consumedPedestrianDistanceInMeters;
    }

    public void setConsumedPedestrianDistanceInMeters(long consumedPedestrianDistanceInMeters) {
        this.consumedPedestrianDistanceInMeters = consumedPedestrianDistanceInMeters;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MuttiRoutePoint)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        MuttiRoutePoint other = (MuttiRoutePoint) o;
        return new EqualsBuilder().append(timeDeltaInSeconds, other.timeDeltaInSeconds)
                .append(dislikeFactorInSeconds, other.dislikeFactorInSeconds)
                .append(consumedPedestrianDistanceInMeters, other.consumedPedestrianDistanceInMeters)
                .append(geoPoint, other.geoPoint).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeBuilderHelper.HASH_CODE_BUILDER_INITIAL_ODD_NUMBER,
                HashCodeBuilderHelper.HASH_CODE_BUILDER_MULTIPLIER_ODD_NUMBER).append(timeDeltaInSeconds)
                .append(dislikeFactorInSeconds).append(consumedPedestrianDistanceInMeters).append(geoPoint).hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("timeDeltaInSeconds", timeDeltaInSeconds)
                .append("dislikeFactorInSeconds", dislikeFactorInSeconds)
                .append("consumedPedestrianDistanceInMeters", consumedPedestrianDistanceInMeters)
                .append("geoPoint", geoPoint).toString();
    }

}