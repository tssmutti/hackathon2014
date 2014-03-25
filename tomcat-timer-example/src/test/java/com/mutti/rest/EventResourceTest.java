package com.mutti.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EventResourceTest {
	
	private List<MuttiEvent> muttisEvents;
	
	private EventResource eventResource;
	
	@Before
	public void setUp(){
		MuttiEvent event1 = new MuttiEvent();
		event1.setEventId("foo");
		event1.setLocation("here");
		MuttiEvent event2 = new MuttiEvent();
		event2.setEventId("bar");
		event2.setLocation("there");
		muttisEvents = new ArrayList<MuttiEvent>();
		muttisEvents.add(event1);
		muttisEvents.add(event2);
		MuttisMemory.muttisEvents = muttisEvents;
		eventResource = new EventResource();
	}
	
	@Test
	public void testGetMuttiEventById(){
		MuttiEvent event = eventResource.getMuttiEventById("foo");
		assertNotNull(event);
		assertEquals("here", event.getLocation());
		event = eventResource.getMuttiEventById("bar");
		assertNotNull(event);
		assertEquals("there", event.getLocation());
	}
	
	@Test
	public void testCalculateIfEventIsObservable() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = "15-02-2014 02:21:56";
		Date appointment = sdf.parse(dateInString);

		assertEquals(MuttiStatus.RED, eventResource.calculateIfEventIsObservable(2000, appointment));
		
		dateInString = "15-02-2014 02:56:56";
		appointment = sdf.parse(dateInString);
		assertEquals(MuttiStatus.RED, eventResource.calculateIfEventIsObservable(200, appointment));
	}

}
