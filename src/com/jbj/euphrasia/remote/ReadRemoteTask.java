package com.jbj.euphrasia.remote;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jbj.euphrasia.EntryProvider;
import com.jbj.euphrasia.activities.RemoteSearchActivity;
import com.jbj.euphrasia.activities.SearchActivity;
import com.jbj.euphrasia.interfaces.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


public class ReadRemoteTask extends AbstractRemoteTask {

	@SuppressWarnings("finally")
	@Override
	protected Bundle doInBackground(String[]... args) { 
		super.doInBackground(args);
		Bundle queryBundle = new Bundle();
		try{
			int success = myJsonObject.getInt("success");
			Log.i("SUCCESS",String.valueOf(success));
			if(success==1){
				JSONArray arrayResults = myJsonObject.getJSONArray("entries");
				Log.i("array size"," "+String.valueOf(arrayResults.length()));
				for(int i = 0;i<arrayResults.length();i++){
					Log.i("Entry number",String.valueOf(i));
					Bundle entryBundle = new Bundle();
					JSONObject object = arrayResults.getJSONObject(i);
					entryBundle.putString("title", object.getString("title"));
					entryBundle.putString("native_text", object.getString("native_text"));
					entryBundle.putString("foreign_text", object.getString("foreign_text"));
					entryBundle.putString("tag", object.getString("tag"));
					entryBundle.putString("audio", object.getString("audio"));
					entryBundle.putString("language", object.getString("language"));
					entryBundle.putString("phrasebook", object.getString("phrasebook"));
					entryBundle.putString("user_id", object.getString("user_id"));
					entryBundle.putString("date", object.getString("date"));
					queryBundle.putBundle(String.valueOf(i), entryBundle);
				}
			}
			else{
				return null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			return queryBundle;
		}
		
	}
	
	@Override
	protected void onPostExecute(Bundle bundle){
		if(bundle!=null){
			
			RemoteSearchActivity remoteSearch = (RemoteSearchActivity)mySourceActivity;
			remoteSearch.getContentResolver().call(EntryProvider.CONTENT_REMOTE_URI, EntryProvider.VIEW_REMOTE, null, bundle);
			Intent displayResults = new Intent(remoteSearch,SearchActivity.class);
			displayResults.putExtra(Constants.EXTRA_REMOTE_BUNDLE, bundle);
			displayResults.setAction(Constants.ACTION_REMOTE_QUERY);
			remoteSearch.startActivity(displayResults);
		}
		else{
			Toast.makeText(mySourceActivity,"Failed to query remote database!",Toast.LENGTH_LONG).show();
		}
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
		return "http://goeuphrasia.com/php/db_read.php";
	}


}
