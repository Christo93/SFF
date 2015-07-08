package dashboardActivities;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.software.R;
import com.example.software.library.Member;
import com.example.software.library.MemberDatabaseHandler;
import com.example.software.library.UserFunctions;

public class Members extends Menu implements OnItemSelectedListener  {



    private String TAG = "member activity";

    final UserFunctions userFunction = new UserFunctions();
    SQLiteDatabase db;
    HashMap<String, String> data;
    TextView firstname,lastname, roleid;
    ListView memberList;
    MemberDatabaseHandler memberDbase;
    Spinner spinner;
    private String accountstatus;
    JSONArray json_members;
    private ArrayList<Member>members;
    private ArrayList<Member>sortedmem;
    private int temp = 0;

    public static final String KEY_INTENT = "SFF";
    private static String KEY_SUCCESS = "success";
    private static final String KEY_UID = "user_id";
    private static final String KEY_FNAME = "user_first_name";
    private static final String KEY_LNAME = "user_last_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_ACCOUNT_STATUS = "user_account_status";
    private static final String KEY_ROLE_ID = "role_id";

    private static final String KEY_START_DATE = "user_start_date";
    private static final String KEY_END_DATE = "user_end_date";

    private static final String KEY_PROFILE_PHOTO = "user_profile_photo";
    private static final String KEY_USER_CV = "user_cv";

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.viewpresentmembers);
        accountstatus = getIntent().getStringExtra(KEY_INTENT);
        memberList = (ListView) findViewById(R.id.MembersList);
        spinner = (Spinner)findViewById(R.id.members);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        members = new ArrayList();
        sortedmem = new ArrayList();
        ArrayList<String>category = new ArrayList();
        category.add("Select members...");
        category.add("Administrator");
        category.add("Executive");
        category.add("Honorary");
        category.add("Student");
        category.add("Professional");
        category.add("Senior");
        category.add("Organizational");

        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, category);

        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_state);
        spinner.setOnItemSelectedListener(this);

        new LongOperation().execute("");


    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner.setSelection(position);
        if(position == 0){
            //do nothing
        }else{
            Log.i(Integer.toString(position), TAG);
            if(temp != position){
                temp = position;

                if(sortedmem.size() != 0){
                    sortedmem.clear();
                }

                if(sortmembers(Integer.toString(position)) == true){
                    setList(sortedmem);
                }else{
                    Member m = new Member("NO MEMBERS","0");
                    sortedmem.add(m);
                    setList(sortedmem);
                }

            }else{
                setList(sortedmem);
            }
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.i("long operation..", TAG);


            String status = "internet";
            JSONObject json = userFunction.membesList(accountstatus);// check
            // email
            // and
            // password

            // in DB

            try {
                if (json.getString(KEY_SUCCESS) != null) {// if user found then
                    // login

                    status = "Success";
                    Log.i("user found..", TAG);
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        // user successfully logged in
                        // Store user details in SQLite Database
                        Log.i("success found..", TAG);

                        json_members = json.getJSONArray("user");
                        // JSONArray json_news = json.getJSONArray("news");

                        // Clear all previous data in database


                        for (int i = 0; i < json_members.length(); i++) {
                            JSONObject fetch_members = json_members
                                    .getJSONObject(i);
                            Member m = new Member(fetch_members.getString(KEY_FNAME)+" "+fetch_members.getString(KEY_LNAME),fetch_members.getString(KEY_ROLE_ID));
                            members.add(m);
                        }

                        Log.i("db add user..", TAG);

                    }

                    else {
                        // incorrect username and/or password
                        status = "fail";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();// error
                status = "error";
            }
            return status;// return login status
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contentEquals("Success")) {
                //Toast.makeText(Members.this, "success", Toast.LENGTH_SHORT).show();
                //showMemberdata();
			/*	Intent dash = new Intent(getApplicationContext(),
						regi.class);
				// Close all views before launching Dashboard
				dash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(dash);
				// Close Login Screen
				finish();*/

            } else if (result.contentEquals("fail")) {


            } else if (result.contentEquals("error")) {



            } else {

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

    private boolean sortmembers(String role_id){
        for (Member e : members) {
            if (e.getRole_id().equals(role_id))
                sortedmem.add(e);
        }

        if(sortedmem.size() == 0){
            return false;
        }else{
            return true;
        }
    }
    private void setList(ArrayList<Member>members){
        ArrayAdapter<Member> adapter = new ArrayAdapter<Member>(getApplicationContext(),android.R.layout.simple_list_item_1,members);
        memberList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
