package com.jbj.euphrasia;

import android.content.Context;
import android.net.Uri;

public class Controller {

	private EntryDatabaseManager myEntry;
	private RecordingManager myRecordingManager;
	private PlayManager myPlayManager;

	public Controller(Context context) {
		myEntry = new EntryDatabaseManager(context);
		myRecordingManager = new RecordingManager(context,this);
		myPlayManager = new PlayManager(context, this);
	}
	public String getAudioPath(){
		//return myEntry.getAudioPath();
		return myEntry.getAudioField().toString();
	}
	public void updateEntryField(Field field){
		field.updateEntryField(myEntry);
	}

	public void onRecord() {
		myRecordingManager.execute();
	}
	
	public void onSave() {
		//TODO way to disable save button or store an entry without a recording?
		//TODO need to save everything regardless of whether that EditText lost focus
		(new DateField()).updateEntryField(myEntry);
		Field audioField = myRecordingManager.save();
		audioField.updateEntryField(myEntry);
		Uri resourceUri = myEntry.saveEntry();
	}

	public void onPlay() {
		myPlayManager.execute();
	}

}
