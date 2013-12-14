package com.jbj.euphrasia.remote;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public abstract class AbstractRemoteTask extends AsyncTask<String[],Void,Bundle> {
	
	protected String myServiceUrl;
	protected BasicHttpParams myParams;
	protected JSONObject myJsonObject;
	protected Activity mySourceActivity;
	protected List<NameValuePair> myPairs;
//	protected Handler mHandler = new Handler(Looper.getMainLooper());
//	
//	public void run() {
//		mHandler.post(new Runnable() {
//			public void run() {
//				
//			}
//		});
//	}
	
	/**
	 * @param Object[] params : an array of objects where each object is a array of Objects
	 * Converts each nested object array to a string array and uses to create BasicHttpParams.
	 */
	protected List<NameValuePair> setParams(String[]... params){
		myParams = new BasicHttpParams();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for(int i = 0;i<params.length;i++){
			String[] param = params[i];
			myParams.setParameter(param[0], param[1]);
			Log.i("AbstractTask","Added parameter "+ myParams.getParameter(param[0]));
			NameValuePair pair = new BasicNameValuePair(param[0],param[1]);
			pairs.add(pair);
		}
		return pairs;
	}
	
	public void setActivity(Activity activity){
		mySourceActivity = activity;
	}
	
	@Override
	protected void onPreExecute(){
		myServiceUrl = this.getServiceUrl();
	}

	@Override
	protected Bundle doInBackground(String[]... params) {
		Log.i("AbstractTask","Accessing service at "+this.getServiceUrl());
		//post.setHeader("Content-type", "application/json");
		HttpUriRequest post = this.getUriRequest(params);
		InputStream inputStream = null;
		String result = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
		    Log.i("AbstractTaskHttpClient", client.getParams().getParameter("title") + " " + client.getParams().getParameter("native_text")+ " " + client.getParams().getParameter("foreign_text"));

			HttpResponse response = client.execute(post);           
		    HttpEntity entity = response.getEntity();
		    inputStream = entity.getContent();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
		    StringBuilder sb = new StringBuilder();

		    String line = null;
		    while ((line = reader.readLine()) != null)
		    {
		        sb.append(line + "\n");
		    }
		    result = sb.toString();
		    Log.i("AbstractTaskResult",result);
		    myJsonObject = new JSONObject(result);
		} catch (Exception e) { 
		    e.printStackTrace();
		}
		return null;
	}

	
	protected abstract HttpUriRequest getUriRequest(String[]...params);
	
	protected abstract String getServiceUrl();

}
