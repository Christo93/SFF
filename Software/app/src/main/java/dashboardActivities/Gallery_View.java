package dashboardActivities;



import com.example.software.R;

import dashboardActivities.Gallery_display.ImageAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Gallery_View extends FragMenu {

    TextView t;

    private Integer[] pics = {
            R.drawable.sff1,
            R.drawable.sff2,
            R.drawable.sff3,
            R.drawable.sff4,
            R.drawable.sff5
    };

    View view;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.gallery_view, container,
                false);
        //fonts
        t = (TextView) view.findViewById(R.id.gallery);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        Gallery gallery = (Gallery) view.findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(getActivity().getApplicationContext()));
        imageView = (ImageView) view.findViewById(R.id.imageView1);
        gallery.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity().getApplicationContext(), "pics: " + arg2, Toast.LENGTH_SHORT).show();
                imageView.setImageResource(pics[arg2]);

            }

        });

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
            Intent dash = new Intent(getActivity().getApplicationContext(),
                    MembershipDetails.class); //change class to activity u want to go back to.
        }
        return true;
    }

}
