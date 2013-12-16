package com.jbj.euphrasia;

import com.jbj.euphrasia.activities.EntryActivity;
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
import android.widget.Toast;

public class Controller {

	private EntryDatabaseManager myEntry;
	private RecordingManager myRecordingManager;
	private PlayManager myPlayManager;
	private Uri myCurrentUri;
	private EntryActivity mySourceActivity;

	public Controller(Context context) {
		myEntry = new EntryDatabaseManager(context);
		myRecordingManager = new RecordingManager(context,this);
		myPlayManager = new PlayManager(context, this);
	}
	public String getAudioPath(){
		return myEntry.getAudioField().toString();
	}
	public void setSourceActivity(EntryActivity activity){
		mySourceActivity = activity;
	}
	public void updateEntryField(Field field){
		field.updateEntryField(myEntry);
	}
	
	public void enablePlayButton(){
		mySourceActivity.enablePlay();
	}

	public void onRecord() {
		myRecordingManager.execute();
	}
	
	public void onSave() {
		if(!myEntry.getForeignText().toString().isEmpty() && !myEntry.getNativeText().toString().isEmpty()){
			if(myCurrentUri == null) {
				(new DateField()).updateEntryField(myEntry);
				Field audioField = myRecordingManager.save();
				audioField.updateEntryField(myEntry);
				myCurrentUri = myEntry.saveEntry();
				Toast.makeText(mySourceActivity, "Entry saved.", Toast.LENGTH_SHORT).show();
			} else {
				myEntry.updateEntry(myCurrentUri);
			}
		}
		else{
			Toast.makeText(mySourceActivity, "You must enter both native and foreign text before saving.", Toast.LENGTH_SHORT).show();
		}
	}

	public void onPlay() {
		try{
			myPlayManager.execute();
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
