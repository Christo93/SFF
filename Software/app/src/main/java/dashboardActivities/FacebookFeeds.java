package dashboardActivities;

import com.example.software.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FacebookFeeds extends FragMenu {

    private EditText field;
    private WebView browser;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    View view;

    public FacebookFeeds() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_facebook_feeds, container,
                false);

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        textView = (TextView) view.findViewById(R.id.textView1);

        // field = (EditText)findViewById(R.id.urlField);
        browser = (WebView) view.findViewById(R.id.webView1);
        // progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        String url = "http://m.facebook.com/SoftwareFoundationFiji?v=timeline&filter=1&refid=17";
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);

        browser.setWebViewClient(new MyBrowser());

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);

            return true;
        }
    }
	
	/*back button
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{       
	  if(keyCode==KeyEvent.KEYCODE_BACK)

	         {
		//  			Intent dash = new Intent(getActivity().getApplicationContext(),
		//			FragMenu.class); //change class to activity u want to go back to.
	        }
	return false;
	    } */

}
