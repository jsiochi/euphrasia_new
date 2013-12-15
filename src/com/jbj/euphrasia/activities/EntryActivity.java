package com.jbj.euphrasia.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.LogoutManager;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.id;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.SyncManager;
import com.jbj.euphrasia.dialog_fragments.ConfirmSaveDialog;
import com.jbj.euphrasia.dialog_fragments.CreatePhrasebookDialog;
import com.jbj.euphrasia.dialog_fragments.EntryDialogFragment;
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
import com.jbj.euphrasia.spinners.LanguageSpinner;
import com.jbj.euphrasia.spinners.PhrasebookSpinner;

import android.net.Uri;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class EntryActivity extends FragmentActivity implements Constants, EntryContract{
	
	private FieldFactory myFieldFactory;
	private Controller myController;
	private Map<String,EditText> myTextViews = new HashMap<String,EditText>();
	private ContentValues myInitialData;
	private String myLanguage;
	private String myPhrasebook;
	
	//TODO make an instance variable for adapter; look into making a cursor adapter from the content provider


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		if(Constants.ACTION_GET_ENTRY_DATA.equals(getIntent().getAction())) {
			myInitialData = processIntent();
			myLanguage = myInitialData.getAsString(EntryColumns.COLUMN_NAME_LANGUAGE);
			myPhrasebook = myInitialData.getAsString(EntryColumns.COLUMN_NAME_PHRASEBOOK);
		}
		myFieldFactory = new FieldFactory();
		myController = new Controller(this);
		myController.setSourceActivity(this);
		
		LanguageSpinner languageSpinner = (LanguageSpinner) findViewById(R.id.select_language);
		languageSpinner.setActivitySource(this);
		
		PhrasebookSpinner phrasebookSpinner = (PhrasebookSpinner) findViewById(R.id.entry_phrasebook_spinner);
		phrasebookSpinner.setActivitySource(this);
		if(Constants.ACTION_GET_ENTRY_DATA.equals(getIntent().getAction())) {
			myInitialData = processIntent();
			myLanguage = myInitialData.getAsString(EntryColumns.COLUMN_NAME_LANGUAGE);
			myPhrasebook = myInitialData.getAsString(EntryColumns.COLUMN_NAME_PHRASEBOOK);
			
			languageSpinner.load(myLanguage);
			phrasebookSpinner.load(myPhrasebook);
		}
		
		findTextViews();
		setUpTextViews();
		loadInitialData();
		
	}
	
	public void setLanguage(String language){
		myLanguage = language;
	}
	
	public void setPhrasebook(String phrasebook){
		myPhrasebook = phrasebook;
	}

	public Controller getController(){
		return myController;
	}
	
	public void enablePlay(){
		Button playButton = (Button)findViewById(R.id.entry_play_btn);
		if(!playButton.isEnabled())
			((Button)findViewById(R.id.entry_play_btn)).setEnabled(true);
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
		else{
			((Button)findViewById(R.id.entry_play_btn)).setEnabled(false);
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
		EditText titleText = (EditText) findViewById(R.id.edit_title);
		myTextViews.put(EntryColumns.COLUMN_NAME_NATIVE_TEXT,nativeText);
		myTextViews.put(EntryColumns.COLUMN_NAME_FOREIGN_TEXT,foreignText);
		myTextViews.put(EntryColumns.COLUMN_NAME_TAG,tagText);
		myTextViews.put(EntryColumns.COLUMN_NAME_TITLE, titleText);
	}

	private ContentValues processIntent() {
		Intent intent = this.getIntent();
		return (ContentValues)intent.getParcelableExtra(ENTRY_INTENT_PARCELABLE);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.entry, menu);
	    menu.findItem(R.id.sync).setIcon(R.drawable.sync);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.save:
	        	handleSave();
	            return true;
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

	public void handleRecording(View view){
		myController.onRecord();
	}
	
	public void handlePlay(View view){
		myController.onPlay();
	}
	

	public void handleSave(){
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
    	myController.updateEntryField(new LanguageField(myLanguage));
    	myController.updateEntryField(new PhrasebookField(myPhrasebook));
    	myController.onSave();
    }

}


