package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;

import dialog_fragments.ExistingUserDialog;
import dialog_fragments.NewUserDialog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends FragmentActivity implements Constants{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	public void doNewUser(View view){
		//launch a dialogue to create a new user account
		NewUserDialog dialog = new NewUserDialog();
		dialog.setSourceActivity(this);
		dialog.show(getSupportFragmentManager(), "new_user");
	}
	
	public void doExistingUser(View view){
		//launch a dialogue to login to existing account
		ExistingUserDialog dialog = new ExistingUserDialog();
		dialog.setSourceActivity(this);
		dialog.show(getSupportFragmentManager(), "existing_user");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void onUserCreation(String accountName, String accountPassword) {
		// user has specified the desired credentials for a new user account. Initialize
		// database. 
	}
	
	public void onLoginAttempt(String name, String password){
		// check the database to see if these 
		// credentials match an existing user
		Log.i("SNOW_GLOBE",name + " " + password);
		if(name.equals("Euphrasia") && password.equals("1234")){
			Intent intent = new Intent(this,MainActivity.class);
			intent.setAction(ACTION_EXISTING_LOGIN);
			intent.putExtra(EXTRA_EXISTING_USER, name);
			startActivity(intent);
		}
	}

}
