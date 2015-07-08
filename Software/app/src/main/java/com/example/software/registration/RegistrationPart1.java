package com.example.software.registration;



import java.util.Calendar;

import com.example.software.LoginAct;
import com.example.software.R;
import com.example.software.library.UserFunctions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationPart1 extends Activity {

    private String TAG = "Register Activity 1";

    Button NexttoRegP2;
    Button btnToLogin;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputEmail;
    EditText inputPassword;
    EditText cPassword;
    TextView t;

    //DOB
    TextView datelabel;
    Button setDOB;

    private int year,month, day;
    static final int DATE_PICKER_ID = 1111;

    UserFunctions userFunction = new UserFunctions();

    String fname, lname, email, password, c_password,dob;
    String testreg = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);
        //font================
        t = (TextView) findViewById(R.id.btnLinkToRegisterScreen);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);
        //=================

        //to cater for when user presses back button



        // Importing all assets like buttons, text fields
        inputFirstName = (EditText) findViewById(R.id.registerFName);
        inputLastName = (EditText) findViewById(R.id.registerLName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        cPassword = (EditText) findViewById(R.id.ConfirmPassword);
        NexttoRegP2 = (Button) findViewById(R.id.btnNext1);
        btnToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        //importing dob assess
        datelabel = (TextView) findViewById(R.id.date_label);
        setDOB = (Button) findViewById(R.id.btnDOB);

        //get current date by calendar
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);


        // Button listener to show date picker dialog

        setDOB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);

            }

        });



        btnToLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent dash = new Intent(getApplicationContext(),
                        LoginAct.class);
                startActivity(dash);
            }
        });

        // next button onclick
        NexttoRegP2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                datelabel.setText(new StringBuilder().append(year)
                        .append("-").append(month+1).append("-").append(day)
                        .append(" "));
                fname = inputFirstName.getText().toString();
                lname = inputLastName.getText().toString();
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                c_password = cPassword.getText().toString();
                dob = datelabel.getText().toString();
                Log.i("date of b", dob );

                AlertDialog.Builder adb = new AlertDialog.Builder(
                        RegistrationPart1.this);

                Intent dash = new Intent(getApplicationContext(),
                        RegistrationPart2.class);

                if ((fname.length()) > 0 && (lname.length()) > 0
                        && (email.length()) > 0 && (password.length()) > 0
                        && (c_password.length()) > 0) {
                    if (password.contentEquals(c_password)) {

                        dash.putExtra("user_firstname", fname);
                        dash.putExtra("user_lastname", lname);
                        dash.putExtra("user_email", email);
                        dash.putExtra("user_password", password);
                        dash.putExtra("user_retyped_password", c_password);
                        dash.putExtra("user_dob", dob);
                        startActivity(dash);
                    } else {

                        adb.setTitle("Password Error");
                        adb.setMessage("Passwords do not match!");
                        adb.setPositiveButton("Ok", null);
                        adb.show();

                    }
                }else {

                    adb.setTitle("Missing Data");
                    adb.setMessage("Please ensure that you have filled in all the required data.");
                    adb.setPositiveButton("Ok", null);
                    adb.show();

                }
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            datelabel.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));



        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    LoginAct.class);
            startActivity(dash);
        }
        return true;
    }


}
