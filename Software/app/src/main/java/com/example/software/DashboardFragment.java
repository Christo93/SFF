package com.example.software;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;






import com.example.software.library.UserFunctions;
import com.example.software.library.UserSessionManager;




import dashboardActivities.Events;
import dashboardActivities.FacebookFeeds;
import dashboardActivities.Gallery_View;
import dashboardActivities.LogOut;
import dashboardActivities.MembershipDetails;
import dashboardActivities.News;
import dashboardActivities.UserProfile;
import dashboardActivities.Video;
import dashboardActivities.about;
import discussion.Discussion;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DashboardFragment extends FragmentActivity implements TrialTimeNoticeDialog.NoticeDialogListener {
    public static DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    public ActionBarDrawerToggle mDrawerToggle;

    private String TAG = "Dashboard Fragment activity";

    public static final String TRIAL = "TRIAL EXPIRED";

    ;

    // nav drawer title
    public CharSequence mDrawerTitle;

    // used to store app title
    public CharSequence mTitle;

    // slide menu items
    public static String[] navMenuTitles;
    public TypedArray navMenuIcons;

    public ArrayList<NavDrawerItem> navDrawerItems;
    public NavDrawerListAdapter adapter;

    final UserFunctions userFunction = new UserFunctions();
    private char state;
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new UserSessionManager(getApplicationContext());
        state = getIntent().getCharExtra(TRIAL,'A');
        if(state == 'A'){
            setContentView(R.layout.activity_main);
        }else{
            showNoticeDialog();
            setContentView(R.layout.activity_main);
        }

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Personal Profile
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
                .getResourceId(0, -1)));
        // Membership Details
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
                .getResourceId(1, -1)));
        // Events
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
                .getResourceId(2, -1)));
        // Facebook Feeds
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
                .getResourceId(3, -1)));
        // Gallery
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
                .getResourceId(4, -1)));
        // News & Announcements
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
                .getResourceId(5, -1)));
        // videos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
                .getResourceId(6, -1)));
        // Discussion Forum, We will add a counter here
        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
        //	.getResourceId(7, -1), true, "50+"));
        // about
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
                .getResourceId(7, -1)));
        // Logout
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
                .getResourceId(8, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.drawer, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for
                // accessibility
                R.string.app_name // nav drawer close - description for
                // accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            // uncomment this when you add fragments
            displayView(0);
        }


        if(session.checkLogin())
            finish();

        HashMap<String, String> user = session.getUserDetails();


        //new LongOperation().execute("");

    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it

        DialogFragment dialog = new TrialTimeNoticeDialog();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {


    }

    /**
     * Slide menu item click listener
     * */
    public class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            // uncomment this when you add fragments
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */

    // Link fragments as you create them
    // here=========================================

    public void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new UserProfile();
                break;
            case 1:
                fragment = new MembershipDetails();
                break;
            case 2:
                fragment = new Events();
                break;
            case 3:
                fragment = new FacebookFeeds();
                break;
            case 4:
                fragment = new Gallery_View();
                break;
            case 5:
                fragment = new News();
                break;
            case 6:
                fragment = new Video();
                break;
            //case 7:
            //	fragment = new Discussion();
            //	break;
            case 7:
                fragment = new about();
                break;
            case 8:
                fragment = new LogOut();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //back button
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)

        {
            Intent dash = new Intent(getApplicationContext(),
                    LoginAct.class); //change class to activity u want to go back to.
            Log.i("press back", "dashbpard");
        }
        return true;
    }



}
