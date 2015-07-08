package dashboardActivities;



import org.json.JSONException;
import org.json.JSONObject;







import com.example.software.DashboardFragment;
import com.example.software.R;
import com.example.software.library.UserFunctions;





import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePassword extends Menu {

    Button btnBack, btnSave;
    EditText New_Password, Confirm_Password, Old_Password;
    TextView save_details;
    UserFunctions userFunction = new UserFunctions();
    String uid = null;
    TextView t;

    private static String KEY_SUCCESS = "success";
    private static final String KEY_UID = "user_id";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.changepassword);
        //fonts
        t = (TextView) findViewById(R.id.changePass);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        save_details = (TextView) findViewById(R.id.save_details);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSavePassword);

        btnBack.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DashboardFragment.class);
                startActivity(i);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Old_Password = (EditText) findViewById(R.id.OldPass);
                New_Password = (EditText) findViewById(R.id.NewPass);
                Confirm_Password = (EditText) findViewById(R.id.ConfirmPass);
                if (!Old_Password.getText().toString().contentEquals("")
                        && !New_Password.getText().toString().contentEquals("")
                        && !Confirm_Password.getText().toString()
                        .contentEquals("")) {
                    if (Confirm_Password.getText().toString()
                            .contentEquals(New_Password.getText().toString())) {
                        new LongOperation().execute("");
                    } else {

                        AlertDialog.Builder adb = new AlertDialog.Builder(
                                ChangePassword.this);
                        adb.setTitle("Confirm Password");
                        adb.setMessage("New Passwords do not match!");
                        adb.setPositiveButton("Ok", null);
                        adb.show();
                    }

                } else {

                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            ChangePassword.this);
                    adb.setTitle("Missing/Invalid Data");
                    adb.setMessage("Please Check all fields to ensure the filled data is correct.");
                    adb.setPositiveButton("Ok", null);
                    adb.show();

                }
            }

        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String status = null;
            uid = userFunction.getUserID(getApplicationContext());

            JSONObject json = userFunction.updatePassword(uid, Old_Password
                    .getText().toString(), New_Password.getText().toString());// register
            // user
            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {

                        JSONObject json_user = json.getJSONObject("user");

                    } else {
                        // Error in update
                        status = "fail";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                status = "error";
            }

            return status;// return login status
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contentEquals("success")) {
                save_details.setText("Password Updated...");// inform user on
                // progress
                btnSave.setEnabled(false);
                Intent dash = new Intent(getApplicationContext(),
                        DashboardFragment.class);
                // Close all views before launching Dashboard
                dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
                finish();

            } else if (result.contentEquals("fail")) {

                save_details
                        .setText("The current password that you specified is incorrect!");
            } else {
                save_details
                        .setText("Oops! Seems like we have encountered an error!");
            }
        }

        @Override
        protected void onPreExecute() {
            // before logging in

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent a = new Intent(getApplicationContext(),
                    DashboardFragment.class); //change class to activity u want to go back to.
            startActivity(a);

        }
        return true;
    }

}
