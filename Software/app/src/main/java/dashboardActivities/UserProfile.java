package dashboardActivities;

import java.util.HashMap;

















import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserSessionManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class UserProfile extends FragMenu {

    Button btnEdit, btnChangePassword;
    //TextView t;

    private String TAG = "User profile";

    TextView fname,lname, gender, email, country, town,postcode,mobile;
    HashMap<String, String> data;



    View view;
    public UserProfile() {
        // Required empty public constructor

    }

    UserSessionManager session;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //setHasOptionsMenu(true);
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.fragment_user_profile, container,
                false);
        //fonts
        //t = (TextView) view.findViewById(R.id.member_title);
        //Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kaushan_script.otf");
        //t.setTypeface(font);

        session = new UserSessionManager(getActivity().getApplicationContext());


        fname = (TextView) view.findViewById(R.id.fname);
        lname = (TextView) view.findViewById(R.id.lname);
        gender = (TextView) view.findViewById(R.id.gender);
        email = (TextView) view.findViewById(R.id.email);
        country = (TextView) view.findViewById(R.id.country);
        town = (TextView) view.findViewById(R.id.town);
        postcode = (TextView) view.findViewById(R.id.postcode);
        mobile = (TextView) view.findViewById(R.id.mobile);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);



        if(session.checkLogin())
            getActivity().finish();

        btnEdit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getActivity().getApplicationContext(), EditProfile.class);
                startActivity(i);
				
				/*Fragment fragment = new EditProfile();
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();*/


            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), ChangePassword.class);
                startActivity(i);


            }
        });



        //DatabaseHandler db = new DatabaseHandler(
        //	getActivity().getApplicationContext());
        //data = db.getUserDetails();
        HashMap<String, String> user = session.getUserDetails();

        fname.setText(user.get(UserSessionManager.KEY_FNAME));
        lname.setText(user.get(UserSessionManager.KEY_LNAME));
        email.setText(user.get(UserSessionManager.KEY_EMAIL));
        gender.setText(user.get(UserSessionManager.KEY_GENDER));
        town.setText(user.get(UserSessionManager.KEY_TOWN));
        country.setText(user.get(UserSessionManager.KEY_COUNTRY));
        postcode.setText(user.get(UserSessionManager.KEY_PCODE));
        mobile.setText(user.get(UserSessionManager.KEY_MOBILE));

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




}
