package com.jbj.euphrasia;

/**
 * @author Jeremiah
 * This class is the content provider for the entries table.
 * Use CONTENT_URI; it is the URI for the entries table.
 */

import java.util.ArrayList;

import com.jbj.euphrasia.EntryContract.EntryColumns;
import com.jbj.euphrasia.interfaces.Constants;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class EntryProvider extends ContentProvider {
	
	private EntryDatabaseHelper myDatabaseHelper;
	private SQLiteDatabase myDatabase;
	
	private static final String MY_AUTHORITY = "com.jbj.euphrasia.provider"; 
	private static final String MY_CONTENT_URI = "content://" + MY_AUTHORITY;
	private static final UriMatcher myUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	public static final String VIEW_REMOTE = "remote_entries";
	//TODO add all needed URI patterns here
	static { 
		myUriMatcher.addURI(MY_AUTHORITY, EntryColumns.TABLE_NAME, 1);
		myUriMatcher.addURI(MY_AUTHORITY, EntryColumns.TABLE_NAME + "/#", 2);
		myUriMatcher.addURI(MY_AUTHORITY, EntryColumns.COLUMN_NAME_PHRASEBOOK, 3);
		myUriMatcher.addURI(MY_AUTHORITY, EntryColumns.COLUMN_NAME_LANGUAGE, 4);
		myUriMatcher.addURI(MY_AUTHORITY, EntryColumns.TABLE_NAME + "/" + VIEW_REMOTE, 5);
		myUriMatcher.addURI(MY_AUTHORITY, EntryColumns.TABLE_NAME + "/" + VIEW_REMOTE + "/#", 6);
		}
	
	public static final Uri CONTENT_URI = Uri.parse(MY_CONTENT_URI + "/" + EntryColumns.TABLE_NAME);
	public static final Uri CONTENT_PHRASEBOOKS_URI = Uri.parse(MY_CONTENT_URI + "/" + EntryColumns.COLUMN_NAME_PHRASEBOOK);
	public static final Uri CONTENT_LANGUAGES_URI = Uri.parse(MY_CONTENT_URI + "/" + EntryColumns.COLUMN_NAME_LANGUAGE);
	public static final Uri CONTENT_REMOTE_URI = Uri.parse(MY_CONTENT_URI + "/" + EntryColumns.TABLE_NAME + "/" + VIEW_REMOTE);
	private static Bundle remoteBundle;
	
	//public static final String GET_PHRASEBOOKS = "get_phrasebooks";

	@Override
	public boolean onCreate() {
		myDatabaseHelper = new EntryDatabaseHelper(getContext());
		remoteBundle = new Bundle();
		return true;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String newSelection = selection;
		Cursor cursor;
		
		switch (myUriMatcher.match(uri)) {
		//URI is entire table
		case 1:
			if (TextUtils.isEmpty(sortOrder)){ 
				sortOrder = "_id ASC";
			}
			break;
		
		//URI is single row
		case 2:
			if (TextUtils.isEmpty(selection)) {
				newSelection = " _id = " + uri.getLastPathSegment();
			}
			else
			{
				newSelection = " AND _id = " + uri.getLastPathSegment();
			}
			break;
		//URI is all the phrasebooks (3) or languages (4)
		case 3:
		case 4:
			myDatabase = myDatabaseHelper.getWritableDatabase();
			String[] theProjection = {uri.getLastPathSegment(), EntryColumns._ID};
			cursor = myDatabase.query(true, EntryColumns.TABLE_NAME, theProjection, uri.getLastPathSegment() + " IS NOT NULL", null, uri.getLastPathSegment(), null, null, null, null);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		case 5:
			MatrixCursor matrixCursor = new MatrixCursor(Constants.SELECT_ALL_PROJECTION_WITH_ID);
			for(int i = 0;i<remoteBundle.size();i++){
				Bundle entryBundle = remoteBundle.getBundle(String.valueOf(i));
				String[] values = new String[]{String.valueOf(i), entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[1]),
						entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[2]),
						entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[3]),
						entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[4]),
						entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[5]),
						entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[6]),
						entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[7]),
						entryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[8])};
				matrixCursor.addRow(values);
			}
			return matrixCursor;
		case 6:
			MatrixCursor matrixCursor2 = new MatrixCursor(Constants.SELECT_ALL_PROJECTION_WITH_ID);
			Bundle theEntryBundle = remoteBundle.getBundle(uri.getLastPathSegment());
			String[] values = new String[]{uri.getLastPathSegment(), theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[1]),
					theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[2]),
					theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[3]),
					theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[4]),
					theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[5]),
					theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[6]),
					theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[7]),
					theEntryBundle.getString(Constants.SELECT_ALL_PROJECTION_WITH_ID[8])};
			matrixCursor2.addRow(values);
			return matrixCursor2;
		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		myDatabase = myDatabaseHelper.getWritableDatabase();
		cursor = myDatabase.query(EntryColumns.TABLE_NAME, projection, newSelection, selectionArgs, null, null, sortOrder);
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
		
		Uri.Builder ub = new Uri.Builder();
		
		ub.authority(MY_AUTHORITY);
		ub.appendPath(EntryColumns.TABLE_NAME);
		
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
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public Bundle call(String method, String arg, Bundle extras) {
		if(method == VIEW_REMOTE) {
			remoteBundle = extras;
		} else {
			throw new IllegalArgumentException("Invalid Method Name");
		}
		
		return null;
	}
}
