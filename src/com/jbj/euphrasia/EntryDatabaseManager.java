package com.jbj.euphrasia;

import android.content.Context;

public class EntryDatabaseManager {
	
	private Field myForeignText;
	private Field myNativeText;
	private Field myAudioField;
	private Field myDateField;
	private Field myTagField;
	
	//TODO add the remaining fields
	
	private EntryDatabaseHelper myDatabaseHelper;
	
	/**
	 * @param context is the current context, found by calling getContext()
	 */
	public EntryDatabaseManager(Context context){
		/*
		 * Set up a new object
		 * (You gotta set 'em up kid
		 * set 'em up)
		 */
		
		myDatabaseHelper = new EntryDatabaseHelper(context);
		myForeignText = new NullField();
		myNativeText = new NullField();
		myAudioField = new NullField();
		myDateField = new NullField();
		myTagField = new NullField();
	}
	
	public void saveEntry() {
		/*
		 * Write current entry to database
		 */
		
		//TODO add a new database entry
	}

	public Field getNativeText() {
		return myNativeText;
	}
	
	public Field getForeignText() {
		return myForeignText;
	}
	
	public Field getAudioField() {
		return myAudioField;
	}

	public void setNativeText(Field data) {
		myNativeText = data;
	}

	public void setForeignText(Field field) {
		/*
		 * Accessed by ForeignTextField. 
		 * TODO - add logic to make sure change is valid
		 */
		myForeignText = field;
	}

	public void setAudioField(AudioField audioField) {
		myAudioField = audioField;
		
	}

}
