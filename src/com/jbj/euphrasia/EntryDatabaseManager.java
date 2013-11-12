package com.jbj.euphrasia;

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class EntryDatabaseManager extends ContentProvider {
	
	private Field myForeignText;
	private Field myNativeText;
	private Field myAudioField;
	private Field myDateField;
	private Field myTagField;
	private Field myTitleField;
	private Context myContext;
	
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
		
		myContext = context;
		myDatabaseHelper = new EntryDatabaseHelper(context);
		myForeignText = new NullField();
		myNativeText = new NullField();
		myAudioField = new NullField();
		myDateField = new NullField();
		myTagField = new NullField();
		myTitleField = new NullField();
	}

	
	public void saveEntry() {
		/*
		 * Write current entry to database
		 */
		
		//TODO add a new database entry
		
		int id = 1; 
		//TODO change this id!!
		
		ContentValues values = new ContentValues();
		values.put(EntryColumns.COLUMN_NAME_ENTRY_ID, id);
		values.put(EntryColumns.COLUMN_NAME_TITLE, myTitleField.toString());
		values.put(EntryColumns.COLUMN_NAME_NATIVE_TEXT, myNativeText.toString());
		values.put(EntryColumns.COLUMN_NAME_FOREIGN_TEXT, myForeignText.toString());
		values.put(EntryColumns.COLUMN_NAME_AUDIO, myAudioField.toString());
		values.put(EntryColumns.COLUMN_NAME_TAG, myTagField.toString());
		values.put(EntryColumns.COLUMN_NAME_DATE, myDateField.toString());
		Intent intent = new Intent(myContext, DatabaseWriteIntentService.class);
		intent.putExtra("values", values);
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
	
	/**
	 * Method should query database and return path to audio file. 
	 * @return String filePath to audio file from database
	 */
	public String getAudioPath(){
		//IMPLEMENT WHEN DATABASE WORKING
		return "FILEPATH";
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


	public void setTagField(TagField tagField) {
		myTagField = tagField;
	}


	public void setTitleField(TitleField titleField) {
		myTitleField = titleField;
	}


	public void setDateField(DateField dateField) {
		myDateField = dateField;
	}


	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
