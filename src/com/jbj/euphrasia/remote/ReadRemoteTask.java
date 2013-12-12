package com.jbj.euphrasia.remote;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import com.jbj.euphrasia.activities.SearchActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ReadRemoteTask extends AsyncTask {
	
	private Activity mySourceActivity;
	
	private String url_read_service = "http://goeuphrasia.com/php/read.php";
	
	public void setSourceActivity(Activity activity){
		mySourceActivity = activity;
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

	@Override
	protected Object doInBackground(Object... args) {
		//add to this in order to read with filter. 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		return null;
	}


}
