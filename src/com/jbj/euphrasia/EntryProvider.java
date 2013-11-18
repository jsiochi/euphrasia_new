package com.jbj.euphrasia;

/**
 * @author Jeremiah
 * This class is the content provider for the entries table.
 * Use CONTENT_URI; it is the URI for the entries table.
 */

import com.jbj.euphrasia.EntryContract.EntryColumns;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class EntryProvider extends ContentProvider {
	
	//TODO add permission in manifest file!!
	private EntryDatabaseHelper myDatabaseHelper;
	private SQLiteDatabase myDatabase;
	
	private static final String MY_AUTHORITY = "com.jbj.euphrasia.provider"; 
	private static final String MY_CONTENT_URI = "content://" + MY_AUTHORITY;
	private static final UriMatcher myUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	//TODO add all needed URI patterns here
	static { 
		myUriMatcher.addURI(MY_CONTENT_URI, EntryColumns.TABLE_NAME, 1);
		myUriMatcher.addURI(MY_CONTENT_URI, EntryColumns.TABLE_NAME + "/#", 2);
		}
	
	public static final Uri CONTENT_URI = Uri.parse(MY_CONTENT_URI + "/" + EntryColumns.TABLE_NAME);

	@Override
	public boolean onCreate() {
		myDatabaseHelper = new EntryDatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String newSelection = selection;
		
		switch (myUriMatcher.match(uri)) {
		//URI is entire table
		case 1:
			if (TextUtils.isEmpty(sortOrder)) sortOrder = "_id ASC";
			break;
		
		//URI is single row
		case 2:
			String extra = " AND ";
			if (TextUtils.isEmpty(selection)) extra = "";
			newSelection = selection + extra + "_id = " + uri.getLastPathSegment();
			break;
		
		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		myDatabase = myDatabaseHelper.getWritableDatabase();
		Cursor cursor = myDatabase.query(EntryColumns.TABLE_NAME, projection, newSelection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		String type;
		
		switch (myUriMatcher.match(uri)) {
		//URI is entire table
		case 1:
			type = "vnd.android.cursor.dir/vnd.com.jbj.provider." + EntryColumns.TABLE_NAME;
			break;
		
		//URI is single row
		case 2:
			type = "vnd.android.cursor.item/vnd.com.jbj.provider." + EntryColumns.TABLE_NAME;
			break;
		
		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		return type;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		myDatabase = myDatabaseHelper.getWritableDatabase();
		long newRowID;
		
		Log.i("URIstuff", uri.toString());
		
		Uri.Builder ub = new Uri.Builder();
		
		ub.authority(MY_AUTHORITY);
		ub.appendPath(EntryColumns.TABLE_NAME);
		
		Log.i("URIstuffMatcher", ub.toString());
		
		if(myUriMatcher.match(uri) == 1) {
			newRowID = myDatabase.insert(EntryColumns.TABLE_NAME, EntryColumns.COLUMN_NAME_NULLABLE, values);
			if(newRowID == -1) throw new IllegalArgumentException("Invalid format of values");
		}
		else
			throw new IllegalArgumentException("Invalid URI");
		
		//getContext().getContentResolver().notifyChange(uri, null);
		return ContentUris.withAppendedId(CONTENT_URI, newRowID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String newSelection = selection;
		
		switch (myUriMatcher.match(uri)) {
		//URI is entire table
		case 1:
			break;
		
		//URI is single row
		case 2:
			String extra = " AND ";
			if (TextUtils.isEmpty(selection)) extra = "";
			newSelection = selection + extra + "_id = " + uri.getLastPathSegment();
			break;
		
		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		myDatabase = myDatabaseHelper.getWritableDatabase();
		int numRows = myDatabase.delete(EntryColumns.TABLE_NAME, newSelection, selectionArgs);
		//getContext().getContentResolver().notifyChange(uri, null);
		
		return numRows;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String newSelection = selection;
		
		switch (myUriMatcher.match(uri)) {
		//URI is entire table
		case 1:
			break;
		
		//URI is single row
		case 2:
			String extra = " AND ";
			if (TextUtils.isEmpty(selection)) extra = "";
			newSelection = selection + extra + "_id = " + uri.getLastPathSegment();
			break;
		
		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		myDatabase = myDatabaseHelper.getReadableDatabase();
		int numRows = myDatabase.update(EntryColumns.TABLE_NAME, values, newSelection, selectionArgs);
		
		return numRows;
	}
}
