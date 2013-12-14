package com.jbj.euphrasia.remote;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import android.os.Bundle;
import android.os.Looper;

public class CreateUserTask extends AbstractRemoteTask {
	
	protected Bundle doInBackground(String[]... arg0) {
		Looper.getMainLooper().prepare();
		super.doInBackground(arg0);
		return null;
	}
	
	@Override
	protected HttpUriRequest getUriRequest(String[]...params) {
		return new HttpPost(myServiceUrl);
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com//php//db_create_user.php";
	}

}
