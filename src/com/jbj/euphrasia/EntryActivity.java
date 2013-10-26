package com.jbj.euphrasia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class EntryActivity extends Activity {
	
	private FieldFactory myFieldFactory;
	private Controller myController;
	private boolean myIsRecording;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entry);
		myFieldFactory = new FieldFactory();
		myController = new Controller(this);
		myIsRecording = false;
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
		Field field = myFieldFactory.createField("componentID", "data");
		myController.updateEntryField(field);
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
	
	public void handlePause(View view){
		myController.onPause();
	}
	
	public void handleSave(View view){
		myController.onSave(new AudioField());
	}
	

}
