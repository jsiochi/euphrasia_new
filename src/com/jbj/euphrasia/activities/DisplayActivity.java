package com.jbj.euphrasia.activities;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.array;
import com.jbj.euphrasia.R.drawable;
import com.jbj.euphrasia.R.id;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.R.string;

public class DisplayActivity extends Activity {
	
	// Within which the entire activity is enclosed
	private DrawerLayout mDrawerLayout;
	 
	// ListView represents Navigation Drawer
	private ListView mDrawerList;
	 
    // ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
    private ActionBarDrawerToggle mDrawerToggle;
    
    // Title of the action bar
    private String mTitle = "";
    
    // slide menu items
 	private String[] navMenuTitles;
 	private TypedArray navMenuIcons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		
		// Getting reference to the DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.fragment_title_array);
		
		
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		 
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		 
		// Getting reference to the ActionBarDrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
		        R.drawable.ic_drawer, R.string.drawer_open,
		        R.string.drawer_close) {
		 
		        /** Called when drawer is closed */
		        public void onDrawerClosed(View view) {
		            getActionBar().setTitle(mTitle);
		            invalidateOptionsMenu();
		        }
		 
		        /** Called when a drawer is opened */
		        public void onDrawerOpened(View drawerView) {
		            getActionBar().setTitle("Euphrasia");
		            invalidateOptionsMenu();
		        }
		 
		    };
		 
		// Setting DrawerToggle on DrawerLayout
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        if (savedInstanceState == null) {
			// on first time display view for first nav item
 			Navigate(0);
 		}
		
		// Creating an ArrayAdapter to add items to the listview mDrawerList
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
		        R.layout.drawer_list_item, getResources().getStringArray(R.array.menu_title_array));
		 
		// Setting the adapter on mDrawerList
		mDrawerList.setAdapter(adapter);
		
		// Setting item click listener for the listview mDrawerList
	    mDrawerList.setOnItemClickListener(new OnItemClickListener() {
	 
	         @Override
	         public void onItemClick(AdapterView<?> parent, View view,
	                 int position, long id) {
	 
        		 // Getting an array of menu titles
        	     String[] menuItems = getResources().getStringArray(R.array.menu_title_array);

        	     // Currently selected river
        	     mTitle = menuItems[position];
	        	 
	        	 // Creating a fragment object
	             Navigate(position);
	             
	             // Passing selected item information to fragment
	             /*Bundle data = new Bundle();
	             data.putInt("position", position);
	             fragment.setArguments(data);*/	 
	         }
	    });
	}
	@Override
	public void setTitle(CharSequence title) {
		mTitle = (String) title;
		getActionBar().setTitle(mTitle);
	}
	         
	     /**
	 	 * Diplaying fragment view for selected nav drawer list item
	 	 * */
	 	private void Navigate(int position) {
	 		// update the main content by replacing fragments
	 		Fragment fragment = null;
	 		switch (position) {
	 		case 0:
	 			fragment = new MainFragment();
	 			break;
	 		case 1:
	 			fragment = new EntryFragment();
	 			break;
	 		case 2:
	 			fragment = new SearchFragment();
	 			break;
	 		case 3:
	 			fragment = new RemoteFragment();
	 			break;
	 		case 4:
	 			fragment = new AccountFragment();
	 			break;
	 		case 5:
	 			fragment = new SettingsFragment();
	 			break;
	 		
	 		default:
	 			break;
	 		}

	 		if (fragment == null) {
	 		// error in creating fragment
				Log.e("DisplayActivity", "Error in creating fragment");
			}
	 		
	 		// Getting reference to the FragmentManager
            FragmentManager fragmentManager = getFragmentManager();

            // Creating a fragment transaction
            FragmentTransaction ft = fragmentManager.beginTransaction();

            // Adding a fragment to the fragment transaction
            ft.replace(R.id.content_frame, fragment);

            // Committing the transaction
            ft.commit();
            
            // update selected item and title
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);

            // Closing the drawer
            mDrawerLayout.closeDrawer(mDrawerList);;
	 }

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
                super.onPostCreate(savedInstanceState);
             // Sync the toggle state after onRestoreInstanceState has occurred.
                mDrawerToggle.syncState();
        }
        
        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            mDrawerToggle.onConfigurationChanged(newConfig);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                	// Pass the event to ActionBarDrawerToggle, if it returns
                    // true, then it has handled the app icon touch event
                        return true;
                }
                // Handle your other action bar items...
                
                return super.onOptionsItemSelected(item);
        }

        /** Called whenever we call invalidateOptionsMenu() */
        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
                // If the drawer is open, hide action items related to the content view
                boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

                menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
                return super.onPrepareOptionsMenu(menu);
        }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}

}
