package com.jbj.euphrasia;

import com.jbj.euphrasia.activities.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class LogoutManager {
	
	private Activity myActivity;
	
	public LogoutManager(Activity activity){
		myActivity = activity;
	}
	
	public void logout(){
		SharedPreferences sharedPreferences = myActivity.getSharedPreferences("My Preferences", 0);
    	SharedPreferences.Editor editor = sharedPreferences.edit();
    	//clear stored user account
    	editor.remove("user_name");
    	editor.remove("pass");
    	editor.remove("user_id");
    	editor.commit();
    	myActivity.startActivity(new Intent(myActivity,LoginActivity.class));
	}

}
