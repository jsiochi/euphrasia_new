package com.jbj.euphrasia.remote;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jbj.euphrasia.activities.*;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class ReadUserTask extends AbstractRemoteTask {
	
	@Override
	public Bundle doInBackground(String[]...params){
		Looper.getMainLooper().prepare();
		super.doInBackground(params);
		Bundle theArgs = new Bundle();
		try {
			int success = myJsonObject.getInt("success");
			if(success==1){
				Toast.makeText(mySourceActivity, "User read from database", Toast.LENGTH_LONG).show();
				JSONArray array = myJsonObject.getJSONArray("product");
				JSONObject user = array.getJSONObject(0);
				String user_id = user.getString("user_id");
				//String password = myJsonObject.getString("pass");
				theArgs.putBoolean("CanLogin", true);
				theArgs.putString("UID",user_id);
			}
			else{
				// no such user. Reject access. 
				Toast.makeText(mySourceActivity, "Account not found! Please try again. Input is case-sensitive.", 10).show();
				theArgs.putBoolean("CanLogin", false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return theArgs;
	}

	@Override
	protected HttpUriRequest getUriRequest(String[]...params) {
		myPairs = this.setParams(params);
		String url = getServiceUrl();
		String requestEnding = URLEncodedUtils.format(myPairs, "utf-8");
		url += "?" + requestEnding;
		Log.i("ReadUserTask",url);
		HttpGet get = new HttpGet(url);
		return get;
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com/php/db_read_user.php";
	}
	
	protected void onPostExecute(Bundle args) {
		//log the user in
		LoginActivity login = (LoginActivity)mySourceActivity;
		Boolean canLogin = args.getBoolean("CanLogin");
		if(!canLogin)
			return;
		login.login(args.getString("UID"));
	}

}
