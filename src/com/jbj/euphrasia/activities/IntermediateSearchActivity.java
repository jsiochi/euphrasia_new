package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
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
		Spinner languageChoices = (Spinner) findViewById(R.id.browse_languages);
		languageChoices.setOnItemSelectedListener(this);
		
		SimpleCursorAdapter languageAdapter = getLanguageAdapter();
		languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		languageChoices.setAdapter(languageAdapter);
		
		if(ACTION_SHOW_LANGUAGES.equals(this.getIntent().getAction())){
			// get all of the languages out of the database
		}
		
		
	}

	private SimpleCursorAdapter getLanguageAdapter() {
		Cursor langCursor = getLanguages();
		String[] froms = {EntryColumns.COLUMN_NAME_LANGUAGE, EntryColumns._ID};
		int[] tos = {android.R.id.text1};
		SimpleCursorAdapter languageAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, langCursor,
                froms, tos, 0);
		
		return languageAdapter;
	}
	
	private Cursor getLanguages() {
		Cursor cursor = getContentResolver().query(EntryProvider.CONTENT_LANGUAGES_URI, null, null, null, null);
		
		String[] froms = {EntryColumns.COLUMN_NAME_LANGUAGE, EntryColumns._ID};
		MatrixCursor extras = new MatrixCursor(froms);
//		String[] extraPhrasebooks = getResources().getStringArray(R.array.test_phrasebooks);
//		for(int i = 1; i <= extraPhrasebooks.length; i++) {
//			extras.addRow(new String[] {extraPhrasebooks[i - 1], String.valueOf(-1*i)});
//		}
		extras.addRow(new String[] {"Choose language","-1"});
		
		Cursor[] cursors = {extras, cursor};
		
		return new MergeCursor(cursors);
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
	public void onPhrasebookBrowse(View view){
		// retrieve all phrasebooks from the database and pass extra to next activity. 
		Intent toBrowsePhrasebookIntent = new Intent(this,BrowsePhrasebookActivity.class);
		toBrowsePhrasebookIntent.setAction(ACTION_BROWSE_PHRASEBOOKS);
		startActivity(toBrowsePhrasebookIntent);
	}
	
	/**
	 * Launches listview of every entry in the database. 
	 */
	public void onViewAll(View view){
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
