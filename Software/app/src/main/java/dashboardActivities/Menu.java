package dashboardActivities;

import com.example.software.DashboardFragment;
import com.example.software.LoginAct;
import com.example.software.R;
import com.example.software.library.UserSessionManager;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;



public class Menu extends Activity {

    UserSessionManager session;

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.dash_menu, menu);
        session = new UserSessionManager(getApplicationContext());
        if(session.checkLogin())
            finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent dash = new Intent(getApplicationContext(),
                        DashboardFragment.class);
                startActivity(dash);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
