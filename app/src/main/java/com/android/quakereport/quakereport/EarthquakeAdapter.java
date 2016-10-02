package com.android.quakereport.quakereport;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.quakereport.quakereport.Earthquake;
import com.android.quakereport.quakereport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganesh on 29-09-2016.
 */
public class EarthquakeAdapter extends ArrayAdapter {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    public EarthquakeAdapter(Activity context) {
        super(context,0);
    }

   @Override
    public View getView(int position,View convertView, ViewGroup parent){
       View listItemView = convertView;
       if(listItemView == null) {
           listItemView = LayoutInflater.from(getContext()).inflate(
                   R.layout.list_item, parent, false);
       }

       // Get the {@link AndroidFlavor} object located at this position in the list
       Earthquake earthquake = (Earthquake) getItem(position);

       // Find the TextView in the list_item.xml layout with the ID version_name
       TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.earthquake_mag);
       // Get the version name from the current AndroidFlavor object and
       // set this text on the name TextView
       magnitudeTextView.setText(earthquake.getMagnitue());

       // Find the TextView in the list_item.xml layout with the ID version_number
       TextView numberTextView = (TextView) listItemView.findViewById(R.id.earthquake_loc);
       // Get the version number from the current AndroidFlavor object and
       // set this text on the number TextView
       numberTextView.setText(earthquake.getLocation());

       // Find the ImageView in the list_item.xml layout with the ID list_item_icon
       TextView timeTextView = (TextView) listItemView.findViewById(R.id.earthquake_time);
       // Get the version number from the current AndroidFlavor object and
       // set this text on the number TextView
       timeTextView.setText(earthquake.getTime());


       listItemView = (View) timeTextView.getParent();



       listItemView.setTag(earthquake.getURL());
       // Return the whole list item layout (containing 2 TextViews and an ImageView)
       // so that it can be shown in the ListView
       return listItemView;
   }
}
