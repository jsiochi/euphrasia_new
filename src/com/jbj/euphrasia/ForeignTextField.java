package com.jbj.euphrasia;

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
