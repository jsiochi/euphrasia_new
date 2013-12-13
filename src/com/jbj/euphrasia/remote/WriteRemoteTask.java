package com.jbj.euphrasia.remote;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import android.widget.Toast;

public class WriteRemoteTask extends AbstractRemoteTask {

	@Override
	protected Object doInBackground(Object... arg0) {
		super.doInBackground(arg0);
		try{
			int success = myJsonObject.getInt("success");
			if(success==1){
				Toast.makeText(mySourceActivity, "Successfully synced to remote database.", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(mySourceActivity, "Failed to sync to remote database.", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected HttpUriRequest getUriRequest() {
		return new HttpPost(myServiceUrl);
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com//php//db_create_entry.php";
	}

}
