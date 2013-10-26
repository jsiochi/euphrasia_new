package com.jbj.euphrasia;

import android.content.Context;

public class Controller {

	private EntryDatabaseManager myEntry;
	private RecordingManager myRecordingManager;
	private PlayManager myPlayManager;

	public Controller(Context context) {
		myEntry = new EntryDatabaseManager(context);
		myRecordingManager = new RecordingManager();
		myPlayManager = new PlayManager();
	}
	
	public void updateEntryField(Field field){
		field.updateEntryField(myEntry);
	}

	public void onRecord() {
		myRecordingManager.execute();
	}

	public void onPlay() {
		myPlayManager.execute();
	}

	public void onPause() {
		myPlayManager.pause();
	}

}
