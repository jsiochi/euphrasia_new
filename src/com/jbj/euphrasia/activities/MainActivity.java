package com.jbj.euphrasia.activities;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.ClearRemoteTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;

public class MainActivity extends ActionBarActivity implements Constants {
	
	private String myUser;
	private Cursor myCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myUser = this.getIntent().getStringExtra(EXTRA_EXISTING_USER);
		ActionBar actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public void onStartEntry(View view){
		startActivity(new Intent(this, EntryActivity.class));
	}
	
	public void onSyncDatabase(View view){
		Toast.makeText(this, "Attempting to sync to remote", Toast.LENGTH_LONG).show();
		getMainLooper();
		AbstractRemoteTask clear = new ClearRemoteTask();
		clear.setActivity(this);
		clear.execute(new String[]{"user_id", "0"});
		myCursor = getContentResolver().query(EntryProvider.CONTENT_URI, SELECT_ALL_PROJECTION, null,null,null);
		onSyncHelper();
	}

	public void onSyncHelper() {
		if(myCursor.moveToNext()) {
			int j;
			j = 0;
			List<String[]> params = new ArrayList<String[]>(); 
			for(int i=0;i<myCursor.getColumnCount();i++){
				String[] param = new String[2];
				param[0] = myCursor.getColumnName(i);
				param[1] = myCursor.getString(i);
				params.add(param);
				j++;
			}
			String[] param = new String[2];
			param[0] = "user_id";
			param[1] = "2";
			params.add(param);
			AbstractRemoteTask write = new WriteRemoteTask();
			write.setActivity(this);
			write.execute(params.toArray(new String[params.size()][2]));
		}
		else {
			myCursor.close();
		}
	}

//		AbstractRemoteTask testTask = new WriteRemoteTask();
//		testTask.setActivity(this);
//		ArrayList<String[]> stuff = new ArrayList<String[]>();
//		for(int i = 0; i<SELECT_ALL_PROJECTION.length-1;i++){
//			if(i==2){
//				String[] nestedStuff = {SELECT_ALL_PROJECTION[i],"2002-09-22 11:11:11"};
//				stuff.add(i, nestedStuff);
//			}
//			else{
//				String[] nestedStuff = {SELECT_ALL_PROJECTION[i],"blah"};
//				stuff.add(i, nestedStuff);
//			}
//		}
//		testTask.execute(stuff.toArray(new String[stuff.size()][2]));
//	}
	
	public void onStartSearch(View view){
		//retrieve all of the languages in the database and pass extra to Intermediate activity
		Intent toIntermediateIntent = new Intent(this,IntermediateSearchActivity.class);
		toIntermediateIntent.setAction(ACTION_SHOW_LANGUAGES);
		startActivity(toIntermediateIntent);
	}
	

}
