package com.jbj.euphrasia.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;

public class MainActivity extends ActionBarActivity implements Constants {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public void onStartEntry(View view){
		startActivity(new Intent(this, EntryActivity.class));
	}
	
	public void onSyncDatabase(View view){
		AbstractRemoteTask write = new WriteRemoteTask();
		write.setActivity(this);
		String[] projection = {EntryContract.EntryColumns.COLUMN_NAME_TITLE, EntryContract.EntryColumns.COLUMN_NAME_TAG};
		Cursor cursor = getContentResolver().query(EntryProvider.CONTENT_URI, projection, null,null,null);
		int j;
		while(cursor.moveToNext()){
			j = 0;
			String[][] params = new String[][]{}; 
			for(int i=0;i<cursor.getColumnCount();i++){
				String[] param = new String[]{};
				param[0] = cursor.getColumnName(i);
				param[1] = cursor.getString(i);
				params[j] = param;
				j++;
			}
			//write each entry to the remote database before continuing
			write.execute(params);
		}
	}
	
	public void onStartSearch(View view){
		//retrieve all of the languages in the database and pass extra to Intermediate activity
		Intent toIntermediateIntent = new Intent(this,IntermediateSearchActivity.class);
		toIntermediateIntent.setAction(ACTION_SHOW_LANGUAGES);
		startActivity(toIntermediateIntent);
	}
	

}
