package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class ForeignTextField extends Field {

	public ForeignTextField(String data) {
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		entryManager.setForeignText(this);
		return entryManager;
	}

}
