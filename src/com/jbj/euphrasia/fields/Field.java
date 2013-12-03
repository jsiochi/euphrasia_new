package com.jbj.euphrasia.fields;

import android.util.Log;

import com.jbj.euphrasia.EntryDatabaseManager;

public abstract class Field {

	protected String myData;
	
	protected Field() {};
	
	public Field(String data) {
		myData = data;
	}
	
	public String toString(){
		Log.i("PRINTING_DATA", myData);
		return myData;
	}
	
	public abstract EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager);
	
	public boolean isNull() {
		return false;
	}

	public void setData(String data) {
		myData = data;
	}

}
