package com.jbj.euphrasia.remote;

import java.io.File;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;

import com.jbj.euphrasia.managers.SyncManager;

import android.os.Bundle;
import android.util.Log;

public class AudioUploadTask extends AbstractRemoteTask {
	
	private File myAudioFile;
	
	public void setAudioFile(String path){
		myAudioFile = new File(path);
	}

	@Override
	protected HttpUriRequest getUriRequest(String[]... params) {
		HttpPost post = new HttpPost(getServiceUrl());
	    MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    FileBody body = new FileBody(myAudioFile);
	    entity.addPart("file", body);
	    try {
			post.setEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
//	private byte[] convertAudio(String filePath){
//		File f = new File(filePath);
//		byte[] soundFileByteArray = new byte[(int) f.length()];
//		try {
//			FileInputStream fis = new FileInputStream(f);
//			fis.read(soundFileByteArray);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally{
//			return soundFileByteArray;
//		}
//	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/db_audio_upload.php";
	}
	
	@Override
	protected void onPostExecute(Bundle args) {
		SyncManager.setActivity(mySourceActivity);
		SyncManager.completeSync();
	}

}
