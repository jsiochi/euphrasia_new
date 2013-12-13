package com.jbj.euphrasia.remote;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import com.jbj.euphrasia.activities.*;
import android.widget.Toast;

public class ReadUserTask extends AbstractRemoteTask {
	
	@Override
	public Void doInBackground(String[]...params){
		super.doInBackground(params);
		try {
			int success = myJsonObject.getInt("success");
			if(success==1){
				Toast.makeText(mySourceActivity, "User read from database", Toast.LENGTH_LONG).show();
				String name = myJsonObject.getString("user_name");
				String password = myJsonObject.getString("pass");
				LoginActivity login = (LoginActivity)mySourceActivity;
				//user account found. Send to main activity.
				login.login(name, password);
			}
			if(success==0){
				// no such user. Reject access. 
				Toast.makeText(mySourceActivity, "Account not found! Please try again. Input is case-sensitive.", 10).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected HttpUriRequest getUriRequest() {
		return new HttpGet(myServiceUrl);
	}

	@Override
	protected String getServiceUrl() {
		return "http://goeuphrasia.com//php//db_read_user.php";
	}

}
