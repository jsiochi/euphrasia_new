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
	
	private Activity myActivity;
	
	public SyncManager(Activity activity){
		myActivity = activity;
	}
	
	public void sync(){
		Toast.makeText(myActivity, "Attempting to sync to remote", Toast.LENGTH_LONG).show();
		myActivity.getMainLooper();
		AbstractRemoteTask clear = new ClearRemoteTask();
		clear.setActivity(myActivity);
		clear.execute(new String[]{"user_id", "0"});
		Cursor cursor = myActivity.getContentResolver().query(EntryProvider.CONTENT_URI, SELECT_ALL_PROJECTION, null,null,null);
		if(cursor.moveToNext()) {
			int j;
			j = 0;
			List<String[]> params = new ArrayList<String[]>(); 
			for(int i=0;i<cursor.getColumnCount();i++){
				String[] param = new String[2];
				param[0] = cursor.getColumnName(i);
				param[1] = cursor.getString(i);
				params.add(param);
				j++;
			}
			String[] param = new String[2];
			param[0] = "user_id";
			param[1] = "2";
			params.add(param);
			AbstractRemoteTask write = new WriteRemoteTask();
			write.setActivity(myActivity);
			write.execute(params.toArray(new String[params.size()][2]));
		}
		else {
			cursor.close();
		}
	}

}
