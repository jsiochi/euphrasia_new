package com.jbj.euphrasia;

import java.util.ArrayList;
import java.util.List;

import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.ClearRemoteTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;

import android.app.Activity;
import android.database.Cursor;
import android.widget.Toast;

public class SyncManager implements Constants {
	
	private static Activity myActivity;
	private static Cursor myCursor;
	private static String myID;
	
	public static void setActivity(Activity source){
		myActivity = source;
		String id = myActivity.getSharedPreferences("My Preferences", 0).getString("user_id","DNE");
		if(!id.equals("DNE")){
			myID = id;
		}
	}
	
	public static void sync(){
		Toast.makeText(myActivity, "Attempting to sync to remote", Toast.LENGTH_LONG).show();
		//myActivity.getMainLooper();
		AbstractRemoteTask clear = new ClearRemoteTask();
		clear.setActivity(myActivity);
		clear.execute(new String[]{"user_id", myID});
		Cursor cursor = myActivity.getContentResolver().query(EntryProvider.CONTENT_URI, SELECT_ALL_PROJECTION, null,null,null);
		myCursor = cursor;
		completeSync();
	}
	
	public static void completeSync(){
		if(myCursor.moveToNext()) {
			int j;
			j = 0;
			List<String[]> params = new ArrayList<String[]>(); 
			for(int i=0;i<myCursor.getColumnCount();i++){
				String[] param = new String[2];
				param[0] = myCursor.getColumnName(i);
				param[1] = myCursor.getString(i);
				params.add(param);
				j++;
			}
			String[] param = new String[2];
			param[0] = "user_id";
			param[1] = myID;
			params.add(param);
			AbstractRemoteTask write = new WriteRemoteTask();
			write.setActivity(myActivity);
			write.execute(params.toArray(new String[params.size()][2]));
		}
		else {
			myCursor.close();
		}
	}

}
