package com.jbj.euphrasia.remote;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.SyncManager;
import com.jbj.euphrasia.activities.MainActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class WriteRemoteTask extends AbstractRemoteTask {

	@Override
	protected Bundle doInBackground(String[]... arg0) {
		//Looper.getMainLooper().prepare();
		super.doInBackground(arg0);
		try{
			int success = myJsonObject.getInt("success");
			Log.i("YOMAMA", myJsonObject.getString("message"));
			Log.i("JOMAMA", String.valueOf(success));
			//Log.i("YOKO_ONO", myJsonObject.getString("errorjunk"));
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
		SyncManager.setActivity(mySourceActivity);
		SyncManager.completeSync();
	}

}
