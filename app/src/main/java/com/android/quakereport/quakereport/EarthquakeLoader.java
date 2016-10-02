package com.android.quakereport.quakereport;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by ganesh on 02-10-2016.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    public static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    String requestURL;
    public EarthquakeLoader(Context context, String url) {

        super(context);
        requestURL = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG,"Started the Loader");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.v(LOG_TAG,"Load in Background method call");
        return QueryUtils.extractEarthquakes(requestURL);
    }
}
