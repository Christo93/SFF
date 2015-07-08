package com.example.software.registration;

import mailing.Sender;

import org.json.JSONException;
import org.json.JSONObject;











import com.example.software.DashboardFragment;
import com.example.software.LoginAct;
import com.example.software.R;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;

import dashboardActivities.ForgetPassword;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistrationPart3  extends Activity{

    private String TAG = "Register Activity 3";

    Button btnRegister;
    Button btnLinkToLogin;
    Button BackToRegP2;

    EditText inputSAns;
    TextView registerErrorMsg;
    TextView t;

    UserFunctions userFunction = new UserFunctions();
    String fname, lname, email, password, c_password, date, gender, dob,
            address, town, country, postcode, mobile, roleid, secretqst,
            secretans, accountstatus;
    int index,s_quest;


    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static final String KEY_UID = "user_id";
    private static final String KEY_TITLE = "user_title";
    private static final String KEY_FNAME = "user_first_name";
    private static final String KEY_LNAME = "user_last_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_DOB = "user_dob";
    private static final String KEY_GENDER = "user_gender";
    private static final String KEY_ADDRESS = "user_address";
    private static final String KEY_TOWN = "user_town";
    private static final String KEY_COUNTRY = "user_country";
    private static final String KEY_PCODE = "user_post_code";
    private static final String KEY_PHONE = "user_phone";
    private static final String KEY_MOBILE = "user_mobile";
    private static final String KEY_RECEIPT = "user_receipt";
    private static final String KEY_ACCOUNT_STATUS = "user_account_status";
    private static final String KEY_CONFIRMATION_CODE = "user_confirmation_code";
    private static final String KEY_SECRET_QUESTION = "user_secret_question";
    private static final String KEY_ANSWER = "user_secret_answer";
    private static final String KEY_ROLE_ID = "role_id";
    private static final String KEY_USER_PRIVACY = "user_privacy";
    private static final String KEY_START_DATE = "user_start_date";
    private static final String KEY_END_DATE = "user_end_date";
    private static final String KEY_RENEWAL_APPLIED = "user_renewal_applied";
    private static final String KEY_PROFILE_PHOTO = "user_profile_photo";
    private static final String KEY_USER_CV = "user_cv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_cont2);
        //fonts============
        t = (TextView) findViewById(R.id.btnLinkToRegisterScreen);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);
        //======================
        Intent dash = getIntent();
        fname = dash.getStringExtra("user_firstname");
        Log.i(TAG, fname);
        lname = dash.getStringExtra("user_lastname");
        email = dash.getStringExtra("user_email");
        password = dash.getStringExtra("user_password");
        dob = dash.getStringExtra("user_dob");
        address = dash.getStringExtra("user_address");
        town = dash.getStringExtra("user_town");
        country = dash.getStringExtra("user_country");
        postcode = dash.getStringExtra("user_postal");
        mobile = dash.getStringExtra("user_mobile");
        gender = dash.getStringExtra("user_gender");

        // Importing all assets like buttons, text fields
        inputSAns = (EditText) findViewById(R.id.secret_ans);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        BackToRegP2 = (Button) findViewById(R.id.btnBack2);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        final Spinner RoleIdSpinner = (Spinner) findViewById(R.id.member_opt);
        final Spinner SecretQstSpinner = (Spinner) findViewById(R.id.secret_quest);

        Log.i("secret question on create",SecretQstSpinner.getSelectedItem().toString()+" "+SecretQstSpinner.getSelectedItemPosition());

        BackToRegP2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent dash = new Intent(getApplicationContext(),
                        RegistrationPart2.class);
                dash.putExtra("user_firstname", fname);
                dash.putExtra("user_lastname", lname);
                dash.putExtra("user_email", email);
                dash.putExtra("user_password", password);
                dash.putExtra("user_retyped_password", c_password);
                dash.putExtra("user_dob", dob);
                startActivity(dash);

            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginAct.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                secretans = inputSAns.getText().toString();
                roleid = RoleIdSpinner.getSelectedItem().toString();
                secretqst = SecretQstSpinner.getSelectedItem().toString();
                Log.i("secret question",secretqst+" "+SecretQstSpinner.getSelectedItemPosition());
                index = RoleIdSpinner.getSelectedItemPosition();
                s_quest = SecretQstSpinner.getSelectedItemPosition();
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        RegistrationPart3.this);

                if ( (secretqst.length()) > 0
                        && (secretans.length()) > 0&& (roleid.length()) > 0) {


                    new LongOperation().execute("");// start registration
                    // task
                    // Intent dash = new Intent(getApplicationContext(),
                    // DashboardFragmentAct.class);
                    // startActivity(dash);


                } else {

                    adb.setTitle("Missing Data");
                    adb.setMessage("Please ensure that you have filled in all the required data.");
                    adb.setPositiveButton("Ok", null);
                    adb.show();

                }
            }
        });



    }


    private class LongOperation extends AsyncTask<String, Void, String> {

        final AlertDialog.Builder adb = new AlertDialog.Builder(
                RegistrationPart3.this);
        @Override
        protected String doInBackground(String... params) {
            String status = null;

            // set account status to 2 -> awaiting confirmation
            accountstatus = Integer.toString(2);
            //	String date = "1992-00-00";
            // test variable
            Log.i(TAG, Integer.toString(index + 4));
            roleid = Integer.toString(index + 4);
            secretqst = Integer.toString(s_quest +1);

            JSONObject json = userFunction.registerUser(fname, lname, email,
                    password,dob, gender, address, town, country, roleid, postcode,
                    mobile, secretqst, secretans, accountstatus);// register
            // user
            // check for registration response


            try {
                if (json.getString(KEY_SUCCESS) != null) {// if registration
                    // successful
                    status = "success";
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        // user successfully registred
                        // Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(
                                getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");

                        // Clear all previous data in database
                        userFunction.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_FNAME),
                                json_user.getString(KEY_TITLE),
                                json_user.getString(KEY_LNAME),
                                json_user.getString(KEY_EMAIL),
                                json.getString(KEY_UID),
                                json_user.getString(KEY_GENDER),
                                json_user.getString(KEY_DOB),
                                json_user.getString(KEY_ADDRESS),
                                json_user.getString(KEY_TOWN),
                                json_user.getString(KEY_COUNTRY),
                                json_user.getString(KEY_PCODE),
                                json_user.getString(KEY_PHONE),
                                json_user.getString(KEY_MOBILE),
                                json_user.getString(KEY_RECEIPT),
                                json_user.getString(KEY_ACCOUNT_STATUS),
                                json_user.getString(KEY_CONFIRMATION_CODE),
                                json_user.getString(KEY_SECRET_QUESTION),
                                json_user.getString(KEY_ANSWER),
                                json_user.getString(KEY_ROLE_ID),
                                json_user.getString(KEY_USER_PRIVACY),
                                json_user.getString(KEY_START_DATE),
                                json_user.getString(KEY_END_DATE),
                                json_user.getString(KEY_RENEWAL_APPLIED),
                                json_user.getString(KEY_PROFILE_PHOTO),
                                json_user.getString(KEY_USER_CV));
                        Log.i("db add user..", TAG);

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
                registerErrorMsg.setText("Registering User...");// inform user
                final String Subject = "Registration to Software Foundation";
                final String Body = "Dear "
                        + fname
                        + " "
                        + lname
                        + "\nYou have now successfully registered to the SOFTWARE FOUNDATION OF FIJI."
                        + "In order for you to access the system, you are provided with login credentials as follows:"
                        + "\nEmail: " + email + "\nPassword: "+password
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
										adb.setTitle("Registeration Success ");
										adb.setMessage("Please check you email  ");
										adb.setPositiveButton("Ok", null);
										adb.show();		
										        
								  }
								});*/




                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
								/*adb.setTitle("Error Password Reset");
								adb.setMessage("There was a error in password reset.\nplease check you Internet Connection");
								adb.setPositiveButton("Ok", null);
								adb.show();*/

                        }
                    }
                }.start();
                Log.i(TAG, "mail sent");
                adb.setTitle("Registeration Success ");
                adb.setMessage("Please check you email  ");
                adb.setPositiveButton("Ok", null);
                adb.show();

                Intent dash = new Intent(getApplicationContext(),
                        LoginAct.class);
                // Close all views before launching Dashboard
                dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dash);
                // Close Login Screen
                finish();
					
					
					
					/*Intent dash = new Intent(getApplicationContext(),
							LoginAct.class);
					// Close all views before launching Dashboard
					dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(dash);
					// Close Login Screen
					finish();// on progress*/


            } else if (result.contentEquals("fail")) {
                adb.setTitle("Registeration Failure ");
                adb.setMessage("The specified email already exists within our database. Choose another!");
                adb.setPositiveButton("Ok", null);
                adb.show();

            } else {
                adb.setTitle("Error ");
                adb.setMessage("Oops! Seems like we have encountered an error!");
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
                    RegistrationPart2.class);
            dash.putExtra("user_firstname", fname);
            dash.putExtra("user_lastname", lname);
            dash.putExtra("user_email", email);
            dash.putExtra("user_password", password);
            dash.putExtra("user_retyped_password", c_password);
            dash.putExtra("user_dob", dob);
            startActivity(dash);
        }
        return true;
    }
}
