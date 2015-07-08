package com.example.software.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UserFunctions {

    private String TAG = "user functions";

    private JSONParser jsonParser;

    private static String loginURL = "http://scims.usp.ac.fj/~S11074661/loginsff/index.php";
    private static String registerURL = "http://scims.usp.ac.fj/~S11074661/loginsff/index.php";
    //private static String loginURL = "http://10.0.2.2/loginsff/index.php";
    private static String login_tag = "login";
    private static String register_tag = "register";

    // constructor
    public UserFunctions() {
        jsonParser = new JSONParser();
    }

    /**
     * function make Login Request
     *
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String user_email, String user_password) {
        // Building Parameters
        Log.i("login user - user functions..", TAG);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("user_email", user_email));
        params.add(new BasicNameValuePair("user_password", user_password));
        Log.i("login - email password", TAG);
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        Log.i("login url - pass..", TAG);
        // return json
        // Log.e("JSON", json.toString());
        Log.i("return json", TAG);
        return json;
    }

    /**
     * function make Login Request
     *
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String user_first_name,
                                   String user_last_name, String user_email, String user_password,
                                   String user_dob, String user_gender, String user_address,
                                   String user_town, String user_country, String roleid,
                                   String postocde, String mobile, String secretqst, String secretans,
                                   String accountStatus) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("user_first_name", user_first_name));
        params.add(new BasicNameValuePair("user_last_name", user_last_name));
        params.add(new BasicNameValuePair("user_email", user_email));
        params.add(new BasicNameValuePair("user_password", user_password));
        params.add(new BasicNameValuePair("user_dob", user_dob));
        params.add(new BasicNameValuePair("user_gender", user_gender));
        params.add(new BasicNameValuePair("user_address", user_address));
        params.add(new BasicNameValuePair("user_town", user_town));
        params.add(new BasicNameValuePair("user_country", user_country));
        params.add(new BasicNameValuePair("role_id", roleid));
        params.add(new BasicNameValuePair("user_post_code", postocde));
        params.add(new BasicNameValuePair("user_mobile", mobile));
        params.add(new BasicNameValuePair("user_secret_question", secretqst));
        params.add(new BasicNameValuePair("user_secret_answer", secretans));
        params.add(new BasicNameValuePair("user_account_status", accountStatus));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        Log.i("register url json ..", TAG);
        // return json
        return json;
    }

    public JSONObject vid(String vid_url_id) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "video"));
        params.add(new BasicNameValuePair("vid_url_id", vid_url_id));


        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }

    public JSONObject updateDetails(String uid, String user_first_name,
                                    String user_last_name, String user_email, String user_town,
                                    String user_country, String postcode, String mobile) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "update_details"));
        params.add(new BasicNameValuePair("user_id", uid));
        params.add(new BasicNameValuePair("user_first_name", user_first_name));
        params.add(new BasicNameValuePair("user_last_name", user_last_name));
        params.add(new BasicNameValuePair("user_email", user_email));
        params.add(new BasicNameValuePair("user_town", user_town));
        params.add(new BasicNameValuePair("user_country", user_country));
        params.add(new BasicNameValuePair("user_post_code", postcode));
        params.add(new BasicNameValuePair("user_mobile", mobile));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }

    public JSONObject applyrenewal(String uid,String email, String path){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "apply_renewal"));
        params.add(new BasicNameValuePair("user_id", uid));
        params.add(new BasicNameValuePair("user_email", email));
        params.add(new BasicNameValuePair("user_receipt", path));

        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject updatePassword(String uid, String Old_Password,
                                     String New_Password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "update_password"));
        params.add(new BasicNameValuePair("user_id", uid));
        params.add(new BasicNameValuePair("old", Old_Password));
        params.add(new BasicNameValuePair("new", New_Password));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }

    public JSONObject membesList(String accountstatus) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "members"));
        params.add(new BasicNameValuePair("user_account_status", accountstatus));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject AddDiscussion(String dis_subject,String discussion, String user_id) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "AddDiscussion"));
        params.add(new BasicNameValuePair("dis_subject", dis_subject));
        params.add(new BasicNameValuePair("discussion", discussion));
        params.add(new BasicNameValuePair("user_id", user_id));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject ReplyDiscussion(String reply,String dis_id, String user_id) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "ReplyDiscussion"));
        params.add(new BasicNameValuePair("reply", reply));
        params.add(new BasicNameValuePair("dis_id", dis_id));
        params.add(new BasicNameValuePair("user_id", user_id));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject ForgetPassword(String email) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "forgetpassword"));
        params.add(new BasicNameValuePair("user_email", email));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject ResetPassword(String email) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "reset"));
        params.add(new BasicNameValuePair("user_email", email));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject Events(String event) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "events"));
        params.add(new BasicNameValuePair("event", event));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject News() {
        String news = "news";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "news"));
        params.add(new BasicNameValuePair("news", news));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    public JSONObject Discussion() {
        String discussion = "discussion";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "discussion"));
        params.add(new BasicNameValuePair("discussion", discussion));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }
    public JSONObject GetReplyDisscussion(String dis_id) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "GetReply"));
        params.add(new BasicNameValuePair("dis_id", dis_id));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }
    public JSONObject Video() {
        String video = "video";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "video"));
        params.add(new BasicNameValuePair("video", video));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }


    public JSONObject AttendEvent(String userid, String eventid) {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "AttendEvent"));
        params.add(new BasicNameValuePair("user_id", userid));
        params.add(new BasicNameValuePair("event_id", eventid));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;

    }

    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if (count > 0) {
            // user logged in
            return true;
        }
        return false;
    }

    public String getUserID(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        String id = db.getUserID();
        return id;
    }

    /**
     * Function to logout user Reset Database
     * */
    public static boolean logoutUser(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);

        MemberDatabaseHandler db_mem = new MemberDatabaseHandler(context);


        db.resetTables();


        db_mem.resetTables();


        return true;
    }

}
