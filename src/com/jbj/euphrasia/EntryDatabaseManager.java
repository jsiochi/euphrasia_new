package com.jbj.euphrasia;

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class EntryDatabaseManager {
	
	private Field myForeignText;
	private Field myNativeText;
	private Field myLanguageField;
	private Field myAudioField;
	private Field myDateField;
	private Field myTagField;
	private Field myTitleField;
	private Context myContext;
	
	//TODO add the remaining fields
	
	public EntryDatabaseManager(Context context){
		myContext = context;
		myForeignText = new NullField();
		myNativeText = new NullField();
		myLanguageField = new NullField();
		myAudioField = new NullField();
		myDateField = new NullField();
		myTagField = new NullField();
		myTitleField = new NullField();
	}

	/**
	 * Write current entry to database
	 * @return URI of the newly inserted entry
	 */
	public Uri saveEntry() {
		
		
		if(myTitleField.isNull()) {
			myTitleField = new TitleField(myNativeText.toString());
		}
		
		ContentValues values = new ContentValues();
		values.put(EntryColumns.COLUMN_NAME_TITLE, myTitleField.toString());
		values.put(EntryColumns.COLUMN_NAME_NATIVE_TEXT, myNativeText.toString());
		values.put(EntryColumns.COLUMN_NAME_FOREIGN_TEXT, myForeignText.toString());
		values.put(EntryColumns.COLUMN_NAME_LANGUAGE, myLanguageField.toString());
		values.put(EntryColumns.COLUMN_NAME_AUDIO, myAudioField.toString());
		values.put(EntryColumns.COLUMN_NAME_TAG, myTagField.toString());
		values.put(EntryColumns.COLUMN_NAME_DATE, myDateField.toString());
		
		Uri newUri = myContext.getContentResolver().insert(EntryProvider.CONTENT_URI, values);
		
		//TEST CODE FOR DATABASE READ (confirmed working)
		String[] projection = {EntryColumns._ID, EntryColumns.COLUMN_NAME_TITLE, EntryColumns.COLUMN_NAME_NATIVE_TEXT, EntryColumns.COLUMN_NAME_FOREIGN_TEXT, 
				EntryColumns.COLUMN_NAME_TAG, EntryColumns.COLUMN_NAME_AUDIO, EntryColumns.COLUMN_NAME_LANGUAGE, EntryColumns.COLUMN_NAME_DATE};
		Cursor cursor = myContext.getContentResolver().query(EntryProvider.CONTENT_URI, projection, null, null, null);
		
		while(cursor.moveToNext()) {
			Log.i("Database_READ", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + 
					" " + cursor.getString(4) + " " + cursor.getString(5) + " " + cursor.getString(6) + " " + cursor.getString(7));
		}
		
		cursor.close();
		
		return newUri;
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
	
	public Field getTagField() {
		return myTagField;
	}
	
	public Field getDateField() {
		return myDateField;
	}
	
	public Field getTitleField() {
		return myTitleField;
	}
	
	public Field getLanguageField() {
		return myLanguageField;
	}
	
	/**
	 * Method should query database and return path to audio file. 
	 * @return String filePath to audio file from database
	 */
	public String getAudioPath(){
		return myAudioField.toString();
	}

	public void setNativeText(Field data) {
		Log.i("DB_MANAGER","Updated native");
		myNativeText = data;
	}

	public void setForeignText(Field field) {
		/*
		 * Accessed by ForeignTextField. 
		 * TODO - add logic to make sure change is valid
		 */
		Log.i("DB_MANAGER","Updated foreign");
		myForeignText = field;
	}
	
	public void setLanguageField(Field field) {
		Log.i("DB_MANAGER","Updated language");
		myLanguageField = field;
	}

	public void setAudioField(AudioField audioField) {
		Log.i("DB_MANAGER","Updated audio");
		myAudioField = audioField;
	}


	public void setTagField(TagField tagField) {
		Log.i("DB_MANAGER","Updated tag");
		myTagField = tagField;
	}


	public void setTitleField(TitleField titleField) {
		Log.i("DB_MANAGER","Updated title");
		myTitleField = titleField;
	}


	public void setDateField(DateField dateField) {
		myDateField = dateField;
	}
}
