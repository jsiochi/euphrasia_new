package com.jbj.euphrasia;

import java.io.FileDescriptor;
import java.io.IOException;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class PlayManager extends MediaManager {
	
	private MediaPlayer myMediaPlayer;

	public PlayManager(Context context, Controller controller) {
		super(context, controller);
		myMediaPlayer = new MediaPlayer();
	}

	@Override
	public void start() {
		myMediaPlayer = new MediaPlayer();
        try {
            myMediaPlayer.setDataSource(myController.getAudioPath());
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


}
