package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class LanguageField extends Field {
	
	public LanguageField(String data){
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(
			EntryDatabaseManager entryManager) {
		entryManager.setLanguageField(this);
		return entryManager;
	}

}
