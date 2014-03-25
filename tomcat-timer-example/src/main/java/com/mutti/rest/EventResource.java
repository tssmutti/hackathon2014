package com.mutti.rest;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

import com.daimler.moovel.domain.Cost;
import com.daimler.moovel.domain.CostType;
import com.google.api.services.calendar.model.Event;
import com.mutti.google.calendar.GoogleCalendarClientException;
import com.mutti.google.calendar.GoogleCalendarConnector;
import com.mutti.google.connector.GoogleDirectionsConnector;
import com.mutti.google.connector.MuttiRoute;
import com.mutti.moovel.connector.MoovelConnector;
import com.mutti.util.DateConverter;
import com.mutti.util.MailSender;
import com.mutti.util.TripTime;

@Path("/events")
public class EventResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MuttiEvent> getEventForUser() {
		GoogleCalendarConnector connector = new GoogleCalendarConnector();
		List<Event> calendarItems;
		try {
			calendarItems = connector.getEventItemsFromGoogle();
			List<MuttiEvent> muttisEvents = MuttiMapper
					.mapToMutti(calendarItems);
			// store in memory
			MuttisMemory.muttisEvents = muttisEvents;
			return muttisEvents;
		} catch (GoogleCalendarClientException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	/**
	 * Getting the overall trip duration for a given vehicletype, a start
	 * position and a given event.
	 * 
	 * @param eventid
	 * @param vehicletype
	 * @param startlat
	 * @param startlng
	 * @return
	 */
	@GET
	@Path("muttisresponse")
	@Produces(MediaType.APPLICATION_JSON)
	public MuttisResponse getMuttisResponse(
			@QueryParam("eventid") String eventid,
			@QueryParam("vehicletype") String vehicletype,
			@QueryParam("startlat") String startlatitude,
			@QueryParam("startlng") String startlongitude) {

		MuttiEvent currentEvent = getMuttiEventById(eventid);
		if (currentEvent == null) {
			return new MuttisResponse();
		}
		RouteResource routing = new RouteResource();
		Location location = routing.getForwardGeo(currentEvent.getLocation());
		GoogleDirectionsConnector connector = new GoogleDirectionsConnector();
		String start = startlatitude + "," + startlongitude;
		String end = location.getLatitude() + "," + location.getLongitude();
		TripTime tripTime = new TripTime();
		tripTime.setDeparture(false);
		Calendar eventTime = Calendar.getInstance();
		eventTime.setTime(currentEvent.getStart());
		tripTime.setTime(eventTime);
		List<MuttiRoute> routes = connector.getGoogleRoutes(start, end,
				tripTime, "driving");
		int durationInSeconds = 0;
		Calendar startTime = null;
		if (!routes.isEmpty()) {
			Cost durationCost = routes.get(0).getCosts()
					.getCostsOfType(CostType.DURATION);
			durationInSeconds = (int) durationCost.getValueAsLong();
			startTime = routes.get(0).getDepartureTime();
			sendStartMail(startlatitude, startlongitude, location, currentEvent, startTime);
		}

		MuttisResponse muttisResponse = new MuttisResponse();
		MuttiStatus status;
		if (isTrafficJamOnRoute()) {
			muttisResponse
					.setTrafficJamInformation("DETECTED TRAFFIC JAM ON YOUR ROUTE! ADDITIONAL REQUIRED TIME IS 10 MINUTES!");
			status = calculateIfEventIsObservable(durationInSeconds + 600,
					currentEvent.getStart());
			muttisResponse.setStatus(status);
		} else {
			status = calculateIfEventIsObservable(durationInSeconds,
					currentEvent.getStart());
			muttisResponse.setStatus(status);
		}
		muttisResponse.setTotalTripDurationInSeconds(durationInSeconds);
        muttisResponse.setAlternativeRoute(calculateAlternativeRoute(startlatitude, startlongitude, location, currentEvent, status));
		
        sendMailToAttendeesIfWillBeTooLate(startlatitude, startlongitude, location, currentEvent, status);

		muttisResponse.setStatus(calculateIfEventIsObservable(
				durationInSeconds, currentEvent.getStart()));
		muttisResponse.setDestination(location);
		return muttisResponse;
	}

    private String calculateAlternativeRoute(String startLat, String startLng, Location meetingLocation, MuttiEvent currentEvent,
            MuttiStatus status) {
        String alternativeRoute = "";
        if (status == MuttiStatus.RED) {

            MoovelConnector connector = new MoovelConnector();
            String meetingDate = DateConverter.convertToString(currentEvent.getStart());
            // 2014-02-14T21:46:41
            String queryParams = "{\"startPoint\":{\"latitude\":\""
                    + startLat
                    + "\",\"longitude\":\""
                    + startLng
                    + "\",\"address\":{\"type\":\"ADDRESS\",\"address\":\"\",\"city\":\"Stuttgart\"},\"properties\":{}},\"endPoint\":{\"latitude\":\""
                    + meetingLocation.getLatitude()
                    + "\",\"longitude\":\""
                    + meetingLocation.getLongitude()
                    + "\",\"address\":{\"type\":\"STATION\",\"name\":\"Vaihingen\",\"vehicleTypes\":[],\"city\":\"Stuttgart\"},\"properties\":{\"id\":\"\"}},\"preferences\":{},\""
                    + "searchDate\":{\"date\":\"" + meetingDate
                    + "\",\"isStarttime\":false},\"searchType\":\"COORDINATES\"}";
            try {
                alternativeRoute = connector.getRoute(queryParams);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return alternativeRoute;
    }

    private void sendMailToAttendeesIfWillBeTooLate(String startLat, String startLng, Location meetingLocation, MuttiEvent currentEvent,
			MuttiStatus status) {
		// send mail if you are too late
		if (status == MuttiStatus.RED) {
		    
			for (String attendee : currentEvent.getAttendees()) {
				MailSender
						.sendMailTo(
								attendee,
								"Sorry, but I am in a traffic jam and will be about 22mins too late. Please start the meeting without me. Keep cool but don`t freeze!");
			}
		}
	}
    
    private void sendStartMail(String startLat, String startLng, Location meetingLocation, MuttiEvent currentEvent,
            Calendar appointmentTime) {
        if (Calendar.getInstance().after(appointmentTime)) {
            for (String attendee : currentEvent.getAttendees()) {
                MailSender.sendMailTo(attendee, "Mutti says: 'It is time to move your behind!, you have a meeting ('"
                        + currentEvent.getEventName() + "')");
            }
        }
    }

	@GET
	@Path("isalive")
	@Produces(MediaType.TEXT_PLAIN)
	public String isMuttiBackendAlive() {
		return "This is MUTTI`s Backend Server! Also Greetings from VATI!";
	}

	public MuttiEvent getMuttiEventById(String eventId) {
		if (MuttisMemory.muttisEvents != null) {
			for (MuttiEvent muttiEvent : MuttisMemory.muttisEvents) {
				if (muttiEvent.getEventId().equalsIgnoreCase(eventId)) {
					return muttiEvent;
				}
			}
		}
		return null;
	}

	public MuttiStatus calculateIfEventIsObservable(int durationInSeconds,
			Date appointment) {
		DateTime currentDatePlusDrivingDuration = new DateTime();
		currentDatePlusDrivingDuration = currentDatePlusDrivingDuration
				.plusSeconds(durationInSeconds);

		DateTime dateOfEvent = new DateTime(appointment);
		if (currentDatePlusDrivingDuration.isBefore(dateOfEvent)) {
			return MuttiStatus.GREEN;
		}

		if (currentDatePlusDrivingDuration.isAfter(dateOfEvent)) {
			return MuttiStatus.RED;
		} else {
			return MuttiStatus.YELLOW;
		}
	}

	public boolean isTrafficJamOnRoute() {
		int number = (int) (Math.random() * 10 + 1);
		if (number == 10) {
			return true;
		}
		return false;
	}
}
