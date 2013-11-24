package com.jbj.euphrasia;

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.app.ActionBar.LayoutParams;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class SearchActivity extends ListActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

	private CursorAdapter myCursorAdapter;
	private String myCursorFilter;
	private Cursor myCursor;
	private Controller myController;
	
	private static final String SELECTION_QUERY = "SelectionQuery";
	private static final String SELECTION_ARGS = "SelectionArgs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
//		Intent intent = getIntent();
//		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//			String query = intent.getStringExtra(SearchManager.QUERY);
//			this.doSearch(query);
//		}
		
		ListView listView = (ListView) findViewById(android.R.id.list);
		//query database for collection of all tags
		//display these tags + frequency onCreate
//		ProgressBar progressBar = new ProgressBar(this);
//        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//        progressBar.setIndeterminate(true);
//        getListView().setEmptyView(progressBar);
//        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//        root.addView(progressBar);
		Bundle emptyBundle = new Bundle();
        getLoaderManager().initLoader(0, emptyBundle, this);
        //take data from columns and put in specific views
        String[] fromColumns = {EntryContract.EntryColumns.COLUMN_NAME_TITLE, EntryContract.EntryColumns.COLUMN_NAME_TAG};
        int[] toViews = {R.id.item_title, R.id.item_tags};
        myCursorAdapter = new SimpleCursorAdapter(this, 
                R.layout.search_list_item, myCursor,
                fromColumns, toViews, 0);
        setListAdapter(myCursorAdapter);
        //this.doSearch("ea");
        this.displayEverything();
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
        String[] projection = {EntryColumns._ID, EntryColumns.COLUMN_NAME_TITLE, EntryColumns.COLUMN_NAME_TAG};
        String selection = arg1.getString(SELECTION_QUERY);
        String[] selectionArgs = arg1.getStringArray(SELECTION_ARGS);
        return new CursorLoader(this, baseUri, projection, selection, selectionArgs,null);
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
	
	public void doSearch(String query) {
		String[] projection = {EntryContract.EntryColumns.COLUMN_NAME_TITLE, EntryContract.EntryColumns.COLUMN_NAME_TAG};
		String selection = EntryColumns.COLUMN_NAME_TAG + " LIKE '%?%' OR " + EntryColumns.COLUMN_NAME_NATIVE_TEXT + " LIKE '%?%'";
		String[] selectionArgs = {query, query};
		Bundle args = new Bundle();
		Log.i("Here we go", "I AM a freaking PIZZA aaa");
		args.putStringArray(SELECTION_ARGS, selectionArgs);
		args.putString(SELECTION_QUERY, selection);
		//this.getLoaderManager().restartLoader(0, args, this);
		Log.i("Here we go", "I AM a freaking PIZZA bbb");
	}

}
