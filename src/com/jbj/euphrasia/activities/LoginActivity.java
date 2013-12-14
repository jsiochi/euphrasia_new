package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.R;
import com.jbj.euphrasia.R.layout;
import com.jbj.euphrasia.R.menu;
import com.jbj.euphrasia.interfaces.Constants;
import com.jbj.euphrasia.remote.AbstractRemoteTask;
import com.jbj.euphrasia.remote.CreateUserTask;
import com.jbj.euphrasia.remote.ReadUserTask;
import com.jbj.euphrasia.remote.WriteRemoteTask;

import dialog_fragments.ExistingUserDialog;
import dialog_fragments.NewUserDialog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity implements Constants{

	private String myUsername;
	private String myEmail;
	private String myPassword;

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

	public void onUserCreation(String accountName, String accountPassword, String accountEmail) {
		// user has specified the desired credentials for a new user account. Initialize
		// database. 
		AbstractRemoteTask writeUser = new CreateUserTask();
		writeUser.setActivity(this);
		String[] name = new String[]{"user_name",accountName};
		String[] email = new String[]{"user_email",accountEmail};
		String[] password = new String[]{"user_password",accountPassword};
		writeUser.execute(new String[][]{name,email,password});
	}
	
	public void login(String name, String password){
		Intent intent = new Intent(this,MainActivity.class);
		intent.setAction(ACTION_EXISTING_LOGIN);
		intent.putExtra(EXTRA_EXISTING_USER, name);
		startActivity(intent);
	}
	
	public void onLoginAttempt(String name, String password){
		// check the database to see if these 
		// credentials match an existing user
		AbstractRemoteTask checkUser = new ReadUserTask();
		checkUser.setActivity(this);
		String[] userName = new String[]{"user_name",name};
		String[] userPassword = new String[]{"password",password};
		String[][] params = new String[][]{userName,userPassword};
		checkUser.execute(params);
//		if(name.equals("Euphrasia") && password.equals("1234")){
//			Intent intent = new Intent(this,MainActivity.class);
//			intent.setAction(ACTION_EXISTING_LOGIN);
//			intent.putExtra(EXTRA_EXISTING_USER, name);
//			startActivity(intent);
//		}
	}

}
