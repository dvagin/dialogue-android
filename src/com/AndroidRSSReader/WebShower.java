package com.AndroidRSSReader;
import com.AndroidRSSReader.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


public class WebShower extends Activity{
	private String url;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_item_displayer);
		url = getIntent().getStringExtra("Url");
		WebView wv1 = (WebView) findViewById(R.id.webView1);
		wv1.loadUrl("http://www.rhodesforum.org/mobile/"+url+".html");
		Log.d("tag","http://www.rhodesforum.org/mobile/"+url+".html");
		
	}

}

