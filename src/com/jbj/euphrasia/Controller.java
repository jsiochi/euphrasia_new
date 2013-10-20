package com.jbj.euphrasia;

public class Controller {

	private EntryDatabaseManager myEntry;
	private RecordingManager myRecordingManager;

	public Controller() {
		myEntry = new EntryDatabaseManager();
		myRecordingManager = new RecordingManager();
	}
	
	public void updateEntryField(Field field){
		field.updateEntryField(myEntry);
	}

}
