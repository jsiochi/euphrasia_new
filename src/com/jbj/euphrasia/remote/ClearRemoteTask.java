package com.jbj.euphrasia.remote;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import android.os.Bundle;
import android.os.Looper;

public class ClearRemoteTask extends AbstractRemoteTask {
	
	protected Bundle doInBackground(String[]... arg0) {
		super.doInBackground(arg0);
		return null;
	}

	@Override
	protected HttpUriRequest getUriRequest(String[]...params) {
		myPairs = this.setParams(params);
		HttpPost post = new HttpPost(myServiceUrl);
		try {
			post.setEntity(new UrlEncodedFormEntity(myPairs));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/db_clear.php";
	}

}
