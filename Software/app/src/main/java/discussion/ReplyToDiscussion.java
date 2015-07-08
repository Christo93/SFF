package discussion;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;

import dashboardActivities.Menu;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class ReplyToDiscussion extends Menu {

    private String TAG = "reply ";

    TextView subject, replyBy;
    EditText replydetails;
    Button Reply, Back;
    TextView t;

    String dis_id, dis_sub, discuss, dis_date, dis_userID, reply_id, reply,
            reply_date, user_id;

    HashMap<String, String> data;
    UserSessionManager session;

    final UserFunctions userFunction = new UserFunctions();

    private static String KEY_SUCCESS = "success";
    public static final String KEY_REPLYID = "reply_id";
    public static final String KEY_REPLY = "reply";
    public static final String KEY_DIS_ID = "dis_id";
    public static final String KEY_REPLY_DATE = "reply_date";
    public static final String KEY_USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.dis_reply);
        //fonts
        t = (TextView) findViewById(R.id.dis_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        session = new UserSessionManager(getApplicationContext());


        Intent dash = getIntent();
        dis_id = dash.getStringExtra("dis_id");
        dis_sub = dash.getStringExtra("dis_sub");
        discuss = dash.getStringExtra("discuss");
        dis_date = dash.getStringExtra("disdate");
        dis_userID = dash.getStringExtra("dis_userID");

        replydetails = (EditText) findViewById(R.id.rply);
        subject = (TextView) findViewById(R.id.subjectreply);
        replyBy = (TextView) findViewById(R.id.reply_author);
        Reply = (Button) findViewById(R.id.btnSubmitReply);
        Back = (Button) findViewById(R.id.btnBackToViewReply);

        //user details
        if(session.checkLogin())
            finish();

        HashMap<String, String> user = session.getUserDetails();

        user_id = user.get(UserSessionManager.KEY_USERID);

        replyBy.setText(user.get(UserSessionManager.KEY_NAME));



        subject.setText(dis_sub);

        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent dash = new Intent(getApplicationContext(),
                        ViewReplyDiscussion.class);
                dash.putExtra("dis_id", dis_id);
                dash.putExtra("dis_sub", dis_sub);
                dash.putExtra("discuss", discuss);
                dash.putExtra("disdate", dis_date);
                dash.putExtra("dis_userID", dis_userID);

                startActivity(dash);

            }
        });


        Reply.setOnClickListener(new View.OnClickListener() {

            AlertDialog.Builder show = new AlertDialog.Builder(
                    ReplyToDiscussion.this);


            @Override
            public void onClick(View arg0) {
                // asubject = subject.getText().toString();
                // adetails = details.getText().toString();
                if (replydetails.length() > 0) {


                    new LongOperation().execute("");
                } else {
                    show.setTitle("Missing Data");
                    show.setMessage("Please ensure that you have filled in all the required data.");
                    show.setPositiveButton("Ok", null);
                    show.show();
                }
            }

        });

    }


    private class LongOperation extends AsyncTask<String, Void, String> {

        final AlertDialog.Builder adb = new AlertDialog.Builder(
                ReplyToDiscussion.this);

        @Override
        protected String doInBackground(String... params) {
            String status = null;

            Log.i("reply details", replydetails
                    .getText().toString());



            JSONObject json = userFunction.ReplyDiscussion(replydetails
                    .getText().toString(), dis_id, user_id);



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

                // Launch Discussion Screen
                adb.setTitle("Success");
                adb.setMessage("You Have successfully replied to the Discussion");
                adb.setPositiveButton("Ok", null);
                adb.show();

                Intent dash = new Intent(getApplicationContext(),
                        ViewReplyDiscussion.class);
                dash.putExtra("dis_id", dis_id);
                dash.putExtra("dis_sub", dis_sub);
                dash.putExtra("discuss", discuss);
                dash.putExtra("disdate", dis_date);
                dash.putExtra("dis_userID", dis_userID);

                startActivity(dash);

                // btnyes.setVisibility(View.INVISIBLE);

            } else if (result.contentEquals("fail")) {
                Log.i("sorry ", TAG);
                adb.setTitle("Discussion Error");
                adb.setMessage("There was an error during Reply");
                adb.setPositiveButton("Ok", null);
                adb.show();
            } else if (result.contentEquals("error")) {

                adb.setTitle("Discussion Error");
                adb.setMessage("There was an Network error ");
                adb.setPositiveButton("Ok", null);
                adb.show();
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
                    ViewReplyDiscussion.class); //change class to activity u want to go back to.
            dash.putExtra("dis_id", dis_id);
            dash.putExtra("dis_sub", dis_sub);
            dash.putExtra("discuss", discuss);
            dash.putExtra("disdate", dis_date);
            dash.putExtra("dis_userID", dis_userID);

            startActivity(dash);
        }
        return true;
    }
}
