package dashboardActivities;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.software.R;

public class ViewNews extends Menu {

    private String TAG = "View News";

    Button btnBack, btnyes, btnNo;
    TextView NSub, NDetails, Ntime;
    String newsid,newsub,newsdetail,newstime;
    HashMap<String, String> data;
    TextView t;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.news_view);
        //fonts
        t = (TextView) findViewById(R.id.news_v);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getting intent values
        Intent dash = getIntent();
        newsid = dash.getStringExtra("news_id");
        newsub = dash.getStringExtra("news_subject");
        newsdetail = dash.getStringExtra("news_details");
        newstime = dash.getStringExtra("news_time");

        NSub = (TextView) findViewById(R.id.news_sub);
        NDetails = (TextView) findViewById(R.id.newsdetails);
        Ntime = (TextView) findViewById(R.id.news_time);



        //setting textview.
        NSub.setText(newsub);
        NDetails.setText(newsdetail);
        Ntime.setText(newstime);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //Intent i = new Intent(getApplicationContext(), News.class);
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    News.class); //change class to activity u want to go back to.
        }
        return true;
    }



}
