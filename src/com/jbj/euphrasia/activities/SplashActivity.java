package com.jbj.euphrasia.activities;

import com.jbj.euphrasia.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

 
public class SplashActivity extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
 
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
 
        new Handler().postDelayed(new Runnable() {
 
        	/* New Handler to start the Menu-Activity 
             * and close this Splash-Screen after some seconds.*/
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(i);
                SplashActivity.this.finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
