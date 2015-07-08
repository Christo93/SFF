package dashboardActivities;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.software.DashboardFragment;
import com.example.software.R;



import com.example.software.library.DatabaseHandler;



import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewEvent extends Menu {

    private String TAG = "View Events";

    Button btnBack, btnyes, btnNo;
    TextView E_name, E_desc, E_date;
    String eventname,eventdes,eventdate,eventid;
    HashMap<String, String> data;
    TextView t;
    UserFunctions userFunction = new UserFunctions();
    int counter=0; //to cater for button pressed
    private static String KEY_SUCCESS = "success";
    private static final String KEY_UID = "user_id";
    private static final String KEY_EVENTID = "event_id";

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.events_view);

        session = new UserSessionManager(getApplicationContext());

        //fonts
        t = (TextView) findViewById(R.id.event_v);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(myCustomFont);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getting intent values
        Intent dash = getIntent();
        eventid = dash.getStringExtra("event_id");
        eventname = dash.getStringExtra("event_name");
        eventdes = dash.getStringExtra("event_description");
        eventdate = dash.getStringExtra("event_date");

        E_name = (TextView) findViewById(R.id.eventname);
        E_desc = (TextView) findViewById(R.id.e_details);
        E_date = (TextView) findViewById(R.id.eventtime);

        btnyes = (Button) findViewById(R.id.btnYES);
        btnNo = (Button) findViewById(R.id.btnNO);



        //setting textview.
        E_name.setText(eventname);
        E_desc.setText(eventdes);
        E_date.setText(eventdate);

        btnyes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (counter==0){
                    new LongOperation().execute("");// start registration
                    counter=1;
                }else{

                    btnyes.setVisibility(View.INVISIBLE);
                    Toast.makeText(ViewEvent.this, "your are already attending this event", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), Event.class);
                NavUtils.navigateUpTo(this, i);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String status = null;

            HashMap<String, String> user = session.getUserDetails();


            Log.i(TAG, "eventid ->"+eventid+" userid->"+user.get(UserSessionManager.KEY_USERID));

            String usid = user.get(UserSessionManager.KEY_USERID);

            JSONObject json = userFunction.AttendEvent(usid, eventid);

            // check for registration response


            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        // user successfully registred
                        // Store user details in SQLite Database

                        //	JSONObject json_user = json.getJSONObject("event_user");
                        //Toast.makeText(ViewEvent.this, "your are attending event", Toast.LENGTH_SHORT).show();


                    } else {
                        // Error in registration
                        status = "fail";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                status = "error";
            }

            return status;// return login status
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contentEquals("success")) {

                btnyes.setVisibility(View.INVISIBLE);
                // Launch Dashboard Screen
				/*Intent dashboard = new Intent(getApplicationContext(),
						ViewEvent.class);
				dashboard.putExtra("event_id", data.get("event_id"));
				dashboard.putExtra("event_name", data.get("event_name"));
				dashboard.putExtra("event_description", data.get("event_description"));
				dashboard.putExtra("event_date", data.get("event_date"));
				// Close all views before launching Dashboard
				dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(dashboard);
				// Close Login Screen
				finish();*/

                //	btnyes.setVisibility(View.INVISIBLE);
                Toast.makeText(ViewEvent.this, "Thanks for accepting the invite", Toast.LENGTH_SHORT).show();

            } else if (result.contentEquals("fail")) {
                Log.i("sorry ", TAG);
            }
            else if (result.contentEquals("error")) {

                //btnyes.setVisibility(View.INVISIBLE);
                //Toast.makeText(ViewEvent.this, "your are already attending this event", Toast.LENGTH_SHORT).show();
            }else {
                //btnyes.setVisibility(View.INVISIBLE);
                //Toast.makeText(ViewEvent.this, "your are already attending this event", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // before logging in

        }



    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    Events.class); //change class to activity u want to go back to.
        }
        return true;
    }

}
