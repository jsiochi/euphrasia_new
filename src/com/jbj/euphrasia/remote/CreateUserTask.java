package com.jbj.euphrasia.remote;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;

import com.jbj.euphrasia.activities.LoginActivity;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

public class CreateUserTask extends AbstractRemoteTask {
	
	protected Bundle doInBackground(String[]... arg0) {
		Looper.getMainLooper().prepare();
		super.doInBackground(arg0);
		int success;
		try {
			success = myJsonObject.getInt("success");
			if(success==1){
				//user exists. Send to main activity.
				LoginActivity login = (LoginActivity) mySourceActivity;
				login.login("holder");
			}
			else{
				//user does not exist. Reject access.
				Toast.makeText(mySourceActivity,"User not found!", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
		return "http://goeuphrasia.com/php/db_create_user.php";
	}

}
