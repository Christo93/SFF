package dashboardActivities;

import com.example.software.DashboardFragment;
import com.example.software.R;

import discussion.Discussion;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FragMenu extends Fragment {

    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dashfrag, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sff_forum:
                Intent dash = new Intent(getActivity().getApplicationContext(),
                        Discussion.class);
                startActivity(dash);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed()
    {
    }

}
