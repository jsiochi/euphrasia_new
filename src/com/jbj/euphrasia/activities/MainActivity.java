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
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;

import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.managers.DrawerManager;
import com.jbj.euphrasia.managers.LogoutManager;
import com.jbj.euphrasia.managers.SyncManager;
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
		
		DrawerManager.initialize(savedInstanceState,this);
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
            DrawerManager.syncDrawerToggle();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DrawerManager.changeConfig(newConfig);
        
    }
	
    /** Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
            // If the drawer is open, hide action items related to the content view
            DrawerManager.isDrawerOpen();

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
		if (DrawerManager.isItemSelected(item)) {
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
