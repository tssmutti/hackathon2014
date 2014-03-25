package com.mutti.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mutti.moovel.connector.MoovelConnector;
import com.mutti.moovel.connector.MoovelConnectorException;

@Path("/geo")
public class RouteResource {
	
	@POST
	@Path("route")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getRoute(String payload) {
		
		System.out.println(payload);
		
		MoovelConnector moovelConnector = new MoovelConnector();
		return moovelConnector.getRoute(payload);
		
	}
	
	
	@GET
	@Path("forwardgeo")
	@Produces(MediaType.APPLICATION_JSON)
	public Location getForwardGeo(@QueryParam("address") String address) {
		
		MoovelConnector moovelConnector = new MoovelConnector();
		
		Location geoLocation = new Location();
		
		try {
			com.mutti.moovel.connector.Location location = moovelConnector.getForwardGeoLocation(address);
			geoLocation.setLatitude(location.getLatitude());
			geoLocation.setLongitude(location.getLongitude());
			
			
		} catch (MoovelConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return geoLocation;
	}

}
