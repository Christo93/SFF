package dashboardActivities;

import com.example.software.LoginAct;
import com.example.software.R;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class about extends FragMenu {

    public about() {
        // Required empty public constructor
    }

    View view;
    Button contact;
    TextView t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view =  inflater.inflate(R.layout.about, container,
                false);
        t = (TextView) view.findViewById(R.id.aboutus);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        contact = (Button) view.findViewById(R.id.btnToContact);

        contact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                setHasOptionsMenu(true);
                Intent dash = new Intent(getActivity().getApplicationContext(),
                        ContactUs.class);
                // Close all views before launching Dashboard
                dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
                // Close Login Screen


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