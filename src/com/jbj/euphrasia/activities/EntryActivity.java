package com.jbj.euphrasia.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.id;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.fields.AudioField;
import com.jbj.euphrasia.fields.DateField;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.fields.FieldFactory;
import com.jbj.euphrasia.fields.ForeignTextField;
import com.jbj.euphrasia.fields.LanguageField;
import com.jbj.euphrasia.fields.NativeTextField;
import com.jbj.euphrasia.fields.PhrasebookField;
import com.jbj.euphrasia.fields.TagField;
import com.jbj.euphrasia.fields.TitleField;
import com.jbj.euphrasia.interfaces.Constants;

import android.net.Uri;
import dialog_fragments.ConfirmSaveDialog;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EntryActivity extends FragmentActivity implements Constants, EntryContract, OnItemSelectedListener {
	
	private FieldFactory myFieldFactory;
	private Controller myController;
	private Map<String,EditText> myTextViews = new HashMap<String,EditText>();
	private ContentValues myInitialData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		if(Constants.ACTION_GET_ENTRY_DATA.equals(getIntent().getAction())) {
			myInitialData = processIntent();
		}
		myFieldFactory = new FieldFactory();
		myController = new Controller(this);
		Spinner spinner = (Spinner) findViewById(R.id.entry_phrasebook_spinner);
		spinner.setOnItemSelectedListener(this);
		//String[] test = {"Phrasebook 1", "Phrasebook 2"};
		ArrayAdapter<String> adapter = getPhrasebooks();
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		findTextViews();
		setUpTextViews();
		loadInitialData();
	}
	
	
	private void loadInitialData() {
		if(myInitialData != null) {
			String audioPath = myInitialData.getAsString(EntryColumns.COLUMN_NAME_AUDIO);
			myController.updateEntryField(new AudioField(audioPath));
			String title = myInitialData.getAsString(EntryColumns.COLUMN_NAME_TITLE);
			myController.updateEntryField(new TitleField(title));
			String date = myInitialData.getAsString(EntryColumns.COLUMN_NAME_DATE);
			myController.updateEntryField(new DateField(date));
			String language = myInitialData.getAsString(EntryColumns.COLUMN_NAME_LANGUAGE);
			myController.updateEntryField(new LanguageField(language));
			String nativeText = myInitialData.getAsString(EntryColumns.COLUMN_NAME_NATIVE_TEXT);
			myController.updateEntryField(new NativeTextField(nativeText));
			String foreignText = myInitialData.getAsString(EntryColumns.COLUMN_NAME_FOREIGN_TEXT);
			myController.updateEntryField(new ForeignTextField(foreignText));
			String tagText = myInitialData.getAsString(EntryColumns.COLUMN_NAME_TAG);
			myController.updateEntryField(new TagField(tagText));
			myController.setUri(Uri.withAppendedPath(EntryProvider.CONTENT_URI, 
					String.valueOf(myInitialData.getAsLong("URI_id"))));
			
		}
	}

	/**
	 * adds listeners and checks for initial data
	 */
	private void setUpTextViews() {
		for(String key:myTextViews.keySet()){
			EditText textView = myTextViews.get(key);
			textView.setOnFocusChangeListener(new OnFocusChangeListener(){
				@Override
			    public void onFocusChange(View view, boolean isFocused) {
			        if (!isFocused) {
			        	Log.d("TAG","forein_focus_change");
			            updateField(view);
			        }
			    }
			});
			if (myInitialData != null && myInitialData.containsKey(key)) {
				String initialValue = myInitialData.getAsString(key);
				textView.setText(initialValue);
			}
		}
	}

	/**
	 * stores all view items in a map to reference by database column names
	 */
	private void findTextViews() {
		EditText nativeText = (EditText) findViewById(R.id.native_text);
		EditText foreignText = (EditText) findViewById(R.id.foreign_text);
		EditText tagText = (EditText) findViewById(R.id.edit_tags);
		EditText languageText = (EditText) findViewById(R.id.edit_language);
		EditText titleText = (EditText) findViewById(R.id.edit_title);
		myTextViews.put(EntryColumns.COLUMN_NAME_NATIVE_TEXT,nativeText);
		myTextViews.put(EntryColumns.COLUMN_NAME_FOREIGN_TEXT,foreignText);
		myTextViews.put(EntryColumns.COLUMN_NAME_TAG,tagText);
		myTextViews.put(EntryColumns.COLUMN_NAME_LANGUAGE, languageText);
		myTextViews.put(EntryColumns.COLUMN_NAME_TITLE, titleText);
	}

	private ContentValues processIntent() {
		Intent intent = this.getIntent();
		return (ContentValues)intent.getParcelableExtra(ENTRY_INTENT_PARCELABLE);
	}
		
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}
*/	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.entry, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.save:
	        	handleSave();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
/** get appropriate EditText object
 * Call this method whenever an EditText component losesFocus
 */
	public void updateField(View view){
		if(!view.hasFocus()){
			//TODO is it too inefficient to create a new field object every time?
			EditText editText = (EditText) view;
			Field field = myFieldFactory.createField(view.getId(), editText.getText().toString());
			myController.updateEntryField(field);
			Log.i("new field",field.toString() + field.getClass().getName());
		}
	}
	
	private void updateFieldFromText(EditText editText){
			//TODO is it too inefficient to create a new field object every time?
			Field field = myFieldFactory.createField(editText.getId(), editText.getText().toString());
			myController.updateEntryField(field);
			Log.i("new field",field.toString() + field.getClass().getName());
	}
	
/**
 * @author Bradley
 * To record, user presses a record button. Toggles boolean value to indicate whether recording 
 * should begin.
 * When toggled off, presented option to save, playback, or record again. 
 */
	public void handleRecording(View view){
		myController.onRecord();
	}
	
	public void handlePlay(View view){
		myController.onPlay();
	}
	

	public void handleSave(/*View view*/){
		if(!myController.shouldSave()) {
			Log.i("CHECK_SAVE","not enough to save");
		}
		ConfirmSaveDialog dlg = new ConfirmSaveDialog();
		dlg.setSourceActivity(this);
	    dlg.show(getSupportFragmentManager(), "confirm_save");
	}
	
    public void confirmSave(){
    	for(String s : myTextViews.keySet()) {
			updateFieldFromText(myTextViews.get(s));
		}
    	myController.onSave();
    }


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// create a new entry field and update EntryDatabaseManager
		String selected = parent.getSelectedItem().toString();
		Log.i("EntryActivity",selected);
		
		myController.updateEntryField(new PhrasebookField(selected));
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private ArrayAdapter<String> getPhrasebooks() {
		Bundle bundle = getContentResolver().call(EntryProvider.CONTENT_URI, EntryProvider.GET_PHRASEBOOKS, null, null);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        R.array.test_phrasebooks, android.R.layout.simple_spinner_item);
		adapter.addAll(bundle.getStringArrayList(EntryProvider.GET_PHRASEBOOKS));
		
		return adapter;
	}
	
}


