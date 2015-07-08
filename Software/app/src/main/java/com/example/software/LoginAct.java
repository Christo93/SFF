package com.example.software;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.software.library.ConnectionDetector;
import com.example.software.library.DatabaseHandler;
import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;
import com.example.software.registration.RegistrationPart1;

import dashboardActivities.ForgetPassword;
import dashboardActivities.Gallery_display;
import dashboardActivities.NewsActivity;

public class LoginAct extends Activity {

    private String TAG = "login activity";

    Button btnLogin;
    Button btnLinkToRegister, btnForgotPass;
    Button gallery, news;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    public static String email;
    String password;
    final UserFunctions userFunction = new UserFunctions();
    private ProgressDialog progressDialog;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

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

    // Event details
    private static final String KEY_EVENTID = "event_id";
    private static final String KEY_EVENT_NAME = "event_name";
    private static final String KEY_EVENT_DESCRIPTION = "event_description";
    private static final String KEY_EVENT_DATE = "event_date";

    // news
    public static final String KEY_NEWS_ID = "news_id";
    public static final String KEY_NEWS_SUBJECT = "news_subject";
    public static final String KEY_NEWS_DETAILS = "news_details";
    public static final String KEY_NEWS_TIME = "news_time";

    String username, fname, lname, id, gender, dob, address, town, country,
            pcode, phone, mobile, receipt, account_status;
    String con_code, secret_quest, answer, ans, role_id, privacy, start, end,
            renewal, profile, title, cv;

    // User Session Manager Class
    UserSessionManager session;

    private String m_expiry;
    private String u_id;
    private String accountstatus;
    public static int numdaystrial = 30;
    Date trial_exp_date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        cd = new ConnectionDetector(getApplicationContext());

