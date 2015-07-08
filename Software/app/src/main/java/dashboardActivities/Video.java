package dashboardActivities;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.software.R;
import com.example.software.library.ConnectionDetector;
import com.example.software.library.UserFunctions;

//====================================

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Video extends FragMenu {

    private String TAG = "video activity";

    TextView vidname, viddateadd, vidimg;
    TextView t;
    ListView videolist;

    // videos
    private static String KEY_SUCCESS = "success";
    private static final String KEY_VIDID = "vid_id";
    private static final String KEY_VID_NAME = "vid_name";
    private static final String KEY_VID_DATE_ADD = "vid_date_add";
    private static final String KEY_VID_URL = "vid_url";
    private static final String KEY_VID_URL_ID = "vid_url_id";
    private static final String KEY_VID_IMG = "vid_img";


    final UserFunctions userFunction = new UserFunctions();

    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    JSONObject jsonvideo;
    View view;
    TextView error;
    ImageView networkImage;

    public Video() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.fragment_video, container, false);
        //fonts
        t = (TextView) view.findViewById(R.id.video_title);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        cd = new ConnectionDetector(getActivity().getApplicationContext());


        videolist = (ListView) view.findViewById(R.id.VideoList);

        error = (TextView) view.findViewById(R.id.network);
        networkImage = (ImageView) view.findViewById(R.id.imageView2);
        error.setVisibility(View.INVISIBLE);
        networkImage.setVisibility(View.INVISIBLE);
        oslist = new ArrayList<HashMap<String, String>>();
        new LongOperation().execute("");

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

    private class LongOperation extends AsyncTask<String, Void, String> {

        //	private ProgressDialog pDialog;

        @Override
        protected String doInBackground(String... params) {
            Log.i("long operation..", TAG);
            String status = "internet";


            // email
            isInternetPresent = cd.isConnectingToInternet();
            if(isInternetPresent){
                jsonvideo = userFunction.Video();

                try {
                    if (jsonvideo.getString(KEY_SUCCESS) != null) {// if user found then
                        // login

                        status = "Success";
                        Log.i("user found..", TAG);
                        String res = jsonvideo.getString(KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1) {
                            // user successfully logged in
                            // Store user details in SQLite Database
                            Log.i("success found..", TAG);
                            JSONArray jsonGet = jsonvideo.getJSONArray("video");

                            for(int i=0;i<jsonGet.length();i++){

                                JSONObject fetch_event = jsonGet.getJSONObject(i);
                                // Storing  JSON item in a Variable
                                String vid_id = fetch_event.getString(KEY_VIDID);
                                String vid_name = fetch_event.getString(KEY_VID_NAME);
                                String vid_date = fetch_event.getString(KEY_VID_DATE_ADD);
                                String vid_url = fetch_event.getString(KEY_VID_URL);
                                String vid_url_id = fetch_event.getString(KEY_VID_URL_ID);
                                String vid_img = fetch_event.getString(KEY_VID_IMG);
                                // Adding value HashMap key => value
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(KEY_VIDID, vid_id);
                                map.put(KEY_VID_NAME, vid_name);
                                map.put(KEY_VID_DATE_ADD, vid_date);
                                map.put(KEY_VID_URL, vid_url);
                                map.put(KEY_VID_URL_ID, vid_url_id);
                                map.put(KEY_VID_IMG, vid_img);
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


            }else{
                Log.i("er", "er");
                getActivity().runOnUiThread(new Runnable() {
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

                if (jsonvideo.length() ==0){
                    //Log.i("empty", "events");
                    Toast.makeText(getActivity().getApplicationContext(), "There are no Videos Present", Toast.LENGTH_SHORT).show();
                }

                ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), oslist,
                        R.layout.video_list_row,
                        new String[] { KEY_VID_NAME, KEY_VID_DATE_ADD,KEY_VID_URL_ID }, new int[] {
                        R.id.tvVideoName, R.id.tvVideoDate,
                        R.id.tvVideoUrlId});
                videolist.setAdapter(adapter);

                videolist.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                            long arg3) {
                        // TODO Auto-generated method stub


                        for (int i=0;i<videolist.getCount();i++){
                            if (position ==i){
                                Intent dash = new Intent(getActivity().getApplicationContext(),
                                        Video_get.class);
                                dash.putExtra("vid_id", oslist.get(position).get(KEY_VIDID));
                                dash.putExtra("vid_url_id",oslist.get(position).get(KEY_VID_URL_ID));

                                startActivity(dash);

                            }
                        }



                    }
                });



            } else if (result.contentEquals("fail")) {

            } else if (result.contentEquals("error")) {


            } else {

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

	/*/back button
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
