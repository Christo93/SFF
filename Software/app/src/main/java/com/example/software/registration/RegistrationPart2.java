package com.example.software.registration;



import com.example.software.LoginAct;
import com.example.software.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistrationPart2 extends Activity {

    private String TAG = "Register Activity 2";

    Button BackToRegP1;
    Button NextToRegP3;
    Button btntoLogin;
    EditText inputGender;
    EditText inputAddress;
    EditText inputTown;
    EditText inputPostCode;
    EditText inputCountry;
    EditText inputMobile;
    TextView t;

    String fname, lname, email, password, c_password, date, gender, dob,
            address, town, country, postcode, mobile, roleid, secretqst,
            secretans, accountstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_cont);
        //font=============
        t = (TextView) findViewById(R.id.btnLinkToRegisterScreen);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);
        //====================

        Intent dash = getIntent();
        fname = dash.getStringExtra("user_firstname");
        Log.i(TAG, fname);
        lname = dash.getStringExtra("user_lastname");
        email = dash.getStringExtra("user_email");
        password = dash.getStringExtra("user_password");
        c_password = dash.getStringExtra("user_retyped_password");
        dob = dash.getStringExtra("user_dob");

        // Importing all assets like buttons, text fields
        inputAddress = (EditText) findViewById(R.id.Address);
        inputTown = (EditText) findViewById(R.id.Town);
        inputCountry = (EditText) findViewById(R.id.Country);
        inputPostCode = (EditText) findViewById(R.id.postal);
        inputMobile = (EditText) findViewById(R.id.contact);
        BackToRegP1 = (Button) findViewById(R.id.btnBack1);
        NextToRegP3 = (Button) findViewById(R.id.btnNext2);
        btntoLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        final Spinner Genderspinner = (Spinner) findViewById(R.id.gender);
        btntoLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent dash = new Intent(getApplicationContext(),
                        LoginAct.class);

                startActivity(dash);

            }
        });
        BackToRegP1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent dash = new Intent(getApplicationContext(),
                        RegistrationPart1.class);



                startActivity(dash);

            }
        });

        NextToRegP3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                address = inputAddress.getText().toString();
                town = inputTown.getText().toString();
                country = inputCountry.getText().toString();
                postcode = inputPostCode.getText().toString();
                mobile = inputMobile.getText().toString();
                gender = Genderspinner.getSelectedItem().toString();


                Intent dash = new Intent(getApplicationContext(),
                        RegistrationPart3.class);
                dash.putExtra("user_firstname", fname);
                dash.putExtra("user_lastname", lname);
                dash.putExtra("user_email", email);
                dash.putExtra("user_password", password);
                dash.putExtra("user_retyped_password", c_password);
                dash.putExtra("user_dob", dob);
                dash.putExtra("user_address", address);
                dash.putExtra("user_town", town);
                dash.putExtra("user_country", country);
                dash.putExtra("user_postal", postcode);
                dash.putExtra("user_mobile", mobile);
                dash.putExtra("user_gender", gender);
                startActivity(dash);
            }
        });




    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    RegistrationPart1.class);
            startActivity(dash);
        }
        return true;
    }

}
