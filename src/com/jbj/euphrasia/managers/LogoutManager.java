package com.jbj.euphrasia.managers;

import com.jbj.euphrasia.activities.LoginActivity;
import com.jbj.euphrasia.interfaces.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class LogoutManager implements Constants{
	
	private static Activity myActivity;
	
	public static void setActivity(Activity activity){
		myActivity = activity;
	}
	
	public static void logout(){
		SharedPreferences sharedPreferences = myActivity.getSharedPreferences(PREFS_NAME, 0);
    	SharedPreferences.Editor editor = sharedPreferences.edit();
    	//clear stored user account
    	editor.remove(PREFS_USERNAME_KEY);
    	editor.remove(PREFS_PASSWORD_KEY);
    	editor.remove(PREFS_USERID_KEY);
    	editor.remove("remember");
    	editor.commit();
    	myActivity.startActivity(new Intent(myActivity,LoginActivity.class));
	}

}
