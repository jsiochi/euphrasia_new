package com.jbj.euphrasia.remote;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;


public class ReadRemoteTask extends AbstractRemoteTask {

	@Override
	protected Void doInBackground(String[]... args) { 
		super.doInBackground(args);
		return null;
	}

	@Override
	protected HttpUriRequest getUriRequest() {
		return new HttpGet(myServiceUrl);
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/read.php";
	}


}
