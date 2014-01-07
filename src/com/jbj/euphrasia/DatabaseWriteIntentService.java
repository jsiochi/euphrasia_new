package com.jbj.euphrasia;

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * OBSOLETE CLASS!
 * @author jeremiahsiochi
 */

public class DatabaseWriteIntentService extends IntentService {
	
	public DatabaseWriteIntentService() {
		super("DatabaseWriteIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		EntryDatabaseHelper myDatabaseHelper = new EntryDatabaseHelper(getBaseContext());
		SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
		ContentValues values = intent.getParcelableExtra("values");
		long newRowId = database.insert(EntryColumns.TABLE_NAME, EntryColumns.COLUMN_NAME_NULLABLE, values);
	}
	
	
}
