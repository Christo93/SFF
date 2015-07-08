package com.example.software.library;

import java.util.HashMap;

import com.example.software.LoginAct;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;



public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "SFF_Pref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    private static final String KEY_TITLE = "user_title";
    public static final String KEY_USERID = "user_id";
    public static final String KEY_FNAME = "user_first_name";
    public static final String KEY_LNAME = "user_last_name";
    public static final String KEY_DOB = "user_dob";
    public static final String KEY_Pass = "user_password";
    public static final String KEY_GENDER = "user_gender";
    public static final String KEY_ADDRESS = "user_address";
    public static final String KEY_TOWN = "user_town";
    public static final String KEY_COUNTRY = "user_country";
    public static final String KEY_PCODE = "user_post_code";
    public static final String KEY_PHONE = "user_phone";
    public static final String KEY_MOBILE = "user_mobile";
    public static final String KEY_RECEIPT = "user_receipt";
    public static final String KEY_ACCOUNT_STATUS = "user_account_status";
    public static final String KEY_CONFIRMATION_CODE = "user_confirmation_code";
    public static final String KEY_SECRET_QUESTION = "user_secret_question";
    public static final String KEY_ANSWER = "user_secret_answer";
    public static final String KEY_ROLE_ID = "role_id";
    public static final String KEY_USER_PRIVACY = "user_privacy";
    public static final String KEY_START_DATE = "user_start_date";
    public static final String KEY_END_DATE = "user_end_date";
    public static final String KEY_RENEWAL_APPLIED = "user_renewal_applied";
    public static final String KEY_PROFILE_PHOTO = "user_profile_photo";
    public static final String KEY_USER_CV = "user_cv";

    // Constructor
    public UserSessionManager(Context context){
        this._context = context;
        Log.i("user seesion mamnger", "context");
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name,String pass, String email,String title,String userid,String fname,String lname,String dob,
                                       String gender,String address,String town,String country,String pcode,String phone,String mobile,String receipt,String account_status,String conf_code,
                                       String secret_question,String answer,String role_id,String privacy,String s_date,String e_date,String renewal_applied,String profile,String user_cv){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);
        Log.i("user id", userid);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_TITLE, title);
        editor.putString(KEY_USERID, userid);
        editor.putString(KEY_FNAME, fname);
        editor.putString(KEY_LNAME, lname);
        editor.putString(KEY_DOB, dob);
        editor.putString(KEY_Pass, pass);
        editor.putString(KEY_GENDER, gender);
        editor.putString(KEY_ADDRESS, address);
        editor.putString(KEY_TOWN, town);
        editor.putString(KEY_COUNTRY, country);
        editor.putString(KEY_PCODE, pcode);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_MOBILE, mobile);
        editor.putString(KEY_RECEIPT, receipt);
        editor.putString(KEY_ACCOUNT_STATUS, account_status);
        editor.putString(KEY_CONFIRMATION_CODE, conf_code);
        editor.putString(KEY_SECRET_QUESTION, secret_question);
        editor.putString(KEY_ANSWER, answer);
        editor.putString(KEY_ROLE_ID, role_id);
        editor.putString(KEY_USER_PRIVACY, privacy);
        editor.putString(KEY_START_DATE, s_date);
        editor.putString(KEY_END_DATE, e_date);
        editor.putString(KEY_RENEWAL_APPLIED, renewal_applied);
        editor.putString(KEY_PROFILE_PHOTO, profile);
        editor.putString(KEY_USER_CV, user_cv);



        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginAct.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));
        user.put(KEY_DOB, pref.getString(KEY_DOB, null));
        user.put(KEY_Pass, pref.getString(KEY_Pass, null));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_TOWN, pref.getString(KEY_TOWN, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
        user.put(KEY_COUNTRY, pref.getString(KEY_COUNTRY, null));
        user.put(KEY_PCODE, pref.getString(KEY_PCODE, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        user.put(KEY_RECEIPT, pref.getString(KEY_RECEIPT, null));
        user.put(KEY_ACCOUNT_STATUS, pref.getString(KEY_ACCOUNT_STATUS, null));
        user.put(KEY_CONFIRMATION_CODE, pref.getString(KEY_CONFIRMATION_CODE, null));
        user.put(KEY_SECRET_QUESTION, pref.getString(KEY_SECRET_QUESTION, null));
        user.put(KEY_ROLE_ID, pref.getString(KEY_ROLE_ID, null));
        user.put(KEY_USER_PRIVACY, pref.getString(KEY_USER_PRIVACY, null));
        user.put(KEY_START_DATE, pref.getString(KEY_START_DATE, null));
        user.put(KEY_END_DATE, pref.getString(KEY_END_DATE, null));
        user.put(KEY_RENEWAL_APPLIED, pref.getString(KEY_RENEWAL_APPLIED, null));
        user.put(KEY_PROFILE_PHOTO, pref.getString(KEY_PROFILE_PHOTO, null));
        user.put(KEY_USER_CV, pref.getString(KEY_USER_CV, null));


        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginAct.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
