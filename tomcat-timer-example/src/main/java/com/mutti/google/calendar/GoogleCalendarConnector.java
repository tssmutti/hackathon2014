package com.mutti.google.calendar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.gson.Gson;
import com.mutti.google.calendar.generated.EventList;
import com.mutti.google.calendar.generated.Item;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;

public class GoogleCalendarConnector {

	private String googleCalendarUrl = "https://www.googleapis.com/calendar/v3/calendars/tssmutti%40gmail.com/events?orderBy=startTime&singleEvents=true&key=1038817965506-dc899rq3gh965uou87bhqsvm3mg9bvec.apps.googleusercontent.com";
	
	public List<Event> getEventItemsFromGoogle()
			throws GoogleCalendarClientException {
	
		  
		final HttpTransport TRANSPORT = new NetHttpTransport();
	    final JsonFactory JSON_FACTORY = new JacksonFactory();
	    
	    Collection<String> scopes = new ArrayList<String>();
	    scopes.add(CalendarScopes.CALENDAR);

	    GoogleCredential credential;
		try {
			
//			InputStream inputStream = 
//				      getClass().getClassLoader().getResourceAsStream("2d7ccfb2dd2bf9b7e04718204e69483430c7c489-privatekey.p12");
//				   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream ));
			
			credential = new  GoogleCredential.Builder()
			  .setTransport(TRANSPORT)
			  .setJsonFactory(JSON_FACTORY)
			  .setServiceAccountId("1038817965506-dc899rq3gh965uou87bhqsvm3mg9bvec@developer.gserviceaccount.com")
			  .setServiceAccountScopes(scopes)
			  .setServiceAccountPrivateKeyFromP12File(new File("c:/2d7ccfb2dd2bf9b7e04718204e69483430c7c489-privatekey.p12"))
			  .build();
			
			  Calendar cal = new com.google.api.services.calendar.Calendar.Builder(TRANSPORT, JSON_FACTORY, credential).setApplicationName("tssmutti2014").build();

			  //com.google.api.services.calendar.Calendar.Events eventsL = cal.events();
			  
			  List<Event> eventList = new ArrayList<Event>();
			  
			  String pageToken = null;
			  do {
			    Events events = cal.events().list("tssmutti@gmail.com").setPageToken(pageToken).execute();
			    List<Event> items = events.getItems();
			    eventList.addAll(items);
			    pageToken = events.getNextPageToken();
			  } while (pageToken != null);
			  
			  
			    return eventList;
			    
			    
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new GoogleCalendarClientException(
					"Exception while executing request against google: "
							+ e1.getStackTrace());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new GoogleCalendarClientException(
					"Exception while executing request against google: "
							+ e1.getStackTrace());
		}
	    
		
		
		/*
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(googleCalendarUrl);
		String googleCalendarResponseAsString;
		request.addHeader(
				"Authorization",
				"Bearer ya29.1.AADtN_UqCRpz9lDkJZi_ShjsYuxKQ0EhnMOWZ1TwDcLPW0y7ZAw0rjeVySWNk6_c");
		request.addHeader("Content-Type", "application/json");

		try {
			HttpResponse response;
			response = client.execute(request);
			googleCalendarResponseAsString = EntityUtils.toString(response
					.getEntity());
		} catch (Exception e) {
			throw new GoogleCalendarClientException(
					"Exception while executing request against google: "
							+ e.getStackTrace());
		}
		Gson gson = new Gson();
		EventList eventList = gson.fromJson(googleCalendarResponseAsString,
				EventList.class);
		return eventList.getItems();
		
		*/
	}

}
