package com.jbj.euphrasia.remote;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;

import android.os.Bundle;
import android.util.Log;

public class AudioUploadTask extends AbstractRemoteTask {

	@Override
	protected HttpUriRequest getUriRequest(String[]... params) {
		HttpPost post = new HttpPost(getServiceUrl());
		byte[] data = convertAudio("");
		post.setEntity(new  ByteArrayEntity(data));
		return post;
	}
	
	@Override
	protected Bundle doInBackground(String[]... params) {
		super.doInBackground(params);
		try {
			int success = myJsonObject.getInt("success");
			if(success == 1){
				Log.i("SUCCESS","File uploaded successfully");
			}
			else{
				Log.i("FAILURE","File not uploaded");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] convertAudio(String filePath){
		File f = new File(filePath);
		byte[] soundFileByteArray = new byte[(int) f.length()];
		try {
			FileInputStream fis = new FileInputStream(f);
			fis.read(soundFileByteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			return soundFileByteArray;
		}
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/db_audio_upload.php";
	}

}
