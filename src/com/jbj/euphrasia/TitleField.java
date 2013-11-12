package com.jbj.euphrasia;

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
