package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class TagField extends Field {
	
	public TagField(){
		super("");
	}
	
	public TagField(String tags){
		//might have to call method to properly format tags
		super(tags);
	}	

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		entryManager.setTagField(this);
		return entryManager;
	}

}
