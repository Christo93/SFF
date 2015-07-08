package com.example.software;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Fonts extends DashboardFragment {

    ImageView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.membershipdetails);
        setContentView(R.layout.register2);
        setContentView(R.layout.registration_cont);
        setContentView(R.layout.registration_cont2);
        setContentView(R.layout.drawer_list_item);
        setContentView(R.layout.changepassword);
        setContentView(R.layout.editprofile);
        setContentView(R.layout.fragment_events);
        setContentView(R.layout.events_view);
        setContentView(R.layout.news_view);
        setContentView(R.layout.forgot_password);


        initTypeface();


    }




    //custom fonts
    private void initTypeface() {
        Typeface lily = Typeface.createFromAsset(getAssets(), "fonts/lily.ttf");
        Typeface lemon = Typeface.createFromAsset(getAssets(), "fonts/lemon.ttf");
        Typeface mountain = Typeface.createFromAsset(getAssets(), "fonts/mountain.ttf");
        Typeface kaushan = Typeface.createFromAsset(getAssets(), "kaushan_script_regular.ttf");

        TextView reg = (TextView) findViewById(R.id.btnLinkToRegisterScreen);
        TextView mem = (TextView) findViewById (R.id.memberTitle);
        TextView title = (TextView) findViewById(R.id.title);
        TextView changePass = (TextView) findViewById(R.id.changePass);
        TextView editProf = (TextView) findViewById(R.id.editProf);
        TextView event = (TextView) findViewById(R.id.event_title);
        TextView event_v = (TextView) findViewById(R.id.event_v);
        TextView news_v = (TextView) findViewById(R.id.news_v);
        TextView forgot_pass = (TextView) findViewById(R.id.forgot_pass);


        //Button login = (Button) findViewById(R.id.login);

        //login.setTypeface(lily);

        mem.setTypeface(kaushan);
        reg.setTypeface(kaushan);
        title.setTypeface(kaushan);
        changePass.setTypeface(kaushan);
        editProf.setTypeface(kaushan);
        event.setTypeface(kaushan);
        event_v.setTypeface(lemon);
        news_v.setTypeface(lemon);
        forgot_pass.setTypeface(lily);


    }


}

	
	

