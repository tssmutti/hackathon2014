package com.mutti.rest;

import java.io.Serializable;

public class MuttisResponse implements Serializable {

	private static final long serialVersionUID = 2032595722300902943L;
	
	private int totalTripDurationInSeconds;
	
	private MuttiStatus status;
	
	private int delayInSeconds;
	
	private String trafficJamInformation;
	
	private String alternativeRoute;

	private Location destination;

	public long getTotalTripDurationInSeconds() {
		return totalTripDurationInSeconds;
	}

	public void setTotalTripDurationInSeconds(int totalTripDurationInSeconds) {
		this.totalTripDurationInSeconds = totalTripDurationInSeconds;
	}

	public MuttiStatus getStatus() {
		return status;
	}

	public void setStatus(MuttiStatus status) {
		this.status = status;
	}

	public int getDelayInSeconds() {
		return delayInSeconds;
	}

	public void setDelayInSeconds(int delayInSeconds) {
		this.delayInSeconds = delayInSeconds;
	}

	public String getTrafficJamInformation() {
		return trafficJamInformation;
	}

	public void setTrafficJamInformation(String trafficJamInformation) {
		this.trafficJamInformation = trafficJamInformation;
	}

	public String getAlternativeRoute() {
		return alternativeRoute;
	}

	public void setAlternativeRoute(String alternativeRoute) {
		this.alternativeRoute = alternativeRoute;
	}	
	
	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

}
