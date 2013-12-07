package com.jbj.euphrasia;

import com.jbj.euphrasia.fields.AudioField;
import com.jbj.euphrasia.fields.DateField;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.managers.PlayManager;
import com.jbj.euphrasia.managers.RecordingManager;
import com.jbj.euphrasia.spinners.EuphrasiaSpinner;
import com.jbj.euphrasia.spinners.LanguageSpinner;
import com.jbj.euphrasia.spinners.PhrasebookSpinner;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class Controller {

	private EntryDatabaseManager myEntry;
	private RecordingManager myRecordingManager;
	private PlayManager myPlayManager;
	private Uri myCurrentUri;

	public Controller(Context context) {
		myEntry = new EntryDatabaseManager(context);
		myRecordingManager = new RecordingManager(context,this);
		myPlayManager = new PlayManager(context, this);
	}
	public String getAudioPath(){
		return myEntry.getAudioField().toString();
	}
	public void updateEntryField(Field field){
		field.updateEntryField(myEntry);
	}

	public void onRecord() {
		myRecordingManager.execute();
	}
	
	public void onSave() {
		//TODO way to disable save button or store an entry without a recording?
		//TODO need to save everything regardless of whether that EditText lost focus
		if(myCurrentUri == null) {
			Log.i("SAVING_STUFF", "new entry");
			(new DateField()).updateEntryField(myEntry);
			Field audioField = myRecordingManager.save();
			audioField.updateEntryField(myEntry);
			myCurrentUri = myEntry.saveEntry();
		} else {
			Log.i("SAVING_STUFF", "updating entry");
			myEntry.updateEntry(myCurrentUri);
		}
	}

	public void onPlay() {
		myPlayManager.execute();
	}
	
	public void setUri(Uri uri) {
		myCurrentUri = uri;
	}
	
	public void setInitialAudio(String audioPath) {
		this.updateEntryField(new AudioField(audioPath));
	}
	
	public boolean shouldSave() {
		return myEntry.shouldSave(3);
	}

	public boolean hasValid(EuphrasiaSpinner spinner) {
		if(spinner instanceof LanguageSpinner){
			return myEntry.hasValidLanguage();
		}
		if(spinner instanceof PhrasebookSpinner){
			return myEntry.hasValidPhrasebook();
		}
		else{
			return false;
		}
	}

}
