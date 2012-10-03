package com.AndroidRSSReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Splash extends Activity {
	private static final int STARTUP_DELAY = 3000;
	private AspectRatioImageView logo;
	private Handler handler = new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.splash);
	    
	    logo = (AspectRatioImageView) findViewById(R.id.imageView1);
	    logo.setAllignType(AspectRatioImageView.ALLIGN_BY_HEIGHT);
	 
	    handler.postDelayed(showMain, STARTUP_DELAY);
	}
	
	private Runnable showMain = new Runnable() {
		
		@Override
		public void run() {
			Intent intent = new Intent (getApplicationContext(), AndroidRSSReader.class);
			startActivity(intent);
			finish();
		}
	};
}
