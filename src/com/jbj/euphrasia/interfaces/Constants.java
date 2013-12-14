package com.jbj.euphrasia.interfaces;

import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryContract.EntryColumns;

public interface Constants {
	
	final String ACTION_GET_ENTRY_DATA = "Data for existing entry";
	final String ACTION_VIEW_ALL = "View every activity in the database";
	final String ACTION_BROWSE_PHRASEBOOKS = "View all phrasebooks in the database";
	final String ACTION_ONLY_LANGUAGES = "Only show language";
	final String ACTION_SHOW_LANGUAGES = "View all languages in the database";
	final String ENTRY_INTENT_PARCELABLE = "com.jbj.euphrasia.entryFromSearch";
	final String EXTRA_LANGUAGE_KEY = "language key";
	final String EXTRA_PHRASEBOOK_KEY = "phrasebook key";
	final String ACTION_EXISTING_LOGIN ="ëxisting user login";
	final String EXTRA_EXISTING_USER = "existing user";
	
	final String EXTRA_REMOTE_BUNDLE = "Remote query results";
	final String ACTION_REMOTE_QUERY = "display remote query results";
	final String[] DISPLAY_FROM_COLUMNS = {EntryColumns._ID, EntryColumns.COLUMN_NAME_TITLE, EntryColumns.COLUMN_NAME_TAG, 
			EntryColumns.COLUMN_NAME_NATIVE_TEXT};
	final String[] SELECT_ALL_PROJECTION = {EntryColumns.COLUMN_NAME_TITLE, 
			EntryColumns.COLUMN_NAME_TAG,EntryColumns.COLUMN_NAME_DATE,
			EntryColumns.COLUMN_NAME_FOREIGN_TEXT, EntryColumns.COLUMN_NAME_LANGUAGE, EntryColumns.COLUMN_NAME_NATIVE_TEXT,
			EntryColumns.COLUMN_NAME_PHRASEBOOK, EntryColumns.COLUMN_NAME_AUDIO};
}
