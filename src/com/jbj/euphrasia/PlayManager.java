package com.jbj.euphrasia;

import java.io.FileDescriptor;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

public class PlayManager extends MediaManager {
	
	private MediaPlayer myMediaPlayer;

	public PlayManager() {
		//FIGURE OUT WHAT TO DO WITH FILENAMES
		super(new FileDescriptor(), context);
		myMediaPlayer = new MediaPlayer();
	}

	@Override
	public void start() {
		myMediaPlayer = new MediaPlayer();
        try {
            myMediaPlayer.setDataSource(myFileName);
            myMediaPlayer.prepare();
            myMediaPlayer.start();
        } catch (IOException e) {
            Log.e("AUDIO_PLAY_TEST", "prepare() failed");
        }
	}

	@Override
	public void stop() {
		myMediaPlayer.release();
        myMediaPlayer = null;
	}

	@Override
	public void pause() {
		if(myStatus){
			myMediaPlayer.release();
			myMediaPlayer = null;
		}
	}

}
