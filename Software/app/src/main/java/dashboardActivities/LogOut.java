package dashboardActivities;



import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class LogOut extends Fragment {

    UserSessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        session = new UserSessionManager(getActivity().getApplicationContext());
        UserFunctions.logoutUser(getActivity().getApplicationContext());
        session.logoutUser();

    }

}
