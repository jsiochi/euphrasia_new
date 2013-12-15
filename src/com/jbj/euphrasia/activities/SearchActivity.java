package com.jbj.euphrasia.activities;


import com.jbj.euphrasia.EntryContract;
import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.R;
import com.jbj.euphrasia.SyncManager;
import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.dialog_fragments.DeleteAlertDialog;
import com.jbj.euphrasia.dialog_fragments.NewUserDialog;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.listeners.EntryListListener;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class SearchActivity extends ListActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>, Constants {

	private CursorAdapter myCursorAdapter;
	private String myCursorFilter;
	private Cursor myCursor;
	private ListView myListView;
	private SearchActivity myActivity;
	private CheckBox myWarningCheckbox; 
	private boolean warnOnDelete = true;
	
	private static final String SELECTION_QUERY = "SelectionQuery";
	private static final String SELECTION_ARGS = "SelectionArgs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myActivity = this;
		setContentView(R.layout.activity_search);
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
		
		Intent intent = getIntent();
		String query = intent.getStringExtra(SearchManager.QUERY);
		if (ACTION_VIEW_ALL.equals(intent.getAction())) {
			String selection = EntryColumns.COLUMN_NAME_TITLE + " LIKE '%" + query + "%' OR " + EntryColumns.COLUMN_NAME_TAG + " LIKE '%" + query + "%' OR " 
					+ EntryColumns.COLUMN_NAME_NATIVE_TEXT + " LIKE '%" + query + "%'";
			this.doEntrySearch(selection);
			myListView.setOnItemClickListener(new EntryListListener(this));
		}
		if(ACTION_BROWSE_PHRASEBOOKS.equals(intent.getAction())){
			//show only entries from this phrasebook. 
			String phrasebookExtra = intent.getStringExtra(EXTRA_PHRASEBOOK_KEY);
			String selection = EntryColumns.COLUMN_NAME_PHRASEBOOK + " LIKE '%" + phrasebookExtra + "%'";
			this.doEntrySearch(selection);
			myListView.setOnItemClickListener(new EntryListListener(this));
		}
		if(ACTION_ONLY_LANGUAGES.equals(intent.getAction())){
			//show only entries from this language.
			String languageExtra = intent.getStringExtra(EXTRA_LANGUAGE_KEY);
			String selection = EntryColumns.COLUMN_NAME_LANGUAGE + " LIKE '%" + languageExtra + "%'";
			this.doEntrySearch(selection);
			myListView.setOnItemClickListener(new EntryListListener(this));
		}
		if(ACTION_REMOTE_QUERY.equals(intent.getAction())){
//			Bundle remoteResults = intent.getBundleExtra(EXTRA_REMOTE_BUNDLE);
//			MatrixCursor matrixCursor = new MatrixCursor(DISPLAY_FROM_COLUMNS);
//			for(int i = 0;i<remoteResults.size();i++){
//				Bundle entryBundle = remoteResults.getBundle(String.valueOf(i));
//				String[] values = new String[]{String.valueOf(i), entryBundle.getString("title"),entryBundle.getString("tag"),
//						entryBundle.getString("native_text")};
//				matrixCursor.addRow(values);
//			}
//			Log.i("Matrix Cursor rows",""+matrixCursor.getCount());
//			myCursor = matrixCursor;
			myCursorFilter = EntryProvider.VIEW_REMOTE;
			this.doEntrySearch("");
		}
//		View checkBoxView = View.inflate(this, R.layout.checkbox, null);
//		CheckBox noWarningOption = (CheckBox)checkBoxView.findViewById(R.id.checkbox);
//		
//		Log.i("Check",""+(noWarningOption==null));
//		myWarningCheckbox = noWarningOption;
//		noWarningOption.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView,
//					boolean isChecked) {
//				if(isChecked)
//					warnOnDelete = false;
//			}
//			
//		});
		myListView = (ListView) findViewById(android.R.id.list);
		myListView.setLongClickable(true);
		myListView.setOnItemLongClickListener(new OnItemLongClickListener(){
		

			/**
			 * Delete item from database on long click. 
			 */
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, final long id) {
				if(warnOnDelete){
					// 1. Instantiate an AlertDialog.Builder with its constructor
					AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);

					// 2. Chain together various setter methods to set the dialog characteristics
					builder.setMessage(R.string.delete_alert_message);
					//builder.setView(myWarningCheckbox);
					builder.setPositiveButton(R.string.delete_confirm_button, new OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							warnOnDelete = false;
							deleteEntry(id);
						}
						
					});
					builder.setNegativeButton(R.string.delete_cancel_button, new OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
						
					});

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
					return true;
				}
				else{
					return deleteEntry(id);
				}
			}
			
		});
	}
	
	public boolean deleteEntry(long id){
		myActivity.getContentResolver().delete(Uri.withAppendedPath(EntryProvider.CONTENT_URI, 
				String.valueOf(id)), "", null);
		Log.i("ENTRYLONGCLICK","long press is " + String.valueOf(id));
		myActivity.getLoaderManager().restartLoader(0, new Bundle(), myActivity);
		return true;
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
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.search, menu);
	    menu.findItem(R.id.sync).setIcon(R.drawable.sync);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.sync:
	        	SyncManager.setActivity(this);
	        	SyncManager.sync();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public android.content.Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		
		Uri baseUri;
        if (myCursorFilter != null) {
            baseUri = Uri.withAppendedPath(EntryProvider.CONTENT_URI,
                    Uri.encode(myCursorFilter));
            Log.i("JazzingJ",baseUri.equals(EntryProvider.CONTENT_REMOTE_URI) + "");
            
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
	
//	public void displayEverything(){
//		String[] projection = {EntryContract.EntryColumns.COLUMN_NAME_TITLE, EntryContract.EntryColumns.COLUMN_NAME_TAG};
//		myCursor = getContentResolver().query(EntryProvider.CONTENT_URI, projection, null,null,null);
//	}
	
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
