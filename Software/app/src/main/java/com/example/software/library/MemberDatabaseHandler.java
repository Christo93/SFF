package com.example.software.library;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemberDatabaseHandler extends SQLiteOpenHelper {
    private String TAG = "member database handler";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "android_member";

    // Login table name
    public static final String TABLE_LOGIN = "member";

    // Login Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_FNAME = "user_first_name";
    public static final String KEY_LNAME = "user_last_name";
    public static final String KEY_EMAIL = "user_email";
    public static final String KEY_USERID = "user_id";
    public static final String KEY_ACCOUNT_STATUS = "user_account_status";
    public static final String KEY_ROLE_ID = "role_id";
    public static final String KEY_START_DATE = "user_start_date";
    public static final String KEY_END_DATE = "user_end_date";
    public static final String KEY_PROFILE_PHOTO = "user_profile_photo";
    public static final String KEY_USER_CV = "user_cv";


    public MemberDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("create login table", TAG);
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"

                + KEY_FNAME + " TEXT," + KEY_LNAME + " TEXT,"
				/* + KEY_EMAIL + " TEXT ," */
                + KEY_USERID + " TEXT," + KEY_ACCOUNT_STATUS + " TEXT,"
                + KEY_ROLE_ID + " TEXT," + KEY_START_DATE + " TEXT,"
                + KEY_END_DATE + " TEXT," + KEY_PROFILE_PHOTO + " TEXT,"
                + KEY_USER_CV + " TEXT " + ")";
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
    public void addMember(String id, String firstname, String lastname, /*
																		 * String
																		 * email
																		 * ,
																		 */
                          String account_status, String role_id, String start_date,
                          String end_date, String profile_photo, String user_cv) {

        SQLiteDatabase db = this.getWritableDatabase();
        // onUpgrade(db, 1, 1);
        ContentValues values = new ContentValues();
        Log.i("adding values", TAG);
        values.put(KEY_USERID, id);
        values.put(KEY_FNAME, firstname);
        values.put(KEY_LNAME, lastname);
        // values.put(KEY_EMAIL, email); // Email
        values.put(KEY_ACCOUNT_STATUS, account_status);
        values.put(KEY_ROLE_ID, role_id);
        values.put(KEY_START_DATE, start_date);
        values.put(KEY_END_DATE, end_date);
        values.put(KEY_PROFILE_PHOTO, profile_photo);
        values.put(KEY_USER_CV, user_cv);
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        Log.i("added memeber to sql", TAG);
        db.close(); // Closing database connection
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getMemberDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("user_first_name", cursor.getString(1));
            user.put("user_last_name", cursor.getString(2));
            user.put("user_email", cursor.getString(3));
            user.put("user_id", cursor.getString(4));

            user.put("user_account_status", cursor.getString(5));

            user.put("role_id", cursor.getString(6));

            user.put("user_start_date", cursor.getString(7));
            user.put("user_end_date", cursor.getString(8));
            user.put("user_profile_photo", cursor.getString(9));
            user.put("user_cv", cursor.getString(10));

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

    public Cursor getData(String role_id){

        SQLiteDatabase db = this. getReadableDatabase();
        Cursor res = db. rawQuery( "select * from "+TABLE_LOGIN+" WHERE role_id ="+role_id+"", null );
        return res;
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
