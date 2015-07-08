package dashboardActivities;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;













import com.example.software.DashboardFragment;
import com.example.software.R;
import com.example.software.R.id;
import com.example.software.R.layout;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfile extends Menu {

    Button btnBack, btnSave;
    EditText fname, lname, email, town, country, postcode, mobile;
    HashMap<String, String> data;
    String u_fname, u_lname, u_mobile, u_postcode, u_country, u_town, u_email;
    String uid;
    TextView save_details;
    UserFunctions userFunction = new UserFunctions();
    TextView t;
    Fragment profile = new UserProfile();

    UserSessionManager session;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static final String KEY_UID = "user_id";
    private static final String KEY_FNAME = "user_first_name";
    private static final String KEY_LNAME = "user_last_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_TOWN = "user_town";
    private static final String KEY_COUNTRY = "user_country";
    private static final String KEY_PCODE = "user_post_code";
    private static final String KEY_PHONE = "user_phone";
    private static final String KEY_MOBILE = "user_mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);
        //fonts
        t = (TextView) findViewById(R.id.editProf);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);
        session = new UserSessionManager(getApplicationContext());

        getActionBar().setDisplayHomeAsUpEnabled(true);

        fname = (EditText) findViewById(R.id.EditFName);
        lname = (EditText) findViewById(R.id.EditLastName);
        email = (EditText) findViewById(R.id.EditEmail);
        town = (EditText) findViewById(R.id.EditTown);
        country = (EditText) findViewById(R.id.EditCountry);
        postcode = (EditText) findViewById(R.id.EditPostal);
        mobile = (EditText) findViewById(R.id.EditMobile);
        save_details = (TextView) findViewById(R.id.error);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSavePassword);

        if(session.checkLogin())
            finish();

        HashMap<String, String> user = session.getUserDetails();
        fname.setText(user.get(UserSessionManager.KEY_FNAME));
        lname.setText(user.get(UserSessionManager.KEY_LNAME));
        email.setText(user.get(UserSessionManager.KEY_EMAIL));

        town.setText(user.get(UserSessionManager.KEY_TOWN));
        country.setText(user.get(UserSessionManager.KEY_COUNTRY));
        postcode.setText(user.get(UserSessionManager.KEY_PCODE));
        mobile.setText(user.get(UserSessionManager.KEY_MOBILE));

        uid = user.get(UserSessionManager.KEY_USERID);

        Log.i("id", uid);

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), DashboardFragment.class);
                startActivity(i);



            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                u_fname = fname.getText().toString();
                u_lname = lname.getText().toString();
                u_email = email.getText().toString();
                u_town = town.getText().toString();
                u_country = country.getText().toString();
                u_postcode = postcode.getText().toString();
                u_mobile = mobile.getText().toString();

                new LongOperation().execute("");
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String status = null;




            JSONObject json = userFunction.updateDetails(uid, u_fname, u_lname,
                    u_email, u_town, u_country, u_postcode, u_mobile);// register
            // user
            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {

                        JSONObject json_user = json.getJSONObject("user");
                        DatabaseHandler db = new DatabaseHandler(
                                getApplicationContext());
                        db.updateDetails(json.getString(KEY_UID),
                                json_user.getString(KEY_FNAME),
                                json_user.getString(KEY_LNAME),
                                json_user.getString(KEY_EMAIL),
                                json_user.getString(KEY_TOWN),
                                json_user.getString(KEY_COUNTRY),
                                json_user.getString(KEY_PCODE),
                                json_user.getString(KEY_PHONE),
                                json_user.getString(KEY_MOBILE));
                    } else {
                        // Error in update
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
                save_details.setText("Details Saved...");// inform user on
                // progress
                btnSave.setEnabled(false);
                Intent dash = new Intent(getApplicationContext(),
                        DashboardFragment.class);
                // Close all views before launching Dashboard
                dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
                finish();

            } else if (result.contentEquals("fail")) {
                save_details
                        .setText("error in saving details");
            } else {
                save_details
                        .setText("Oops! Seems like we have encountered a network error!");
            }
        }

        @Override
        protected void onPreExecute() {
            // before logging in

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        //back button
        public boolean onKeyDown(int keyCode, KeyEvent event)
        {
            if(keyCode==KeyEvent.KEYCODE_BACK)

            {
                Intent dash = new Intent(getApplicationContext(),
                        DashboardFragment.class); //change class to activity u want to go back to.
                startActivity(dash);

            }
            return true;
        }
    }

}
