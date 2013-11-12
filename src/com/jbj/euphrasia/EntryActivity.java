package com.jbj.euphrasia;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class EntryActivity extends Activity {
	
	private FieldFactory myFieldFactory;
	private Controller myController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		
		myFieldFactory = new FieldFactory();
		myController = new Controller(this);
		
		EditText nativeText = (EditText) findViewById(R.id.native_text);
		EditText foreignText = (EditText) findViewById(R.id.foreign_text);
		nativeText.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
		    public void onFocusChange(View view, boolean isFocused) {
		        if (!isFocused) {
		        	Log.d("TAG","forein_focus_change");
		            updateField(view);
		        }
		    }
		});
	    foreignText.setOnFocusChangeListener(new OnFocusChangeListener(){
	    	@Override
		    public void onFocusChange(View view, boolean isFocused) {
		        if (!isFocused) {
		        	Log.d("TAG","native_focus_change");
		            updateField(view);
		        }
		    }
	    });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entry, menu);
		return true;
	}
	
/** get appropriate EditText object
 * Call this method whenever an EditText component losesFocus
 */
	public void updateField(View view){
		if(!view.hasFocus()){
			EditText editText = (EditText) view;
			Field field = myFieldFactory.createField(view.getId(), editText.getText().toString());
			myController.updateEntryField(field);
			Log.i("new field",field.toString() + field.getClass().getName());
		}
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
		myController.onSave(new AudioField());
	}
	
	public void handleSave(View view){
		myController.onSave(new AudioField());
	}
}


