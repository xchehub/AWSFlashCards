package com.erranddaddy.awsflashcards;

import android.content.res.TypedArray;
import android.os.Bundle;

import android.widget.BaseAdapter;

import com.erranddaddy.awsflashcards.navDrawer.NavDrawerAdapter;
import com.erranddaddy.awsflashcards.navDrawer.NavDrawerItem;

import java.util.ArrayList;

public class MainActivity extends DrawerLayoutActivity {

    private NavDrawerAdapter mNavDrawerAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;

    @Override
    public void init() {
        // Retrieve the typedArray from the XML. Notice the weird Syntax "obtain"
        TypedArray navIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        // Retrieve the titles
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_titles);
        // Initialize the ArrayList
        navDrawerItems = new ArrayList<NavDrawerItem>();

        // Add items to the ArrayList of NavDrawer items.
        for(int i = 0; i < navMenuTitles.length; i++) {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navIcons.getDrawable(i)));
        }

        // An typed array can be recycled to avoid waste of System Resources.
        // Though we only have 2 items, it is still a good practice.
        navIcons.recycle();

        mNavDrawerAdapter = new NavDrawerAdapter(this, navDrawerItems);
    }

    @Override
    public void displayView(int position, Bundle fragmentBundle) {

    }

    @Override
    protected BaseAdapter getAdapter() {
        return mNavDrawerAdapter;
    }
}
O