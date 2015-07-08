package discussion;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.software.DashboardFragment;
import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;

import dashboardActivities.EditProfile;
import dashboardActivities.Menu;

public class ViewReplyDiscussion extends Menu {

    String dis_id, dis_sub,discuss,dis_date,dis_userID,user_id,username;
    TextView subject,author,dateAdded,Details;
    Button replyDis, Back;
    ListView replyList;
    TextView t;

    ListAdapter adapter;

    UserSessionManager session;

    HashMap<String, String> data;

    private String TAG = "view and reply ";

    private static String KEY_SUCCESS = "success";
    public static final String KEY_REPLYID = "reply_id";
    public static final String KEY_REPLY = "reply";
    public static final String KEY_DIS_ID = "dis_id";
    public static final String KEY_REPLY_DATE = "reply_date";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER = "username";


    Button backtoParent;

    final UserFunctions userFunction = new UserFunctions();
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.view_discussion);
        //fonts
        t = (TextView) findViewById(R.id.dis_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);
        session = new UserSessionManager(getApplicationContext());
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        subject = (TextView) findViewById(R.id.tvsubject);
        author = (TextView) findViewById(R.id.tvauthor);
        dateAdded = (TextView) findViewById(R.id.tvdate_added);
        Details = (TextView) findViewById(R.id.tvdetails);


        replyList = (ListView) findViewById(R.id.ReplyList);
        replyDis = (Button) findViewById(R.id.btn_reply_discussion);

        backtoParent = (Button) findViewById(R.id.btnBackParent);
        if(session.checkLogin())
            finish();

        backtoParent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),
                        Discussion.class);
                startActivity(i);
            }
        });

        replyDis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(),
                        ReplyToDiscussion.class);
                i.putExtra("dis_id",dis_id);
                i.putExtra("dis_sub",dis_sub);
                i.putExtra("discuss",discuss);
                i.putExtra("disdate",dis_date );
                i.putExtra("dis_userID",dis_userID );
                i.putExtra("username",username );

                startActivity(i);
            }
        });





        oslist = new ArrayList<HashMap<String, String>>();

        Intent dash = getIntent();
        dis_id = dash.getStringExtra("dis_id");
        dis_sub = dash.getStringExtra("dis_sub");
        discuss = dash.getStringExtra("discuss");
        dis_date = dash.getStringExtra("disdate");
        dis_userID = dash.getStringExtra("dis_userID");
        username = dash.getStringExtra("username");

        subject.setText(dis_sub);
        author.setText(username);
        dateAdded.setText(dis_date);
        Details.setText(discuss);
        oslist.clear();
        new GetReplies().execute("");


    }



    private class GetReplies extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            String status = null;


            // check for registration response
            JSONObject jsondiscussion = userFunction.GetReplyDisscussion(dis_id);

            try {
                if (jsondiscussion.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = jsondiscussion.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        JSONArray jsonGetDis = jsondiscussion.getJSONArray("dis_reply");


                        for (int i = 0; i < jsonGetDis.length(); i++) {

                            JSONObject fetch = jsonGetDis.getJSONObject(i);

                            String reply_id = fetch.getString(KEY_REPLYID);
                            String reply = fetch.getString(KEY_REPLY);
                            String dis_id = fetch.getString(KEY_DIS_ID);
                            String reply_date = fetch.getString(KEY_REPLY_DATE);
                            String user_id = fetch.getString(KEY_USER_ID);
                            String replyuser = fetch.getString(KEY_USER);

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(KEY_REPLYID, reply_id);
                            map.put(KEY_REPLY, reply);
                            map.put(KEY_DIS_ID, dis_id);
                            map.put(KEY_REPLY_DATE, reply_date);
                            map.put(KEY_USER_ID, user_id);
                            map.put(KEY_USER, replyuser);
                            oslist.add(map);


                        }



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


                adapter = new SimpleAdapter(getApplicationContext(), oslist,
                        R.layout.reply_list_row,
                        new String[] { KEY_USER,KEY_REPLY ,KEY_REPLY_DATE }, new int[] {
                        R.id.tvReplyUser,R.id.tvReplyDetails,R.id.tvReplyDate });
                replyList.setAdapter(adapter);


            } else if (result.contentEquals("fail")) {
                Log.i("sorry ", TAG);
            } else if (result.contentEquals("error")) {
                Log.i("error ", TAG);

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
