package dashboardActivities;

import com.example.software.LoginAct;
import com.example.software.R;


//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gallery_display extends Activity {

    TextView t;

    private Integer[] pics = {
            R.drawable.sff1,
            R.drawable.sff2,
            R.drawable.sff3,
            R.drawable.sff4,
            R.drawable.sff5
    };

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);
        //fonts
        t = (TextView) findViewById(R.id.gallery);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));
        imageView = (ImageView) findViewById(R.id.imageView1);
        gallery.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "pics: " + arg2, Toast.LENGTH_SHORT).show();
                imageView.setImageResource(pics[arg2]);

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class ImageAdapter extends BaseAdapter
    {
        private Context context;
        int imageBackground;

        public ImageAdapter (Context context)
        {
            this.context = context;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pics.length;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(pics[arg0]);
            return imageView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
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
                    LoginAct.class); //change class to activity u want to go back to.
            startActivity(dash);
        }
        return true;
    }


}
