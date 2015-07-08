package dashboardActivities;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.software.DashboardFragment;
import com.example.software.R;
import com.example.software.library.UserSessionManager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class Video_get extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener,OnEditorActionListener {


    private String TAG = "video activity";

    private YouTubePlayerView ytpv;
    private YouTubePlayer ytp;
    private EditText et;
    String vidurlid;

    UserSessionManager session;

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.dash_menu, menu);
        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin())
            finish();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.home:
                Intent dash = new Intent(getApplicationContext(),
                        DashboardFragment.class);
                startActivity(dash);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent dash = getIntent();
        vidurlid = dash.getStringExtra("vid_url_id");
        Log.i(TAG, vidurlid);

        ytpv = (YouTubePlayerView) findViewById(R.id.youtubeplayer);
        ytpv.initialize("AIzaSyC-R09MYt2HS82nxKuFCzvNgC60DnxFcqM", this);



        et = (EditText) findViewById(R.id.eturl);
        et.setText(vidurlid);
        et.setOnEditorActionListener(this);

        //================
        final Button button = (Button) findViewById(R.id.go);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                if(ytp !=null){
                    ytp.loadVideo(et.getText().toString());
                }


            }
        });
        //===============


    }

    @Override
    public void onInitializationFailure(Provider arg0,YouTubeInitializationResult arg1) {
        Toast.makeText(this, "Initialization Fail", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,boolean wasrestored) {
        ytp = player;
        Toast.makeText(this, "Initialization  Success", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_GO ){
            if(ytp !=null){
                ytp.loadVideo(et.getText().toString());
            }
        }
        return false;
    }



    public ContextWrapper getActivity() {
        // TODO Auto-generated method stub
        return null;
    }


}
