package dashboardActivities;

import java.util.HashMap;

import mailing.Sender;

import com.example.software.R;



import com.example.software.library.UserSessionManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUs extends Menu {

    EditText subject , details;
    TextView t;

    Button Send , Back;

    UserSessionManager session;

    String username,email,sub, body,pass;
    String comp_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        //fonts
        t = (TextView) findViewById(R.id.textView1);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kaushan_script.otf");
        t.setTypeface(font);
        session = new UserSessionManager(getApplicationContext());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        subject = (EditText) findViewById(R.id.editSubject);
        details = (EditText) findViewById(R.id.editText1);
        Send = (Button) findViewById(R.id.btnSendEmail);


        final AlertDialog.Builder adb = new AlertDialog.Builder(
                ContactUs.this);

        Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                HashMap<String, String> user = session.getUserDetails();



                username = user.get(UserSessionManager.KEY_NAME);
                email = user.get(UserSessionManager.KEY_EMAIL);
                pass =  user.get(UserSessionManager.KEY_Pass);

                if ((subject.length() > 0) && (details.length() > 0)) {

                    //email the admin
                    sub = subject.getText().toString();
                    body = details.getText().toString();
                    comp_body = body + "\nSent by "+email;
                    new Thread() {
                        public void run() {
                            try {
                                Sender sender = new Sender("androidsff@gmail.com",
                                        "admin123.");
                                sender.sendMail(sub, comp_body,
                                        email, "androidsff@gmail.com");

                                runOnUiThread(new Runnable() {

                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                username+" your email has been sent to the Admin\n"
                                                        + "", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });


                                Log.i("contact us", email+" "+pass+" mail sent");
                            } catch (Exception e) {

                                Log.i("contact us", "exception error");
                            }
                        }
                    }.start();

                } else {
                    adb.setTitle("Missing Data");
                    adb.setMessage("Please ensure that you have filled Secret Answer.");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                }
            }
        });



    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    about.class); //change class to activity u want to go back to.
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), about.class);
                NavUtils.navigateUpTo(this, i);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
