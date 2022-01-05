package com.erranddaddy.awsflashcards;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
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
                R.drawable.ic_navigation_drawer,
                R.string.app_name,
                R.string.app_name
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class DrawerListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
