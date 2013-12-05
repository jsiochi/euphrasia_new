package com.jbj.euphrasia;

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class EntryDatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "Entry.db";
	public static final int DATABASE_VERSION = 4;
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ", ";
	private static final String NEW_COLUMNS = EntryColumns.COLUMN_NAME_PHRASEBOOK + TEXT_TYPE + ";"; //change every time new column is added
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + EntryColumns.TABLE_NAME + "(" +
			EntryColumns._ID + " INTEGER PRIMARY KEY, " +
			EntryColumns.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_NATIVE_TEXT + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_FOREIGN_TEXT + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_LANGUAGE + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_AUDIO + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_TAG + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_PHRASEBOOK + TEXT_TYPE + COMMA_SEP + 
			EntryColumns.COLUMN_NAME_NULLABLE + TEXT_TYPE +
			");";
	
	private static final String SQL_UPGRADE_TABLE = "ALTER TABLE " + EntryColumns.TABLE_NAME + " ADD COLUMN " + NEW_COLUMNS;
	
	private static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + EntryColumns.TABLE_NAME;
	
	public EntryDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// This is only a placeholder that dumps the entire database
		// TODO: implement this with correct functionality
		database.execSQL(SQL_DELETE_ENTRIES);
		onCreate(database);
		//database.execSQL(SQL_UPGRADE_TABLE);
	}
	
	public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		onUpgrade(database, oldVersion, newVersion);
	}
}
