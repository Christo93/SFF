package dashboardActivities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.software.R;
import com.example.software.library.ConnectionDetector;
import com.example.software.library.UserFunctions;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Events extends FragMenu {

    private String TAG = "event activity";
    private static String KEY_SUCCESS = "success";
    private static final String KEY_EVENTID = "event_id";
    private static final String KEY_EVENT_NAME = "event_name";
    private static final String KEY_EVENT_DESCRIPTION = "event_description";
    private static final String KEY_EVENT_DATE = "event_date";
    private ArrayList<Event> mEvents;
    private int num_events = 0;

    // Event details

    HashMap<String, String> data;
    SQLiteDatabase db;
    ListView eventlist;
    JSONArray json_members;

    final UserFunctions userFunction = new UserFunctions();

    ImageView networkImage;
    TextView network;
    TextView t;

    View view;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    public Events() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // setHasOptionsMenu(true);


        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_events, container, false);
        //font
        t = (TextView) view.findViewById(R.id.event_title);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        cd = new ConnectionDetector(getActivity().getApplicationContext());

        eventlist = (ListView) view.findViewById(R.id.EventsList);
        network = (TextView) view.findViewById(R.id.network);
        networkImage = (ImageView) view.findViewById(R.id.imageView2);
        network.setVisibility(View.INVISIBLE);
        networkImage.setVisibility(View.INVISIBLE);
        //new GetEvents().execute("");

        eventlist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub

                Intent dash = new Intent(getActivity().getApplicationContext(),
                        ViewEvent.class);
                dash.putExtra("event_id", mEvents.get(position).getEvent_id());
                dash.putExtra("event_name", mEvents.get(position)
                        .getEvent_name());
                dash.putExtra("event_description", mEvents.get(position)
                        .getEvent_decs());
                dash.putExtra("event_date", mEvents.get(position)
                        .getEvent_date());

                startActivity(dash);

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }
                return false;
            }
        });

        return view;

    }

    private class GetEvents extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String status = null;

            isInternetPresent = cd.isConnectingToInternet();
            //if (isInternetPresent) {
            JSONObject json = userFunction.Events("event");

            // check for registration response

            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        json_members = json.getJSONArray("event");

                        for (int i = 0; i < json_members.length(); i++) {
                            JSONObject fetch_events = json_members
                                    .getJSONObject(i);
                            if (checkstore(fetch_events
                                    .getString(KEY_EVENTID)) == false) {
                                String dirty_date = fetch_events
                                        .getString(KEY_EVENT_DATE);
                                String clean_date = dirty_date.substring(0,
                                        10);
                                Event e = new Event(
                                        fetch_events.getString(KEY_EVENTID),
                                        fetch_events
                                                .getString(KEY_EVENT_NAME),
                                        fetch_events
                                                .getString(KEY_EVENT_DESCRIPTION),
                                        clean_date);
                                EventStore.get(getActivity()).addEvent(e);
                            }

                        }
                        num_events = EventStore.get(getActivity())
                                .getEvents().size();

                    } else {
                        // Error in registration
                        status = "fail";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                status = "error";
            }

			/*} else {
				Log.i("er", "er");
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						// error.setText("No internet Access");
						network.setVisibility(View.INVISIBLE);
						networkImage.setVisibility(View.INVISIBLE);
						network.setText("Unable to connect to the network");
					}
				});
			}*/

            return status;// return login status
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contentEquals("success")) {
                // if(mEvents.size() != num_events){
                if (json_members.length() == 0) {
                    // Log.i("empty", "events");
                    Toast.makeText(getActivity().getApplicationContext(),
                            "There are no Upcoming Events For now",
                            Toast.LENGTH_SHORT).show();
                }
                mEvents = EventStore.get(getActivity()).getEvents();
                // }

                EventsAdapter adapter = new EventsAdapter(mEvents);
                eventlist.setAdapter(adapter);

            } else if (result.contentEquals("fail")) {
                Log.i("sorry ", TAG);
            } else if (result.contentEquals("error")) {
                Log.i("sorry1 ", TAG);
                // btnyes.setVisibility(View.INVISIBLE);
                // Toast.makeText(ViewEvent.this,
                // "your are already attending this event",
                // Toast.LENGTH_SHORT).show();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // error.setText("No internet Access");
                        network.setVisibility(View.VISIBLE);
                        networkImage.setVisibility(View.VISIBLE);
                        network.setText("Unable to connect to the network");
                    }
                });
            } else {
                // btnyes.setVisibility(View.INVISIBLE);
                // Toast.makeText(ViewEvent.this,
                // "your are already attending this event",
                // Toast.LENGTH_SHORT).show();
                Log.i("sorry2 ", TAG);
            }
        }

        @Override
        protected void onPreExecute() {
            // before logging in

        }
    }

    private class EventsAdapter extends ArrayAdapter<Event> {
        public EventsAdapter(ArrayList<Event> events) {
            super(getActivity(), android.R.layout.simple_list_item_1, events);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.event_list_row, null);

                Event c = getItem(position);

                TextView eventnametext = (TextView) convertView
                        .findViewById(R.id.tvEventName);
                eventnametext.setText(c.getEvent_name());
                TextView eventdatetext = (TextView) convertView
                        .findViewById(R.id.tvEventDate);
                eventdatetext.setText(c.getEvent_date());

            }
            return convertView;

        }

    }

    private boolean checkstore(String id) {
        boolean check = false;
        for (int i = 0; i < EventStore.get(getActivity()).getEvents().size(); i++) {
            if (EventStore.get(getActivity()).getEvent(id) != null) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetEvents().execute("");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(EventStore.get(getActivity()).getEvents().size() != 0){
            EventStore.get(getActivity()).getEvents().clear();
            mEvents.clear();
        }
    }
	
	/*back button
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{       
	 if(keyCode==KeyEvent.KEYCODE_BACK)

	          {
		  	//		Intent dash = new Intent(getActivity().getApplicationContext(),
			//		FragMenu.class); //change class to activity u want to go back to.
	          }
	return false;
	    }*/



}
