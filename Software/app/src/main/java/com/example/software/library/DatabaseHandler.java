package com.example.software.library;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private String TAG = "database handler";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "user_title";
    private static final String KEY_FNAME = "user_first_name";
    private static final String KEY_LNAME = "user_last_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_USERID = "user_id";
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



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("create login table", TAG);
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_FNAME + " TEXT,"
                + KEY_LNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_USERID + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_DOB + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_TOWN+ " TEXT,"
                + KEY_COUNTRY + " TEXT,"
                + KEY_PCODE + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_MOBILE + " TEXT,"
                + KEY_RECEIPT + " TEXT,"
                + KEY_ACCOUNT_STATUS + " TEXT,"
                + KEY_CONFIRMATION_CODE + " TEXT,"
                + KEY_SECRET_QUESTION + " TEXT,"
                + KEY_ANSWER + " TEXT,"
                + KEY_ROLE_ID + " TEXT,"
                + KEY_USER_PRIVACY + " TEXT,"
                + KEY_START_DATE + " TEXT,"
                + KEY_END_DATE + " TEXT,"
                + KEY_RENEWAL_APPLIED + " TEXT,"
                + KEY_PROFILE_PHOTO + " TEXT,"
                + KEY_USER_CV + " TEXT "
                + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        Log.i("created success login table", TAG);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String firstname, String title, String lastname, String email, String userid,
                        String gender,String dob, String address, String town, String country,
                        String post_code, String phone, String mobile, String receipt, String account_status,String confirmation_code ,String secret_question,String answer,String role_id,
                        String user_privacy,  String start_date , String end_date , String renewal_applied , String profile_photo , String user_cv ) {

        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        Log.i("adding values", TAG);

        values.put(KEY_FNAME, firstname);
        values.put(KEY_TITLE, title);// First Name
        values.put(KEY_LNAME, lastname);
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_USERID, userid); // Email
        values.put(KEY_GENDER, gender); //
        values.put(KEY_DOB, dob);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_TOWN, town); // Created At
        values.put(KEY_COUNTRY, country); // Created At
        values.put(KEY_PCODE, post_code);
        values.put(KEY_PHONE, phone);
        values.put(KEY_MOBILE, mobile);
        values.put(KEY_RECEIPT, receipt);
        values.put(KEY_ACCOUNT_STATUS, account_status);
        values.put(KEY_CONFIRMATION_CODE, confirmation_code);
        values.put(KEY_SECRET_QUESTION, secret_question);
        values.put(KEY_ANSWER, answer);
        values.put(KEY_ROLE_ID, role_id);
        values.put(KEY_USER_PRIVACY, user_privacy);
        values.put(KEY_START_DATE, start_date);
        values.put(KEY_END_DATE, end_date);
        values.put(KEY_RENEWAL_APPLIED, renewal_applied);
        values.put(KEY_PROFILE_PHOTO, profile_photo);
        values.put(KEY_USER_CV, user_cv);
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        Log.i("added to sql", TAG);
        db.close(); // Closing database connection
    }

    public void updateDetails(String uid, String fname, String lname, String email,
                              String town, String country, String pcode,String phone , String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("updating details", TAG);
        db.execSQL("UPDATE " + TABLE_LOGIN + " SET user_first_name ='" + fname+ "', "
                + "user_last_name='" + lname + "',"
                + " user_email='" + email + "',"
                + "user_town='" + town + "', "
                + "user_country='" + country+"',"
                + "user_post_code='" + pcode+"',"
                + "user_phone='" + phone+"',"
                + "user_mobile='" + mobile+"',"
                + "user_country='" + country
                + "' WHERE user_id='" + uid + "'");
        Log.i("updated", TAG);
        db.close(); // Closing database connection
    }



    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Log.i(TAG, "Cursor(0)"+cursor.getColumnName(0));
            Log.i(TAG, "Cursor(1)"+cursor.getColumnName(1));
            Log.i(TAG, "Cursor(2)"+cursor.getColumnName(2));
            Log.i(TAG, "Cursor(3)"+cursor.getColumnName(3));
            Log.i(TAG, "Cursor(4)"+cursor.getColumnName(4));
            user.put("user_title", cursor.getString(1));
            user.put("user_first_name", cursor.getString(2));
            user.put("user_last_name", cursor.getString(3));
            user.put("user_email", cursor.getString(4));
            user.put("user_id", cursor.getString(5));
            user.put("user_gender", cursor.getString(6));
            user.put("user_dob", cursor.getString(7));
            user.put("user_address", cursor.getString(8));
            user.put("user_town", cursor.getString(9));
            user.put("user_country", cursor.getString(10));
            user.put("user_post_code", cursor.getString(11));
            user.put("user_phone", cursor.getString(12));
            user.put("user_mobile", cursor.getString(13));
            user.put("user_receipt", cursor.getString(14));
            user.put("user_account_status", cursor.getString(15));
            user.put("user_confirmation_code", cursor.getString(16));
            user.put("user_secret_question", cursor.getString(17));
            user.put("user_secret_answer", cursor.getString(18));
            user.put("role_id", cursor.getString(19));
            user.put("user_privacy", cursor.getString(20));
            user.put("user_start_date", cursor.getString(21));
            user.put("user_end_date", cursor.getString(22));
            user.put("user_renewal_applied", cursor.getString(23));
            user.put("user_profile_photo", cursor.getString(24));
            user.put("user_cv", cursor.getString(25));

        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    public String getUserID() {

        String selectQuery = "SELECT  user_id FROM " + TABLE_LOGIN;
        String id = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        // return user
        return id;
    }

    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }

}
