package com.mutti.moovel.connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mutti.moovel.forwardgeo.generated.LocationList;

public class MoovelConnector {

	private String moovelForwardGeoCodingUrl = "https://www.moovel.com/openmobility/v6/locations/search";
	private String moovelRouteUrl = "https://www.moovel.com/openmobility/v6/journeys/search?mspGroupId=OEPNV";

	public Location getForwardGeoLocation(String location)
			throws MoovelConnectorException {

		String encodedUrl = null;
		String moovelForwardGeoAsString;

		try {
			encodedUrl = URLEncoder.encode(location, "UTF-8");
			// System.out.println("encoded URL" + encodedUrl);

			HttpClient client = new DefaultHttpClient();
			// HttpGet request = new HttpGet(moovelForwardGeoCodingUrl +
			// "?address=" + encodedUrl);
			HttpGet request = new HttpGet(
					"https://www.moovel.com/openmobility/v6/locations/search?address="
							+ encodedUrl);

			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept-Charset", "UTF-8");

			HttpResponse response;
			response = client.execute(request);
			moovelForwardGeoAsString = EntityUtils.toString(response
					.getEntity());
		} catch (Exception e) {
			throw new MoovelConnectorException(
					"Exception while executing request against moovel: "
							+ e.getStackTrace());
		}

		System.out.println("moovel geo: " + moovelForwardGeoAsString);

		Gson gson = new Gson();

		Type collectionType = new TypeToken<Collection<LocationList>>() {
		}.getType();
		Collection<LocationList> locationItemsList = gson.fromJson(
				moovelForwardGeoAsString, collectionType);

		LocationList[] locationArray = locationItemsList
				.toArray(new LocationList[locationItemsList.size()]);

		System.out.println("moovel geo 1: " + locationArray[0].getLatitude());

		Location geoLocation = new Location();

		geoLocation.setLatitude(locationArray[0].getLatitude());
		geoLocation.setLongitude(locationArray[0].getLongitude());

		return geoLocation;
	}

	public String getRoute(String trip) {

		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(moovelRouteUrl);
		request.addHeader("Content-Type", "application/json");
		request.addHeader("Accept", "application/json");
		request.addHeader("Accept-Charset", "UTF-8");

		String routeResponse = null;

		StringEntity routeBody;
		try {
			routeBody = new StringEntity(trip);
			request.setEntity(routeBody);

			HttpResponse response;
			response = client.execute(request);
			routeResponse = EntityUtils.toString(response.getEntity());

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routeResponse;

	}

}
