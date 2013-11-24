package com.jbj.euphrasia.fields;

import com.jbj.euphrasia.EntryDatabaseManager;

public class AudioField extends Field {

	private static final String AUDIODATA_INITIAL = "AWAITING FILE LOCATION";

	public AudioField() {
		super(AUDIODATA_INITIAL);
	}
	
	public AudioField(String data){
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		entryManager.setAudioField(this);
		return entryManager;
	}

}
