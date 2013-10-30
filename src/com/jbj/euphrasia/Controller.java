package com.jbj.euphrasia;

import android.content.Context;

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
	
	public void onSave(Field audioField) {
		audioField.setData(myRecordingManager.save());
		audioField.updateEntryField(myEntry);
	}

	public void onPlay() {
		myPlayManager.execute();
	}

}
