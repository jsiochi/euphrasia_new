package com.jbj.euphrasia;

public class AudioField extends Field {

	public AudioField(String data) {
		super(data);
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		entryManager.setAudioField(this);
		return entryManager;
	}

}
