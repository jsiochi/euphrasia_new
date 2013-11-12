package com.jbj.euphrasia;

/**
 * @author Jeremiah
 * Defines the schema for the database
 */

import android.provider.BaseColumns;

public final class EntryContract {
	public EntryContract() {}
	
	public static abstract class EntryColumns implements BaseColumns{
		public static final String TABLE_NAME = "entries";
		public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
		public static final String COLUMN_NAME_NATIVE_TEXT = "native_text";
		public static final String COLUMN_NAME_FOREIGN_TEXT = "foreign_text";
		public static final String COLUMN_NAME_TITLE = "title"; //defaults to first four words of native_text
		public static final String COLUMN_NAME_AUDIO = "audio";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_TAG = "tag";
		public static final String COLUMN_NAME_NULLABLE = "nullable";
	}
}
