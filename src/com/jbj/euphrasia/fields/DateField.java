package com.jbj.euphrasia.fields;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.jbj.euphrasia.EntryDatabaseManager;

public class DateField extends Field {
	
	private Date myDate;
	private SimpleDateFormat myFormatter;
	
	public DateField(){
		myDate = new Date();
		//TODO format myDate to enable chronological sort? - i.e. yyyy/mm/dd time
		setData(myDate.toString());
	}
	
	public DateField(String date){
		super(date);
		try {
			myDate = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
