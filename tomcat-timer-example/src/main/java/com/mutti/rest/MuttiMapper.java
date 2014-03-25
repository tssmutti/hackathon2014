package com.mutti.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

public class MuttiMapper {

	public static List<MuttiEvent> mapToMutti(List<Event> calendarItems) {
		ArrayList<MuttiEvent> muttis = new ArrayList<MuttiEvent>();

		for (Event event : calendarItems) {
			System.out.println(event.getSummary());

			MuttiEvent newMutti = new MuttiEvent();
			newMutti.setEventId(event.getId());

			Date startDate = null;
			Date endDate = null;
			startDate = new Date(event.getStart().getDateTime().getValue());
			endDate = new Date(event.getEnd().getDateTime().getValue());

			newMutti.setStart(startDate);
			newMutti.setEnd(endDate);
			newMutti.setEventName(event.getSummary());
			newMutti.setHtmlLink(event.getHtmlLink());
			newMutti.setLocation(event.getLocation());
			newMutti.setAttendees(getAttendeesAsString(event.getAttendees()));
			muttis.add(newMutti);

		}

		/*
		 * for(Events item:calendarItems){ MuttiEvent newMutti = new
		 * MuttiEvent(); newMutti.setEventId(item.getId());
		 * 
		 * SimpleDateFormat sdfToDate = new SimpleDateFormat(
		 * "dd.MM.yyyy HH:mm:ss"); Date startDate = null; Date endDate = null;
		 * try { startDate = sdfToDate.parse(item.getStart().getDateTime());
		 * endDate= sdfToDate.parse(item.getEnd().getDateTime());
		 * 
		 * } catch (ParseException e) { e.printStackTrace(); }
		 * newMutti.setStart(startDate); newMutti.setEnd(endDate);
		 * newMutti.setEventName(item.getSummary());
		 * newMutti.setHtmlLink(item.getHtmlLink());
		 * newMutti.setLocation(item.getLocation());
		 * newMutti.setAttendees(getAttendeesAsString(item.getAttendees()));
		 * muttis.add(newMutti); }
		 */
		return muttis;
	}

	private static List<String> getAttendeesAsString(
			List<EventAttendee> attendees) {
		List<String> attendeesAsString = new ArrayList<String>();

		if (attendees != null) {

			for (EventAttendee attendee : attendees) {
				attendeesAsString.add(attendee.getEmail());
			}

		}
		return attendeesAsString;
	}

}
