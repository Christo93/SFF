package discussion;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.software.DashboardFragment;
import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;

import dashboardActivities.ChangePassword;
import dashboardActivities.ViewEvent;
import dashboardActivities.ViewNews;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Discussion extends Activity {

    private String TAG = "discussion ";

    Button addDis;

    HashMap<String, String> data;
    SQLiteDatabase db;
    ListView discussionlist;
    JSONArray jsonGetDis;
    TextView t;

    String aauthor_user;
    View view;
    private static String KEY_SUCCESS = "success";
    public static final String KEY_DISID = "dis_id";
    public static final String KEY_DIS_SUBJECT = "dis_subject";
    public static final String KEY_DISCUSSION = "discussion";
    public static final String KEY_DIS_DATE = "dis_date";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "username";

    final UserFunctions userFunction = new UserFunctions();
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.fragment_discussion_list, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_discussion:
                Intent i = new Intent(getApplicationContext(), AddDiscussion.class);
                startActivity(i);
                return true;
            case R.id.dis_home:
                Intent j = new Intent(getApplicationContext(),
                        DashboardFragment.class);
                startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_discuss);
        t = (TextView) findViewById(R.id.dis_title);
        Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/kaushan_script.otf");
        t.setTypeface(font);

        discussionlist = (ListView) findViewById(R.id.DiscussionList);

        oslist = new ArrayList<HashMap<String, String>>();
        new LongOperation().execute("");

    };

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String status = null;

            // check for registration response
            JSONObject jsondiscussion = userFunction.Discussion();

            try {
                if (jsondiscussion.getString(KEY_SUCCESS) != null) {// if
                    // registration
                    // successful
                    status = "success";
                    String res = jsondiscussion.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        jsonGetDis = jsondiscussion.getJSONArray("discussion");

                        for (int i = 0; i < jsonGetDis.length(); i++) {

                            JSONObject fetch = jsonGetDis.getJSONObject(i);

                            String dis_id = fetch.getString(KEY_DISID);
                            String dis_sub = fetch.getString(KEY_DIS_SUBJECT);
                            String discuss = fetch.getString(KEY_DISCUSSION);
                            String disdate = fetch.getString(KEY_DIS_DATE);
                            String dis_userID = fetch.getString(KEY_USER_ID);
                            String dis_username = fetch
                                    .getString(KEY_USER_NAME);

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(KEY_DISID, dis_id);
                            map.put(KEY_DIS_SUBJECT, dis_sub);
                            map.put(KEY_DISCUSSION, discuss);
                            map.put(KEY_DIS_DATE, disdate);
                            map.put(KEY_USER_ID, dis_userID);
                            map.put(KEY_USER_NAME, dis_username);
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
                if (jsonGetDis.length() == 0) {
                    // Log.i("empty", "events");
                    Toast.makeText(getApplicationContext(),
                            "There is no Discussion Present",
                            Toast.LENGTH_SHORT).show();
                }
                ListAdapter adapter = new SimpleAdapter(
                        getApplicationContext(), oslist,
                        R.layout.discuss_list_row, new String[] {
                        KEY_DIS_SUBJECT, KEY_USER_NAME, KEY_DIS_DATE },
                        new int[] { R.id.tvDisSubject, R.id.tvDisUser,
                                R.id.tvDisDate });
                discussionlist.setAdapter(adapter);

                discussionlist
                        .setOnItemClickListener(new OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> arg0,
                                                    View arg1, int position, long arg3) {
                                // TODO Auto-generated method stub

                                for (int i = 0; i < discussionlist.getCount(); i++) {
                                    if (position == i) {
                                        Intent dash = new Intent(
                                                getApplicationContext(),
                                                ViewReplyDiscussion.class);
                                        dash.putExtra(
                                                "dis_id",
                                                oslist.get(position).get(
                                                        KEY_DISID));
                                        dash.putExtra(
                                                "dis_sub",
                                                oslist.get(position).get(
                                                        KEY_DIS_SUBJECT));
                                        dash.putExtra(
                                                "discuss",
                                                oslist.get(position).get(
                                                        KEY_DISCUSSION));
                                        dash.putExtra(
                                                "disdate",
                                                oslist.get(position).get(
                                                        KEY_DIS_DATE));
                                        dash.putExtra(
                                                "dis_userID",
                                                oslist.get(position).get(
                                                        KEY_USER_ID));
                                        dash.putExtra(
                                                "username",
                                                oslist.get(position).get(
                                                        KEY_USER_NAME));

                                        startActivity(dash);

                                    }
                                }

                            }
                        });

            } else if (result.contentEquals("fail")) {
                Log.i("sorry ", TAG);
            } else if (result.contentEquals("error")) {
                Log.i("internt ", TAG);
                Toast.makeText(getApplicationContext(),
                        "Unable to connect to the Network", Toast.LENGTH_SHORT)
                        .show();

            } else {
                Log.i("damm interenet ", TAG);
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

    // back button
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    DashboardFragment.class); // change class to activity u want
            // to go back to.
            startActivity(dash);
        }
        return true;
    }
}
