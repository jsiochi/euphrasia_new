package com.jbj.euphrasia;

import java.util.Date;

public class DateField extends Field {
	
	private Date myDate; 
	
	public DateField(){
		myDate = new Date();
		//TODO format myDate to enable chronological sort? - i.e. yyyy/mm/dd time
		setData(myDate.toString());
	}
	
	public DateField(Date date){
		super(date.toString());
		myDate = date;
	}

	@Override
	public EntryDatabaseManager updateEntryField(
			EntryDatabaseManager entryManager) {
		entryManager.setDateField(this);
		return entryManager;
	}

}
