package com.jbj.euphrasia;

public class NullField extends Field {

	private static final String NULL_DATA = "THIS IS NULL";

	public NullField() {
		super();
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		return entryManager;
	}

}
