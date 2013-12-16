package com.jbj.euphrasia.listeners;

import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.activities.SearchActivity;

import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class EntryListListener implements OnItemClickListener{
	
	private SearchActivity mySourceActivity;
	
	public EntryListListener(SearchActivity activity){
		mySourceActivity = activity;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String[] projection = {EntryColumns.COLUMN_NAME_NATIVE_TEXT,EntryColumns.COLUMN_NAME_FOREIGN_TEXT,
				EntryColumns.COLUMN_NAME_LANGUAGE,EntryColumns.COLUMN_NAME_TITLE,
				EntryColumns.COLUMN_NAME_AUDIO,EntryColumns.COLUMN_NAME_DATE,EntryColumns.COLUMN_NAME_TAG, EntryColumns.COLUMN_NAME_PHRASEBOOK};
		Cursor cursor = mySourceActivity.getContentResolver().query(Uri.withAppendedPath(EntryProvider.CONTENT_URI, 
				String.valueOf(id)), projection, null,null,null);
		Log.i("ENTRYCLICK",""+String.valueOf(id));
		mySourceActivity.sendToEntry(cursor, id);
	}
	
}
