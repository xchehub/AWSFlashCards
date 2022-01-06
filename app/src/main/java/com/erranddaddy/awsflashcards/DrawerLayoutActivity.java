package com.erranddaddy.awsflashcards;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

public abstract class DrawerLayoutActivity extends Activity {
    private final static String LOG_TAG = "DrawerActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle, mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        // Enable action bar app and icon
        // Behaving it as toggle button.
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Draw layout
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList.setOnItemClickListener(new DrawerListener());

        // Init
        init();

        mDrawerList.setAdapter(getAdapter());
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.drawable.ic_navigation_drawer,
                R.string.app_name,
                R.string.app_name
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if(savedInstanceState == null) {
            displayView(0, null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstance has occurred
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
            /**
             * A handler with a postDelayed is used so it only changes fragments once the drawer is
             * already closed. This can be adjusted if the timing is not right.
             * Similar behavior that most Google apps offers.
             */
            mDrawerLayout.closeDrawer(mDrawerList);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayView(position, null);
                }
            }, 300);
        }
    }

    /**
     * Define what clicking on the drawer will do.
     *
     * @param position The position of the clicked item.
     * @param fragmentBundle A bundle in case something need to be passed to a specific fragment.
     */
    public abstract void displayView(int position, Bundle fragmentBundle);

    /**
     * Specific initializations.
     */
    public abstract void init();

    /**
     * Override this method in case of need for a different list colors, etc..
     * Should use same Id's to avoid confusion
     *
     * @return The Activity layout for this drawer activity
     */
    private int getLayout() {
        return R.layout.drawer_activity;
    }

    /**
     * Getter for the drawer toggle
     *
     * @return The drawer toggle for this activity.
     */
    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    /**
     * @return The list used by the drawer.
     */
    public ListView getDrawerList() {
        return mDrawerList;
    }

    /**
     * @return The drawer Layout used by this activity.
     */
    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    /**
     * Method to be Overridden that will return an Adapter that extends Base Adapter.
     * The adapter will them be used by the Drawer Layout.
     */
    protected abstract BaseAdapter getAdapter();
}
