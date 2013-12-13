package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.CustomCursorAdapter;
import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.R.id;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;

import android.app.ActionBar.LayoutParams;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SearchActivity extends ListActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>, Constants {

	private CursorAdapter myCursorAdapter;
	private String myCursorFilter;
	private Cursor myCursor;
	private ListView myListView;
	private ListActivity myActivity;
	
	private static final String SELECTION_QUERY = "SelectionQuery";
	private static final String SELECTION_ARGS = "SelectionArgs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myActivity = this;
		setContentView(R.layout.activity_search);
		
		Intent intent = getIntent();
		String query = intent.getStringExtra(SearchManager.QUERY);
		if (ACTION_VIEW_ALL.equals(intent.getAction())) {
			String selection = EntryColumns.COLUMN_NAME_TITLE + " LIKE '%" + query + "%' OR " + EntryColumns.COLUMN_NAME_TAG + " LIKE '%" + query + "%' OR " 
					+ EntryColumns.COLUMN_NAME_NATIVE_TEXT + " LIKE '%" + query + "%'";
			this.doEntrySearch(selection);
		}
		if(ACTION_BROWSE_PHRASEBOOKS.equals(intent.getAction())){
			//show only entries from this phrasebook. 
			String phrasebookExtra = intent.getStringExtra(EXTRA_PHRASEBOOK_KEY);
			Log.i("DEBUG_PHRASEBOOKS", phrasebookExtra);
			String selection = EntryColumns.COLUMN_NAME_PHRASEBOOK + " LIKE '%" + phrasebookExtra + "%'";
			this.doEntrySearch(selection);
		}
		if(ACTION_ONLY_LANGUAGES.equals(intent.getAction())){
			//show only entries from this language.
			String languageExtra = intent.getStringExtra(EXTRA_LANGUAGE_KEY);
			String selection = EntryColumns.COLUMN_NAME_LANGUAGE + " LIKE '%" + languageExtra + "%'";
			this.doEntrySearch(selection);
		}
		
		myListView = (ListView) findViewById(android.R.id.list);
		myListView.setLongClickable(true);
		myListView.setOnItemLongClickListener(new OnItemLongClickListener(){
			
			/**
			 * Delete item from database on long click. 
			 */
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				myActivity.getContentResolver().delete(Uri.withAppendedPath(EntryProvider.CONTENT_URI, 
						String.valueOf(id)), null, null);
				return false;
			}
			
		});
		myListView.setOnItemClickListener(new EntryListListener());

		Bundle emptyBundle = new Bundle();
        getLoaderManager().initLoader(0, emptyBundle, this);
        //take data from columns and put in specific views
        String[] fromColumns = {EntryContract.EntryColumns.COLUMN_NAME_TITLE, EntryContract.EntryColumns.COLUMN_NAME_TAG, EntryContract.EntryColumns.COLUMN_NAME_NATIVE_TEXT};
        int[] toViews = {R.id.item_title, R.id.item_tags, R.id.item_native_text};
        myCursorAdapter = new SimpleCursorAdapter(this, 
                R.layout.search_list_item, myCursor,
                fromColumns, toViews, 0){
           @Override
            public View getView(int position, View convertView, ViewGroup parent) {
              View view = super.getView(position, convertView, parent);
              if ( position % 2 == 0)
                  view.setBackgroundResource(R.drawable.search_even_row);
              else
                  view.setBackgroundResource(R.drawable.search_odd_row);
              return view;}
        };
        setListAdapter(myCursorAdapter);
	}


	public class EntryListListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String[] projection = {EntryColumns.COLUMN_NAME_NATIVE_TEXT,EntryColumns.COLUMN_NAME_FOREIGN_TEXT,
					EntryColumns.COLUMN_NAME_LANGUAGE,EntryColumns.COLUMN_NAME_TITLE,
					EntryColumns.COLUMN_NAME_AUDIO,EntryColumns.COLUMN_NAME_DATE,EntryColumns.COLUMN_NAME_TAG};
			Cursor cursor = myActivity.getContentResolver().query(Uri.withAppendedPath(EntryProvider.CONTENT_URI, 
					String.valueOf(id)), projection, null,null,null);
			sendToEntry(cursor, id);
		}
		
	}
	
	public void sendToEntry(Cursor cursor, long id) {
		int columnCount = cursor.getColumnCount();
		Log.i("coumn count", String.valueOf(columnCount));
		int i = 0;
		ContentValues values = new ContentValues();
		cursor.moveToFirst();
		while(i<columnCount){
			Log.i("coumn pos", String.valueOf(i));
			String columnValue = cursor.getString(i);
			String columnName = cursor.getColumnName(i);
			values.put(columnName, columnValue);
			i++;
		}
		values.put("URI_id", id);
		Intent toEntryIntent = new Intent(this,EntryActivity.class);
		toEntryIntent.putExtra(ENTRY_INTENT_PARCELABLE, values);
		toEntryIntent.setAction(ACTION_GET_ENTRY_DATA);
		startActivity(toEntryIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search,  menu);
		return true;
	}

	@Override
	public android.content.Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		
		Uri baseUri;
        if (myCursorFilter != null) {
            baseUri = Uri.withAppendedPath(EntryProvider.CONTENT_URI,
                    Uri.encode(myCursorFilter));
        } else {
            baseUri = EntryProvider.CONTENT_URI;
        }
        String[] projection = {EntryColumns._ID, EntryColumns.COLUMN_NAME_TITLE, EntryColumns.COLUMN_NAME_TAG, EntryColumns.COLUMN_NAME_NATIVE_TEXT};
        
//        if(arg1.containsKey(SELECTION_QUERY)) {
//        	String selection = arg1.getString(SELECTION_QUERY);
//        	return new CursorLoader(this, baseUri, projection, selection, null,null);
//        }
        return new CursorLoader(this, baseUri, projection, arg1.getString(SELECTION_QUERY), null,null);
	}


	@Override
	public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
		myCursorAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(android.content.Loader<Cursor> arg0) {
		myCursorAdapter.swapCursor(null);
	}
	
	public void displayEverything(){
		String[] projection = {EntryContract.EntryColumns.COLUMN_NAME_TITLE, EntryContract.EntryColumns.COLUMN_NAME_TAG};
		myCursor = getContentResolver().query(EntryProvider.CONTENT_URI, projection, null,null,null);
	}
	
	public void doEntrySearch(String selection) {
		String[] projection = {EntryContract.EntryColumns.COLUMN_NAME_TITLE, EntryContract.EntryColumns.COLUMN_NAME_TAG, EntryContract.EntryColumns.COLUMN_NAME_NATIVE_TEXT};
		Bundle args = new Bundle();
		args.putString(SELECTION_QUERY, selection);
		this.getLoaderManager().restartLoader(0, args, this);
	}
	
	public void doPhrasebookSearch(String query){
		// do something cool
	}
}
