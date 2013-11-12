package com.jbj.euphrasia;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import com.jbj.euphrasia.R;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getSupportActionBar();
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onStartEntry(View view){
		startActivity(new Intent(this, EntryActivity.class));
	}
	
	/*public void onStartSearch(View view){
		startActivity(new Intent(this,SearchActivity.class));
	}*/
	

}
