package com.jbj.euphrasia;
import java.io.FileDescriptor;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
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
	
	private MediaRecorder myRecorder;
	
	public RecordingManager(){
		super(new FileDescriptor());
	}
	
	protected void start(){
		myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myRecorder.setOutputFile(myFileName);
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
        myRecorder = null;
	}

	@Override
	public void pause() {
		if(myStatus)
			myRecorder.release();
			myRecorder = null;
	}

}
