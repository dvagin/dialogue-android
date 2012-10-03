package com.AndroidRSSReader;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class HallViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hall_plan);
		//ImageView hallView = (ImageView) findViewById(R.id.imageView1);
		 String imageUrl =  "file:///android_asset/halls_plan.jpg";
	      WebView wv = (WebView) findViewById(R.id.webView1);
	        wv.getSettings().setBuiltInZoomControls(true);
	     wv.loadUrl(imageUrl);
	}
	
}
