package com.jbj.euphrasia.remote;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

public class ClearRemoteTask extends AbstractRemoteTask {

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
		return "http://goeuphrasia.com//php//db_clear.php";
	}

}
