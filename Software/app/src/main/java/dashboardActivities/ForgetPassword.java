package dashboardActivities;

import mailing.Sender;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.software.DashboardFragment;
import com.example.software.LoginAct;
import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassword extends Activity {

    private String TAG = "Forget Password";

    EditText emailadd, secretans;
    Button submit, resetpwd, Back;
    TextView secretquest, viewsecret, viewsecans;
    TextView t;
    UserFunctions userFunction = new UserFunctions();

    String userid, fname, lname, email, secretqst, s_ans, password;

    private static String KEY_SUCCESS = "success";
    private static final String KEY_UID = "user_id";
    private static final String KEY_FNAME = "user_first_name";
    private static final String KEY_LNAME = "user_last_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_SECRET_QUESTION = "user_secret_question";
    private static final String KEY_ANSWER = "user_secret_answer";
    private static final String KEY_PASSWORD = "user_password";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        //font
        t = (TextView) findViewById(R.id.forgotPass);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);

        emailadd = (EditText) findViewById(R.id.emailForget);
        secretans = (EditText) findViewById(R.id.secret_ans);
        submit = (Button) findViewById(R.id.Submit);
        resetpwd = (Button) findViewById(R.id.Reset);
        Back = (Button) findViewById(R.id.Back2Login);
        secretquest = (TextView) findViewById(R.id.user_secret_quest);
        viewsecret = (TextView) findViewById(R.id.sec_quest);
        viewsecans = (TextView) findViewById(R.id.sec_ans);

        //getActionBar().setDisplayHomeAsUpEnabled(true);


        secretquest.setVisibility(View.INVISIBLE);
        viewsecret.setVisibility(View.INVISIBLE);
        viewsecans.setVisibility(View.INVISIBLE);
        secretans.setVisibility(View.INVISIBLE);
        resetpwd.setVisibility(View.INVISIBLE);

        //
        email = emailadd.getText().toString();

        final AlertDialog.Builder adb = new AlertDialog.Builder(
                ForgetPassword.this);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                email = emailadd.getText().toString();
                if ((email.length() > 0)) {
                    new LongOperation().execute("");
                } else {
                    adb.setTitle("Missing Data");
                    adb.setMessage("Please ensure that you have filled in all the required data.");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent dash = new Intent(getApplicationContext(),
                        LoginAct.class);
                startActivity(dash);
            }
        });

        resetpwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if ((secretans.length() > 0)) {
                    Log.i(TAG, " " + secretans + " " + s_ans + " " + secretqst);
                    String compare = secretans.getText().toString();
                    if (compare.contentEquals(s_ans)) {
                        new LongOperation2().execute("");
                    } else {
                        Log.i(TAG, " " + secretans.getText().toString() + " "
                                + s_ans + " " + secretqst);
                        adb.setTitle("Incorrect Answer");
                        adb.setMessage("The Secret Answer does not match \n Please contact the admin ");
                        adb.setPositiveButton("Ok", null);
                        adb.show();
                    }
                } else {
                    adb.setTitle("Missing Data");
                    adb.setMessage("Please ensure that you have filled Secret Answer.");
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

    private class LongOperation extends AsyncTask<String, Void, String> {

        final AlertDialog.Builder adb = new AlertDialog.Builder(
                ForgetPassword.this);

        @Override
        protected String doInBackground(String... params) {
            String status = null;

            // set account status to 2 -> awaiting confirmation

            JSONObject json = userFunction.ForgetPassword(email);

            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {

                        JSONObject json_user = json.getJSONObject("user");
                        userid = json_user.getString(KEY_UID);
                        fname = json_user.getString(KEY_FNAME);
                        lname = json_user.getString(KEY_LNAME);
                        secretqst = json_user.getString(KEY_SECRET_QUESTION);
                        s_ans = json_user.getString(KEY_ANSWER);

                    } else {
                        // Error in registration
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
                Log.i(TAG, userid + " " + fname + " " + lname + " " + s_ans
                        + " " + secretqst);
                setQuestion();

                secretquest.setVisibility(View.VISIBLE);
                viewsecret.setVisibility(View.VISIBLE);
                viewsecans.setVisibility(View.VISIBLE);
                secretans.setVisibility(View.VISIBLE);
                resetpwd.setVisibility(View.VISIBLE);

            } else if (result.contentEquals("fail")) {
                Log.i(TAG, "fail");
                adb.setTitle("Email Error");
                adb.setMessage("Email Does not exist ");
                adb.setPositiveButton("Ok", null);
                adb.show();

            } else {
                Log.i(TAG, "fail");

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

    public void setQuestion() {

        if (Integer.parseInt(secretqst) == 1)  {
            secretquest.setText("Mother\' birth-place");
        }
        if (Integer.parseInt(secretqst) == 2) {
            secretquest.setText("Childhood best friend");
        }
        if (Integer.parseInt(secretqst) == 3) {
            secretquest.setText("Name of first pet");
        }
        if (Integer.parseInt(secretqst) == 4) {
            secretquest.setText("Favourite teacher");
        }
        if (Integer.parseInt(secretqst) == 5) {
            secretquest.setText("Favourite historical person");
        }
        if (Integer.parseInt(secretqst) == 6) {
            secretquest.setText("Grandfather\' occupation");
        }

    }

    private class LongOperation2 extends AsyncTask<String, Void, String> {
        final AlertDialog.Builder adb = new AlertDialog.Builder(
                ForgetPassword.this);

        @Override
        protected String doInBackground(String... params) {
            String status = null;

            // set account status to 2 -> awaiting confirmation

            JSONObject json = userFunction.ResetPassword(email);

            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {

                        JSONObject json_user = json.getJSONObject("user");
                        password = json_user.getString(KEY_PASSWORD);

                    } else {
                        // Error in registration
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
                Log.i(TAG, userid + " " + fname + " " + lname + " " + password);


                // final String sentemail = to_email.getText().toString();
                // Log.i("receipent", sentemail);
                final String Subject = "Reset Password";
                final String Body = "Hello "
                        + fname
                        + " "
                        + lname
                        + "\nYour password has been reset. You can login to your account using the following credentials"
                        + "\nEmail: " + email + "\nPassword: w45u8fk9"
                        + "\n\nRegards," + "\nSFF Team"
                        + "\n\n================================================="
                        + "\n***Please do not reply to this email since its auto generated.***";
                new Thread() {
                    public void run() {
                        try {
                            Sender sender = new Sender("androidsff@gmail.com",
                                    "admin123.");
                            sender.sendMail(Subject, Body,
                                    "androidsff@gmail.com", email);
							
							/*runOnUiThread(new Runnable() 
							{
							  @Override
							  public void run() 
							  {
								  Log.i(TAG, "mail sent");
									adb.setTitle("Password Reset");
									adb.setMessage("Please check you email ");
									adb.setPositiveButton("Ok", null);
									adb.show();	
									        
							  }
							});*/

                            runOnUiThread(new Runnable() {

                                public void run() {


                                    Log.i(TAG, "mail sent");
                                    adb.setTitle("Password Reset");
                                    adb.setMessage("Please check you email ");
                                    adb.setPositiveButton("Ok", null);
                                    adb.show();
                                }
                            });




                        } catch (Exception e) {
                            Log.i(TAG, "erro");

                        }
                    }
                }.start();
				
				/*Log.i(TAG, "mail sent");
				adb.setTitle("Password Reset");
				adb.setMessage("Please check you email ");
				adb.setPositiveButton("Ok", null);
				adb.show();*/

                //open login activity
                Intent dash = new Intent(getApplicationContext(),
                        LoginAct.class);
                // Close all views before launching Dashboard
                dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
                // Close Login Screen
                finish();


                // Toast.makeText(getApplicationContext(),
                // "Email was sent to "+sentemail+" from: duttalvin@gmail.com ",
                // Toast.LENGTH_SHORT).show();

            } else if (result.contentEquals("fail")) {
                Log.i(TAG, "mail fail");
                adb.setTitle("Error Password Reset");
                adb.setMessage("There was a error in password reset.\nplease check you Internet Connection");
                adb.setPositiveButton("Ok", null);
                adb.show();

            } else {
                Log.i(TAG, "mail else");

                adb.setTitle("Error Password Reset");
                adb.setMessage("There was a error in password reset.\nplease check you Internet Connection");
                adb.setPositiveButton("Ok", null);
                adb.show();

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
            Intent dash = new Intent(getApplicationContext(),
                    LoginAct.class); //change class to activity u want to go back to.
            startActivity(dash);
        }
        return true;
    }

}
