package com.jbj.euphrasia.activities;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.LogoutManager;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.SyncManager;
import com.jbj.euphrasia.R.layout;

import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.AudioUploadTask;
import com.jbj.euphrasia.remote.ClearRemoteTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;

public class MainActivity extends Activity implements Constants {
	
	private String myUser;
	private Cursor myCursor;
	
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
	 	
	 // Name the Activity
	 	private Activity currentActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myUser = this.getIntent().getStringExtra(EXTRA_EXISTING_USER);
		//ActionBar actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
		
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
 		Intent intent = null;
 		if (position != 0) {
	 		switch (position) {
	/* 		case 0:
	 			startActivity(new Intent(this, MainActivity.class));
	 			break;*/
	 		case 1:
	 			startActivity(new Intent(this, EntryActivity.class));
	 			break;
	 		case 2:
	 			Intent toIntermediateIntent = new Intent(this,IntermediateSearchActivity.class);
	 			toIntermediateIntent.setAction(ACTION_SHOW_LANGUAGES);
	 			startActivity(toIntermediateIntent);
	 			break;
	 		case 3:
	 			startActivity(new Intent(this,RemoteSearchActivity.class));
	 			break;
	 		case 4:
	 			intent = new Intent(currentActivity, LoginActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            currentActivity.startActivity(intent);
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
	 		// error in creating fragment
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
	
	public void onStartEntry(View view){
		startActivity(new Intent(this, EntryActivity.class));
	}
	
	public void onSyncDatabase(View view){
		SyncManager.setActivity(this);
		SyncManager.sync();
	}

	public void onStartSearch(View view){
		//retrieve all of the languages in the database and pass extra to Intermediate activity
		Intent toIntermediateIntent = new Intent(this,IntermediateSearchActivity.class);
		toIntermediateIntent.setAction(ACTION_SHOW_LANGUAGES);
		startActivity(toIntermediateIntent);
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

    /** Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
            // If the drawer is open, hide action items related to the content view
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

            //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    menu.findItem(R.id.sync).setIcon(R.drawable.sync);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		if (mDrawerToggle.onOptionsItemSelected(item)) {
        	// Pass the event to ActionBarDrawerToggle, if it returns
            // true, then it has handled the app icon touch event
                return true;
        }
        // Handle your other action bar items...
	    switch (item.getItemId()) {
	        case R.id.sync:
	        	SyncManager.setActivity(this);
	        	SyncManager.sync();
	        	return true;
	        case R.id.logout:
	        	LogoutManager.setActivity(this);
	        	LogoutManager.logout();
	        	return true;
	        case R.id.about:
	        	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://goeuphrasia.com"));
	        	startActivity(browserIntent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}


	public void onBrowseRemote(View view){
		startActivity(new Intent(this,RemoteSearchActivity.class));
	}
	

}
