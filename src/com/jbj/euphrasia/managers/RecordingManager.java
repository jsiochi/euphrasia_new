package com.jbj.euphrasia.managers;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.jbj.euphrasia.Controller;
import com.jbj.euphrasia.fields.AudioField;
import com.jbj.euphrasia.fields.Field;
import com.jbj.euphrasia.fields.NullField;

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
import android.widget.Toast;

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
	
	private static final String STORAGE_LOCATION = File.separator + "Euphrasia";
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
			myCache = File.createTempFile("recording", "tmp", myContext.getCacheDir());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        myRecorder.setOutputFile(myCache.getAbsolutePath());
        Log.i("MEDIA_START_CACHE", "cache location: " + myCache.getAbsolutePath());
        myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            myRecorder.prepare();
        } catch (IOException e) {
            Log.e("AUDIO_RECORD_TEST","prepare() failed");
        }
        Log.d("MEDIA_RECORD_START", "recording");
        myRecorder.start();
        Toast toast = Toast.makeText(myContext, "Now recording", Toast.LENGTH_SHORT);
        toast.show();
	}
	
	protected void stop(){
		myRecorder.stop();
        myRecorder.release();
        Toast toast = Toast.makeText(myContext, "Recording stopped", Toast.LENGTH_SHORT);
        toast.show();
        myController.updateEntryField(new AudioField(myCache.getAbsolutePath()));
        myRecorder = null;
	}


	public Field save() {
		//return string reference to file path
		String dirLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + STORAGE_LOCATION;
		File temporaryFile = new File(dirLocation);
		temporaryFile.mkdir();
		Date date = new Date();
		String pathEnding = File.separator + date.toString().hashCode();
		File permanentFile = new File(dirLocation + pathEnding);
		try
		{
			myFileManager.copyFile(myCache, permanentFile);
			Field audioField = new AudioField();
			audioField.setData(permanentFile.getAbsolutePath());
			return audioField;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return new NullField();
	}

}
