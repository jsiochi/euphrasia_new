package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.spinners.LanguageSpinner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class IntermediateSearchActivity extends Activity implements Constants, OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
		LanguageSpinner languageChoices = (LanguageSpinner) findViewById(R.id.browse_languages);
		languageChoices.setActivitySource(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse, menu);
		return true;
	}
	
	/**
	 * launches listview of every phrasebook in the database. 
	 */
	public void onPhrasebookBrowse(){
		// retrieve all phrasebooks from the database and pass extra to next activity. 
		Intent toBrowsePhrasebookIntent = new Intent(this,BrowsePhrasebookActivity.class);
		toBrowsePhrasebookIntent.setAction(ACTION_BROWSE_PHRASEBOOKS);
		startActivity(toBrowsePhrasebookIntent);
	}
	
	/**
	 * Launches listview of every entry in the database. 
	 */
	public void onViewAll(){
		Intent viewAllIntent = new Intent(this,SearchActivity.class);
		viewAllIntent.setAction(ACTION_VIEW_ALL);
		startActivity(new Intent(this,SearchActivity.class));
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
