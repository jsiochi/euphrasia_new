package com.jbj.euphrasia;

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseWriteIntentService extends IntentService {
	Context myContext;
	
	public DatabaseWriteIntentService(Context context) {
		super("DatabaseWriteIntentService");
		myContext = context;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		EntryDatabaseHelper myDatabaseHelper = new EntryDatabaseHelper(myContext);
		SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
		ContentValues values = intent.getParcelableExtra("values");
		long newRowId;
		newRowId = database.insert(EntryColumns.TABLE_NAME, EntryColumns.COLUMN_NAME_NULLABLE, values);
	}
	
	
}