        session = new UserSessionManager(getApplicationContext());

        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.buttonSignIn);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);

        btnForgotPass = (Button) findViewById(R.id.forgot_pass);
        news = (Button) findViewById(R.id.news_feeds);
        news.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        NewsActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnForgotPass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        ForgetPassword.class);
                startActivity(i);
                finish();
            }
        });
        //Gallery link
        gallery = (Button) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Gallery_display.class);
                startActivity(i);
                finish();
            }
        });

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                if ((email.length()) > 0 && (password.length()) > 0) {

                    new LongOperation().execute("");// start login task
					/*
					 * Intent dash = new Intent(getApplicationContext(),
					 * DashboardFragment.class); startActivity(dash);
					 */

                } else {

                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            LoginAct.this);
                    adb.setTitle("Missing Data");
                    adb.setMessage("Missing Email and/or Password");
                    adb.setPositiveButton("Ok", null);
                    adb.show();

                }

            }
        });

        // Link to Register Screen

        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegistrationPart1.class);
                startActivity(i);
                finish();
            }
        });

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.i("long operation..", TAG);
            String status = "internet";

            isInternetPresent = cd.isConnectingToInternet();

            if (isInternetPresent) {

                JSONObject json = userFunction.loginUser(email, password);// check
                // email
                // and
                // password

                // in DB

                try {
                    if (json.getString(KEY_SUCCESS) != null) {// if user found
                        // then
                        // login

                        status = "Success";
                        Log.i("user found..", TAG);
                        String res = json.getString(KEY_SUCCESS);
                        if (Integer.parseInt(res) == 1) {
                            // user successfully logged in
                            // Store user details in SQLite Database
                            Log.i("success found..", TAG);
                            JSONObject json_user = json.getJSONObject("user");

                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            DatabaseHandler db = new DatabaseHandler(
                                    getApplicationContext());
                            u_id = json.getString(KEY_UID);
                            m_expiry = json_user.getString(KEY_END_DATE);
                            accountstatus = json_user
                                    .getString(KEY_ACCOUNT_STATUS);

                            Log.i("logoutueser - userfunction call..", TAG);


                            username = json_user.getString(KEY_FNAME) + " "
                                    + json_user.get(KEY_LNAME);
                            fname = json_user.getString(KEY_FNAME);
                            title = json_user.getString(KEY_TITLE);
                            lname = json_user.getString(KEY_LNAME);
                            // json_user.getString(KEY_EMAIL);
                            id = json.getString(KEY_UID);
                            gender = json_user.getString(KEY_GENDER);
                            dob = json_user.getString(KEY_DOB);
                            address = json_user.getString(KEY_ADDRESS);
                            town = json_user.getString(KEY_TOWN);
                            country = json_user.getString(KEY_COUNTRY);
                            pcode = json_user.getString(KEY_PCODE);
                            phone = json_user.getString(KEY_PHONE);
                            mobile = json_user.getString(KEY_MOBILE);
                            receipt = json_user.getString(KEY_RECEIPT);
                            account_status = json_user
                                    .getString(KEY_ACCOUNT_STATUS);
                            con_code = json_user
                                    .getString(KEY_CONFIRMATION_CODE);
                            secret_quest = json_user
                                    .getString(KEY_SECRET_QUESTION);
                            answer = json_user.getString(KEY_ANSWER);
                            role_id = json_user.getString(KEY_ROLE_ID);
                            privacy = json_user.getString(KEY_USER_PRIVACY);
                            start = json_user.getString(KEY_START_DATE);
                            end = json_user.getString(KEY_END_DATE);
                            renewal = json_user.getString(KEY_RENEWAL_APPLIED);
                            profile = json_user.getString(KEY_PROFILE_PHOTO);
                            cv = json_user.getString(KEY_USER_CV);

                            Log.i("id", id);

                        } else {
                            // incorrect username and/or password
                            status = "fail";
                            Log.i("login fail", "fail");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();// error
                    status = "error";
                    Log.i("login error", "error");
                }
            } else {
                // Internet connection is not present
                // Ask user to connect to Internet
                // showAlertDialog(AndroidDetectInternetConnectionActivity.this,
                // "No Internet Connection",
                // "You don't have internet connection.", false);
                //error.setText("No internet Access");
                Log.i("er", "er");
                runOnUiThread(new Runnable() {
                    public void run() {
                        loginErrorMsg.setText("You need to be connected to the Internet in order to login.");
                    }
                });
            }
            return status;// return login status
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contentEquals("Success")) {

                Date d = new Date(); // progress
                Date date = convertStringToDate(m_expiry);

                if (checkaccountstatus(date) == true) {

                    loginErrorMsg.setText("Logging In...");// inform user on
                    session.createUserLoginSession(username,password, email, title, id,
                            fname, lname, dob, gender, address, town, country,
                            pcode, phone, mobile, receipt, account_status,
                            con_code, secret_quest, answer, role_id, privacy,
                            start, end, renewal, profile, cv);

                    Intent i = new Intent(getApplicationContext(),
                            DashboardFragment.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(DashboardFragment.TRIAL, 'A');
                    // Add new Flag to start new Activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                    finish();
                } else if (calctrial(date) == true) {
                    System.out.println("I AM HERE2");
                    loginErrorMsg.setText("Logging In...");// inform user on
                    session.createUserLoginSession(username,password, email, title, id,
                            fname, lname, dob, gender, address, town, country,
                            pcode, phone, mobile, receipt, account_status,
                            con_code, secret_quest, answer, role_id, privacy,
                            start, end, renewal, profile, cv);

                    Intent dash = new Intent(getApplicationContext(),
                            DashboardFragment.class);
                    long diff = trial_exp_date.getTime() - d.getTime();
                    numdaystrial = (int) TimeUnit.DAYS.convert(diff,
                            TimeUnit.MILLISECONDS);
                    // Close all views before launching Dashboard
                    dash.putExtra(DashboardFragment.TRIAL, 'T');
                    dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dash);
                    // Close Login Screen
                    finish();
                } else {
                    // read();
                    System.out.println("I AM HERE5");
                    Toast.makeText(getBaseContext(), R.string.trialexp,
                            Toast.LENGTH_LONG).show();

                }

            } else if (result.contentEquals("fail")) {
                loginErrorMsg.setText("Incorrect username/password");

            } else if (result.contentEquals("error")) {

                loginErrorMsg
                        .setText("Oops! Seems like we have encountered an error!");

            } else {
                loginErrorMsg
                        .setText("You need to be connected to the Internet in order to login.");
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

    private int date_compare(Date d1, Date d2) {
        System.out.println("I AM HERE4");
        // System.out.println(d1.toString());
        // System.out.println(d2.toString());
        int comparison = d1.compareTo(d2);
        return comparison;
    }

    private static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    private boolean checkaccountstatus(Date d) {
        // System.out.println(account_status);
        Date date = new Date();
        int result = date_compare(date, d);
        if (result == 0 || result == -1) {
            return true;
        } else {
            return false;
        }
    }

    public Date convertStringToDate(String dateString) {
        Date date = null;
        String expectedPattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);

        try {
            date = formatter.parse(dateString);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    private boolean calctrial(Date date) {
        System.out.println("I AM HERE6");
        trial_exp_date = addDays(date, 30);

        Date d = new Date();
        // Date expiry = trial.getTrial_expiry_date();
        System.out.println("I AM HERE3");
        // System.out.println(expiry);
        int result = date_compare(d, trial_exp_date);
        // System.out.println(result);
        if (result == 0 || result == -1) {
            return true;
        } else {
            return false;
        }

    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    LoginAct.class); //change class to activity u want to go back to.

        }
        return true;
    }

}
