package com.mutti.google.connector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.daimler.moovel.domain.Route;
import com.daimler.moovel.domain.RouteTime;
import com.daimler.moovel.domain.routing.api.RouteRequest;
import com.google.gson.Gson;
import com.mutti.directions.datatypes.GoogleRoute;
import com.mutti.directions.datatypes.GoogleRouteWrapper;
import com.mutti.directions.datatypes.GoogleRoutingResponse;
import com.mutti.directions.mapper.GoogleDirectionsMapper;
import com.mutti.util.TripTime;

public class GoogleDirectionsConnector {

    protected final static String CRYPTO_KEY = "AmoaMYomn-RO_QYESCYvpP8walU=";
    private static final String CLIENT_ID = "gme-daimlerag4";

    private final static String DIRECTIONS_PREFIX = "/maps/api/directions/";

    // private String googleDirectionsUrl =
    // "https://maps.googleapis.com/maps/api/directions/json?origin=Stuttgart&destination=Vaihingen,Stuttgart&sensor=false&departure_time=1343641500&mode=transit";
    private String googleDirectionsUrl = "https://maps.googleapis.com/maps/api/directions/json?";

    public List<MuttiRoute> getGoogleRoutes(String start, String end, TripTime tripTime, String mode) {
        HttpClient client = new DefaultHttpClient();
        StringBuilder builder = new StringBuilder();
        builder.append(googleDirectionsUrl);
        builder.append("origin=");
        builder.append(start);
        builder.append("&destination=");
        builder.append(end);
        builder.append("&sensor=false");
        if ("transit".equals(mode)) {
            builder.append(tripTime.isDeparture() ? "&departure_time=" : "&arrival_time=");
            builder.append(tripTime.getTime().getTimeInMillis());
        }
        builder.append("&mode=");
        System.out.println("Mode " + mode);
        builder.append(mode);
        appendClientIdSignature(builder, DIRECTIONS_PREFIX);
        HttpGet request = new HttpGet(builder.toString());
        System.out.println(builder.toString());
        String googleCalendarResponseAsString;
        request.addHeader("Content-Type", "application/json");

        try {
            HttpResponse response = client.execute(request);
            googleCalendarResponseAsString = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException("Exception while executing request against google: " + e.getStackTrace(), e);
        }
        Gson gson = new Gson();
        GoogleRoutingResponse googleResponse = gson.fromJson(googleCalendarResponseAsString,
                GoogleRoutingResponse.class);
        List<GoogleRoute> googleRoutes = googleResponse.getRoutes();
        List<GoogleRouteWrapper> routes = new ArrayList<GoogleRouteWrapper>();
        for (GoogleRoute googleRoute : googleRoutes) {
            GoogleRouteWrapper route = new GoogleRouteWrapper.Builder().googleRoute(googleRoute).build();
            routes.add(route);
        }
        RouteRequest routeRequest = new RouteRequest();
        RouteTime routeTime = new RouteTime();
        routeTime.setDeparture(tripTime.isDeparture());
        routeTime.setTime(tripTime.getTime());
        routeRequest.setRouteTime(routeTime);
        List<Route> convertToRoutes = GoogleDirectionsMapper.convertToRoutes(routes, routeRequest);
        return convertToMuttiRoute(convertToRoutes);
    }

    private List<MuttiRoute> convertToMuttiRoute(List<Route> convertToRoutes) {
        List<MuttiRoute> muttiRoutes = new ArrayList<MuttiRoute>();
        for (Route route : convertToRoutes) {
            muttiRoutes.add(new MuttiRoute(route));
        }
        return muttiRoutes;
    }

    protected void appendClientIdSignature(StringBuilder builder, String prefix) {

        builder.append("&client=" + CLIENT_ID);
        String stignature = getSignature(builder.toString(), prefix);
        builder.append(stignature);
    }

    private String getSignature(String urlToEncode, String prefix) {

        String stripped = urlToEncode.substring(urlToEncode.indexOf("json?"), urlToEncode.length());
        String result = null;
        try {
            UrlSigner signer = new UrlSigner(CRYPTO_KEY);
            result = signer.signRequest(prefix + stripped);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
