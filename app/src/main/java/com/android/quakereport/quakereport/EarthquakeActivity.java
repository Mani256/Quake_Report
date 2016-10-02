package com.android.quakereport.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
    private static final String USGS_REQUEST_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";
    ListView earthquakeListView;
    TextView mEmptyStateTextView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        earthquakeListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        earthquakeListView.setEmptyView(mEmptyStateTextView);
        LoaderManager loaderManager = getLoaderManager();
        /**
         * Check for Internet connetion for the app and
         * display a message when there is no Active connection to Internet
         */
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager.initLoader(1, null, this); //Starts the Loader only when there is internet
        } else {
           mEmptyStateTextView.setText("No Internet Connection");
            mProgressBar.setVisibility(View.GONE);
        }
        /**
         * checking for an active Wifi connection
         */
        //boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

    }

    /*private class EarthquakeAsyncTask extends AsyncTask<String,Void,List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... params) {
            return QueryUtils.extractEarthquakes(USGS_REQUEST_URL);
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            updateUI(earthquakes);
        }
    }
*/
    private void updateUI(List earthquakes) {


        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdapter adapter = new EarthquakeAdapter(this);
        // Clear the adapter of previous earthquake data
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            adapter.addAll(earthquakes);
        }
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "Loader creation request");
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.v(LOG_TAG, "Finished loading Data");
        mEmptyStateTextView.setText("No Earthquakes Data found");
        mProgressBar.setVisibility(View.GONE);
        updateUI(earthquakes);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Log.v(LOG_TAG, "Loader Reset took place");
        updateUI(new ArrayList<Earthquake>());
    }
}
