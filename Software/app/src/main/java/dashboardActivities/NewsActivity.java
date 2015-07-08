package dashboardActivities;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.software.LoginAct;
import com.example.software.R;
import com.example.software.library.ConnectionDetector;
import com.example.software.library.UserFunctions;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewsActivity extends Activity {

    private String TAG = "News activity";

    private static String KEY_SUCCESS = "success";
    public static final String KEY_NEWS_ID = "news_id";
    public static final String KEY_NEWS_SUBJECT = "news_subject";
    public static final String KEY_NEWS_DETAILS = "news_details";
    public static final String KEY_NEWS_TIME = "news_time";

    final UserFunctions userFunction = new UserFunctions();

    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    // news

    // internet status detection
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    ListView newslist;
    TextView error;
    ImageView networkImage;
    JSONArray jsonGetNews;

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news);

        t = (TextView) findViewById(R.id.news_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        cd = new ConnectionDetector(getApplicationContext());
        newslist = (ListView) findViewById(R.id.NewsList);
        error = (TextView) findViewById(R.id.network);
        networkImage = (ImageView) findViewById(R.id.imageView2);
        error.setVisibility(View.INVISIBLE);
        networkImage.setVisibility(View.INVISIBLE);
        oslist = new ArrayList<HashMap<String, String>>();

        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        // private ProgressDialog pDialog;

        @Override
        protected String doInBackground(String... params) {
            Log.i("long operation..", TAG);
            String status = "internet";

            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                // Internet Connection is Present
                // make HTTP requests
                JSONObject jsonnews = userFunction.News();
                try {
                    if (jsonnews.getString(KEY_SUCCESS) != null) {// if user
                        // found
                        // then
                        // login

                        status = "Success";
                        Log.i("user found..", TAG);
                        String res = jsonnews.getString(KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1) {
                            // user successfully logged in
                            // Store user details in SQLite Database
                            Log.i("success found..", TAG);
                            jsonGetNews = jsonnews.getJSONArray("news");

                            for (int i = 0; i < jsonGetNews.length(); i++) {

                                JSONObject fetch_event = jsonGetNews
                                        .getJSONObject(i);
                                // Storing JSON item in a Variable
                                String newsid = fetch_event
                                        .getString(KEY_NEWS_ID);
                                String new_sub = fetch_event
                                        .getString(KEY_NEWS_SUBJECT);
                                String news_details = fetch_event
                                        .getString(KEY_NEWS_DETAILS);
                                String newstime = fetch_event
                                        .getString(KEY_NEWS_TIME);
                                // Adding value HashMap key => value
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(KEY_NEWS_ID, newsid);
                                map.put(KEY_NEWS_SUBJECT, new_sub);
                                map.put(KEY_NEWS_DETAILS, news_details);
                                map.put(KEY_NEWS_TIME, newstime);
                                oslist.add(map);

                            }

                        } else {
                            // incorrect username and/or password
                            status = "fail";
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();// error
                    status = "error";
                }
                // showAlertDialog(AndroidDetectInternetConnectionActivity.this,
                // "Internet Connection",
                // "You have internet connection", true);
            } else {
                // Internet connection is not present
                // Ask user to connect to Internet
                // showAlertDialog(AndroidDetectInternetConnectionActivity.this,
                // "No Internet Connection",
                // "You don't have internet connection.", false);
                //error.setText("No internet Access");
                Log.i("er", "er");

                runOnUiThread(new Runnable() {

                    public void run() {
                        error.setVisibility(View.VISIBLE);
                        networkImage.setVisibility(View.VISIBLE);
                        error.setText("Unable to connect to the network");
                    }
                });
            }


            return status;// return login status
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contentEquals("Success")) {

                if (jsonGetNews.length() == 0) {
                    // Log.i("empty", "events");
                    Toast.makeText(getApplicationContext(),
                            "There are no News For now", Toast.LENGTH_SHORT)
                            .show();
                }
                ListAdapter adapter = new SimpleAdapter(
                        getApplicationContext(), oslist,
                        R.layout.news_list_row, new String[] {
                        KEY_NEWS_SUBJECT,
                        KEY_NEWS_TIME }, new int[] {
                        R.id.tvNewsSubject,
                        R.id.tvNewsTime });
                newslist.setAdapter(adapter);


            } else if (result.contentEquals("fail")) {
                Log.i("fail", "fail");
            } else if (result.contentEquals("error")) {
                Log.i("er", "er");
                error.setText("No internet Access");

            } else {
                Log.i("we", "we");
            }
        }

        @Override
        protected void onPreExecute() {
            // before logging in

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    LoginAct.class); //change class to activity u want to go back to.
            startActivity(dash);
        }
        return true;
    }
}
