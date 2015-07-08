package discussion;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.software.DashboardFragment;
import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;

import dashboardActivities.Event;
import dashboardActivities.Events;
import dashboardActivities.FacebookFeeds;
import dashboardActivities.ForgetPassword;
import dashboardActivities.LogOut;
import dashboardActivities.MembershipDetails;
import dashboardActivities.Menu;
import dashboardActivities.News;
import dashboardActivities.UserProfile;
import dashboardActivities.Video;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddDiscussion extends Menu {

    private String TAG = "Add Discussion activity";

    TextView author_user;
    EditText subject, details;
    Button Add, Back, Reply;
    String asubject, aauthor_user, adate_added, adetails, user_id;
    HashMap<String, String> data;
    TextView t;

    private static String KEY_SUCCESS = "success";

    UserSessionManager session;
    final UserFunctions userFunction = new UserFunctions();

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.discussion_detail);
        //fonts
        t = (TextView) findViewById(R.id.dis_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        session = new UserSessionManager(getApplicationContext());


        subject = (EditText) findViewById(R.id.tvsubject);
        author_user = (TextView) findViewById(R.id.tvauthor);

        details = (EditText) findViewById(R.id.tvdetails);

        Back = (Button) findViewById(R.id.btnBackToViewDis);

        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent dash = new Intent(getApplicationContext(),
                        Discussion.class); //change class to activity u want to go back to.
                startActivity(dash);


            }
        });
        Add = (Button) findViewById(R.id.btn_add_discuss);

        if(session.checkLogin())
            finish();



        HashMap<String, String> user = session.getUserDetails();
        user_id = user.get(UserSessionManager.KEY_USERID);
        Log.i("user id", user.get(UserSessionManager.KEY_USERID));
        author_user.setText(user.get(UserSessionManager.KEY_NAME));

        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                asubject = subject.getText().toString();
                adetails = details.getText().toString();

                new LongOperation().execute("");
            }
        });

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        final AlertDialog.Builder adb = new AlertDialog.Builder(
                AddDiscussion.this);
        @Override
        protected String doInBackground(String... params) {
            String status = null;

            JSONObject json = userFunction.AddDiscussion(asubject, adetails,
                    user_id);

            // check for registration response

            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {


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

                //Launch Discussion Screen
                adb.setTitle("Discussion Successful");
                adb.setMessage("Your Discussion is successfully added");
                adb.setPositiveButton("Ok", null);
                adb.show();


                Intent dash = new Intent(getApplicationContext(),
                        Discussion.class);

                startActivity(dash);



                // btnyes.setVisibility(View.INVISIBLE);

            } else if (result.contentEquals("fail")) {
                Log.i("sorry ", TAG);
            } else if (result.contentEquals("error")) {

                adb.setTitle("Discussion Error");
                adb.setMessage("There was an error in adding discussion");
                adb.setPositiveButton("Ok", null);
                adb.show();
            } else {
                // btnyes.setVisibility(View.INVISIBLE);
                // Toast.makeText(ViewEvent.this,
                // "your are already attending this event",
                // Toast.LENGTH_SHORT).show();
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
                    Discussion.class); //change class to activity u want to go back to.
            startActivity(dash);
        }
        return true;
    }



}
