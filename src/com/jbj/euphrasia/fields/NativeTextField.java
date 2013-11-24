package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class NativeTextField extends Field {

	public NativeTextField(String data) {
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		entryManager.setNativeText(this);
		return entryManager;
	}

}
