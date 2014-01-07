package com.jbj.euphrasia.remote;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.activities.MainActivity;
import com.jbj.euphrasia.managers.SyncManager;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class WriteRemoteTask extends AbstractRemoteTask {

	@Override
	protected Bundle doInBackground(String[]... arg0) {
		super.doInBackground(arg0);
		try{
			int success = myJsonObject.getInt("success");
			Log.i("JsonMessage", myJsonObject.getString("message"));
			Log.i("JsonSuccess", String.valueOf(success));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected HttpUriRequest getUriRequest(String[]...params) {
		myPairs = this.setParams(params);
		HttpPost post = new HttpPost(myServiceUrl);
		post.setHeader("Accept", "application/json");
		try {
			post.setEntity(new UrlEncodedFormEntity(myPairs));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/db_create_entry.php";
	}
	
	protected void onPostExecute(Bundle args) {
		//if(myAudioPath!=null){
		if(1==2){
			AudioUploadTask upload = new AudioUploadTask();
			upload.setActivity(mySourceActivity);
			upload.setAudioFile(myAudioPath);
			String[][] params = new String[0][0];
			upload.execute(params);
		}
		else{
			SyncManager.setActivity(mySourceActivity);
			SyncManager.completeSync();
		}
	}

}
