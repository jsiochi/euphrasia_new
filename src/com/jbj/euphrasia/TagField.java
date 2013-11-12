package com.jbj.euphrasia;

public class TagField extends Field {
	
	public TagField(String data){
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		entryManager.setTagField(this);
		return entryManager;
	}

}
