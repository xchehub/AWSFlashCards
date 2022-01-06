package com.erranddaddy.awsflashcards.navDrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.erranddaddy.awsflashcards.R;

import java.util.ArrayList;

public class NavDrawerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    // Default constructor
    public NavDrawerAdapter() {
    }

    /**
     * @param context The Context on which this NavDrawer is being created
     * @param navDrawerItems The ArrayList containing the DrawersItems for the Adapter.
     */
    public NavDrawerAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<NavDrawerItem> getNavDrawerItems() {
        return navDrawerItems;
    }

    public void setNavDrawerItems(ArrayList<NavDrawerItem> navDrawerItems) {
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int i) {
        return navDrawerItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * The GetView is responsible for inflating and creating the View for each one of the items on the ListView
     * To get different behavior on the Items on a List you can do them so on the getView
     * By Example, for a list with alternate colors we could do.
     * if(position % 2 == 0) {
     *     convertView.setBackgroundColor(Color.BLUE);
     * } else {
     *      convertView.setBackgroundColor(Color.RED);
     * }
     * Other Layouts can also be inflated based on the position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        ImageView imageIcon = convertView.findViewById(R.id.navDrawerIcon);
        TextView title = (TextView) convertView.findViewById(R.id.navDrawerTitle);

        title.setText(navDrawerItems.get(position).getTitle());
        imageIcon.setImageDrawable(navDrawerItems.get(position).getIcon());

        return convertView;
    }
}
