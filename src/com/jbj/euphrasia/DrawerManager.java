package com.jbj.euphrasia;

import com.jbj.euphrasia.activities.EntryActivity;
import com.jbj.euphrasia.activities.LoginActivity;
import com.jbj.euphrasia.activities.MainActivity;
import com.jbj.euphrasia.activities.RemoteSearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class DrawerManager {
	
	// Within which the entire activity is enclosed
	private static DrawerLayout mDrawerLayout;
	 
	// ListView represents Navigation Drawer
	private static ListView mDrawerList;
	 
    // ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
    private static ActionBarDrawerToggle mDrawerToggle;
    
    // Title of the action bar
    private static String mTitle = "";
    
    // slide menu items
 	private static String[] navMenuTitles;
 	private static TypedArray navMenuIcons;
 	private static Activity myActivity;


	public static void initialize(Bundle savedInstanceState, Activity activity){
		// Getting reference to the DrawerLayout
				myActivity = activity;
				mDrawerLayout = (DrawerLayout) myActivity.findViewById(R.id.drawer_layout);
				
				// load slide menu items
				navMenuTitles = myActivity.getResources().getStringArray(R.array.fragment_title_array);
				
				
				// nav drawer icons from resources
				navMenuIcons = myActivity.getResources()
						.obtainTypedArray(R.array.nav_drawer_icons);
				 
				mDrawerList = (ListView) myActivity.findViewById(R.id.drawer_list);
				 
				// Getting reference to the ActionBarDrawerToggle
				mDrawerToggle = new ActionBarDrawerToggle(myActivity, mDrawerLayout,
				        R.drawable.ic_drawer, R.string.drawer_open,
				        R.string.drawer_close) {
				 
				        /** Called when drawer is closed */
				        public void onDrawerClosed(View view) {
				        	myActivity.getActionBar().setTitle(mTitle);
				        	myActivity.invalidateOptionsMenu();
				        }
				 
				        /** Called when a drawer is opened */
				        public void onDrawerOpened(View drawerView) {
				        	myActivity.getActionBar().setTitle("Euphrasia");
				        	myActivity.invalidateOptionsMenu();
				        }
				 
				    };
				 
				// Setting DrawerToggle on DrawerLayout
				    mDrawerLayout.setDrawerListener(mDrawerToggle);
				
				    myActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
				    myActivity.getActionBar().setHomeButtonEnabled(true);
		        
		        if (savedInstanceState == null) {
					// on first time display view for first nav item
		        	Navigate(2);
		 		}
		        
		     // Creating an ArrayAdapter to add items to the listview mDrawerList
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(myActivity.getBaseContext(),
				        R.layout.drawer_list_item, myActivity.getResources().getStringArray(R.array.menu_title_array));
				 
				// Setting the adapter on mDrawerList
				mDrawerList.setAdapter(adapter);
				
				// Setting item click listener for the listview mDrawerList
			    mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			 
			         @Override
			         public void onItemClick(AdapterView<?> parent, View view,
			                 int position, long id) {
			 
		        		 // Getting an array of menu titles
		        	     String[] menuItems = myActivity.getResources().getStringArray(R.array.menu_title_array);

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
	
	public static void setTitle(CharSequence title) {
		mTitle = (String) title;
		myActivity.getActionBar().setTitle(mTitle);
	}
	
	/**
 	 * Diplaying fragment view for selected nav drawer list item
 	 * */
 	private static void Navigate(int position) {
 		// update the main content by replacing fragments
 		Intent intent = null;
 		if (position != 2) {
	 		switch (position) {
	 		case 0:
	 			myActivity.startActivity(new Intent(myActivity, MainActivity.class));
	 			break;
	 		case 1:
	 			myActivity.startActivity(new Intent(myActivity, EntryActivity.class));
	 			break;
	 		case 3:
	 			myActivity.startActivity(new Intent(myActivity,RemoteSearchActivity.class));
	 			break;
	 		case 4:
	 			intent = new Intent(myActivity, LoginActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            myActivity.startActivity(intent);
	 			break;
	 		/*case 5:
	 			intent = new Intent(currentActivity, SettingsActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            currentActivity.startActivity(intent);
	 			break;*/
	 		
	 		default:
	 			break;
	 		}
 		

	 		if (intent == null) {
	 		// error in creating intent
				Log.e("DisplayActivity", "Error in creating intent");
			}
	 		
	        // update selected item and title
	        mDrawerList.setItemChecked(position, true);
	        mDrawerList.setSelection(position);
	        setTitle(navMenuTitles[position]);
	
	        // Closing the drawer
	        mDrawerLayout.closeDrawer(mDrawerList);
	        
 		}
 		else {
 			
 		};
 }
 	
 	public static void changeConfig(Configuration newConfig){
 		 mDrawerToggle.onConfigurationChanged(newConfig);
 	}
 	
 	public static void syncDrawerToggle(){
 		mDrawerToggle.syncState();
 	}
 	
 	public static boolean isDrawerOpen(){
 		return mDrawerLayout.isDrawerOpen(mDrawerList);
 	}
 	
 	public static boolean isItemSelected(MenuItem item){
 		return mDrawerToggle.onOptionsItemSelected(item);
 	}
 	
}
