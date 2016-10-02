package com.android.quakereport.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ganesh on 29-09-2016.
 */
public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /** Sample JSON response for a USGS query */
    public static ArrayList<Earthquake> extractEarthquakes(String strngUrl) {

        //Create url object from url string
        URL url = createUrl(strngUrl);


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // / Create a fake list of earthquakes.
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        Earthquake earthquake ;
        try {
            JSONObject jsonObject= new JSONObject(jsonResponse);
            JSONArray features = jsonObject.optJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                earthquake = new Earthquake();
                JSONObject feature = (JSONObject) features.get(i);
                JSONObject properties = feature.getJSONObject("properties");
                earthquake.setMagnitue(properties.getString("mag"));
                earthquake.setLocation(properties.getString("place"));
                earthquake.setURL(properties.getString("url"));
                long timeInMilliseconds = Long.parseLong(properties.getString("time"));
                Date dateObject = new Date(timeInMilliseconds);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMM.yy-HH:mm:ss");
                earthquake.setTime(dateFormatter.format(dateObject));
                earthquakes.add(earthquake);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakes;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
}
