package dashboardActivities;

import java.util.HashMap;








import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserSessionManager;

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

public class MembershipDetails extends FragMenu {

    private String TAG = "Membership Detail";

    TextView SFF_ID,Role, ExpiryDate;
    HashMap<String, String> data;
    Button btnApplyRenewal;
    Button viewpresent;
    Button viewpast;
    TextView t;

    View view;
    UserSessionManager session;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.membershipdetails, container,
                false);
        //fonts
        t = (TextView) view.findViewById(R.id.memberTitle);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);
        session = new UserSessionManager(getActivity().getApplicationContext());
        SFF_ID = (TextView) view.findViewById(R.id.sffid);
        Role = (TextView) view.findViewById(R.id.role);
        ExpiryDate = (TextView) view.findViewById(R.id.expirydate);
        btnApplyRenewal = (Button) view.findViewById(R.id.renewal);
        viewpresent = (Button) view.findViewById(R.id.presentMembers);
        viewpast = (Button) view.findViewById(R.id.pastMembers);
        viewpast.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity().getApplicationContext(), Members.class);
                i.putExtra(Members.KEY_INTENT,"0");
                startActivity(i);

            }
        });

        viewpresent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getActivity().getApplicationContext(), Members.class);
                i.putExtra(Members.KEY_INTENT,"1");
                startActivity(i);
				
				/*Fragment fragment = new EditProfile();
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment).commit();*/


            }
        });

        btnApplyRenewal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity().getApplicationContext(), Camera.class);
                startActivity(i);

            }
        });


        HashMap<String, String> user = session.getUserDetails();
        SFF_ID.setText(user.get(UserSessionManager.KEY_USERID));
        setRole();
        ExpiryDate.setText(user.get(UserSessionManager.KEY_END_DATE));

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

    public void setRole(){
        session = new UserSessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();


        if (Integer.parseInt(user.get(UserSessionManager.KEY_ROLE_ID))==1){
            Role.setText("Administrator");
        }
        if (Integer.parseInt(user.get(UserSessionManager.KEY_ROLE_ID))==2){
            Role.setText("Executive/Council Member");
        }
        if (Integer.parseInt(user.get(UserSessionManager.KEY_ROLE_ID))==3){
            Role.setText("Honorary Member");
        }
        if (Integer.parseInt(user.get(UserSessionManager.KEY_ROLE_ID))==4){
            Role.setText("Student Member");
        }
        if (Integer.parseInt(user.get(UserSessionManager.KEY_ROLE_ID))==5){
            Role.setText("Professional Member");
        }
        if (Integer.parseInt(user.get(UserSessionManager.KEY_ROLE_ID))==6){
            Role.setText("Senior Member");
        }
        if (Integer.parseInt(user.get(UserSessionManager.KEY_ROLE_ID))==7){
            Role.setText("Organizational Member");
        }






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
