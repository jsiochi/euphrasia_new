package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class TitleField extends Field {
	
	public TitleField(String data){
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(
			EntryDatabaseManager entryManager) {
		entryManager.setTitleField(this);
		return entryManager;
	}

}
