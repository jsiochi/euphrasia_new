package com.jbj.euphrasia;

public class EntryDatabaseManager {
	
	private Field myForeignText;
	private Field myNativeText;
	
	public EntryDatabaseManager(){
		/*
		 * Set up a new object
		 */
	}

	public Field getNativeText() {
		return myNativeText;
	}

	public void setNativeText(Field data) {
		myNativeText = data;
	}

	public void setForeignText(Field field) {
		/*
		 * Accessed by ForeignTextField. 
		 * TODO - add logic to make sure change is valid
		 */
		myForeignText = field;
	}

}
