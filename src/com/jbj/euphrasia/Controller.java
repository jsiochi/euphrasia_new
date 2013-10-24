package com.jbj.euphrasia;

import android.content.Context;

public class Controller {

	private EntryDatabaseManager myEntry;
	private RecordingManager myRecordingManager;

	public Controller(Context context) {
		myEntry = new EntryDatabaseManager(context);
		myRecordingManager = new RecordingManager();
	}
	
	public void updateEntryField(Field field){
		field.updateEntryField(myEntry);
	}

}
