package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class PhrasebookField extends Field {
	
	public PhrasebookField(String data){
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(
			EntryDatabaseManager entryManager) {
		entryManager.setPhrasebook(this);
		return entryManager;
	}

}
