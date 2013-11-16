package com.jbj.euphrasia;

public abstract class Field {

	protected String myData;
	
	protected Field() {};
	
	public Field(String data) {
		myData = data;
	}
	
	public String toString(){
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
