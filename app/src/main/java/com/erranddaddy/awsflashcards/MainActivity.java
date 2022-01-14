package com.erranddaddy.awsflashcards;

import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;

import com.erranddaddy.awsflashcards.fragments.DefaultFragment;
import com.erranddaddy.awsflashcards.fragments.FragmentCategories;
import com.erranddaddy.awsflashcards.navDrawer.NavDrawerAdapter;
import com.erranddaddy.awsflashcards.navDrawer.NavDrawerItem;

import java.util.ArrayList;

public class MainActivity extends DrawerLayoutActivity {

    private NavDrawerAdapter mNavDrawerAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;

    // Check which of the items has been selected.
    // Name the items so we know which one is which.
    public static final int CATEGORIES_FRAG = 0;
    public static final int SETTINGS_FRAG = 1;
    private DefaultFragment activeFragment = null;

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
        switch (position) {
            case CATEGORIES_FRAG:
                // Set the activeFragment to selected item on the list
                activeFragment = new FragmentCategories();
                break;
            case SETTINGS_FRAG:
                break;
            default:
                break;
        }

        if (activeFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.alpha_in, R.animator.alpha_out,
                            R.animator.alpha_in, R.animator.alpha_out)
                    .replace(R.id.frame_container, activeFragment)
                    .commit();

            // update selected item and title
            getDrawerList().setItemChecked(position, true);
            getDrawerList().setSelection(position);
            setTitle(navMenuTitles[position]);
        } else {
            Log.i(getLogTag(), "Error creating fragment.");
        }
    }

    @Override
    protected BaseAdapter getAdapter() {
        return mNavDrawerAdapter;
    }
}