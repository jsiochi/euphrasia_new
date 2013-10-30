package com.jbj.euphrasia;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.util.Log;

/*
 * Create a new instance of android.media.MediaRecorder.
Set the audio source using MediaRecorder.setAudioSource(). 
	You will probably want to use MediaRecorder.AudioSource.MIC.
Set output file format using MediaRecorder.setOutputFormat().
Set output file name using MediaRecorder.setOutputFile().
Set the audio encoder using MediaRecorder.setAudioEncoder().
Call MediaRecorder.prepare() on the MediaRecorder instance.
To start audio capture, call MediaRecorder.start().
To stop audio capture, call MediaRecorder.stop().
When you are done with the MediaRecorder instance, call MediaRecorder.release() on it. 
	Calling MediaRecorder.release() is always recommended to free the resource immediately.
 */

public class RecordingManager extends MediaManager{
	
	private static final String STORAGE_LOCATION = File.pathSeparator + "Euphrasia";
	private MediaRecorder myRecorder;
	private File myCache;
	private FileManager myFileManager;
	
	public RecordingManager(Context context, Controller controller){
		super(context,controller);
		myFileManager = new FileManager();
	}
	
	protected void start(){
		myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        try {
			myCache = File.createTempFile("recording", "tmp",myContext.getCacheDir());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        myRecorder.setOutputFile(myCache.getAbsolutePath());
        myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            myRecorder.prepare();
        } catch (IOException e) {
            Log.e("AUDIO_RECORD_TEST","prepare() failed");
        }
        myRecorder.start();
	}
	
	protected void stop(){
		myRecorder.stop();
        myRecorder.release();
        myController.updateEntryField(new AudioField(myCache.getAbsolutePath()));
        myRecorder = null;
	}


	public String save() {
		//return string reference to file path
		String dirLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + STORAGE_LOCATION;
		File temporaryFile = new File(dirLocation);
		temporaryFile.mkdir();
		Date date = new Date();
		String pathEnding = File.pathSeparator + date.toString().hashCode();
		File permanentFile = new File(dirLocation + pathEnding);
		try
		{
		myFileManager.copyFile(myCache, permanentFile);
		return permanentFile.getAbsolutePath();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return "FILE NOT CREATED";
	}

}
