package com.AndroidRSSReader;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class RssItemDisplayer extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_item_displayer);
		RssItem selectedRssItem = com.AndroidRSSReader.AndroidRSSTab1.selectedRssItem;
		//Bundle extras = getIntent().getExtras();
		//TextView titleTv = (TextView)findViewById(R.id.titleTextView);	
		//TextView contentTv = (TextView)findViewById(R.id.contentTextView);	
		WebView wv1 = (WebView) findViewById(R.id.webView1);
				
		String title = "";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
		/*title = "\n" + selectedRssItem.getTitle() + "   ( "
				+ sdf.format(selectedRssItem.getPubDate()) + " )\n\n";
		*/
		String content = "";
		content += selectedRssItem.getDetail() + "<p><a href=\""
				+ selectedRssItem.getLink() + "\">"+selectedRssItem.getLink()+"</a></p>";
		
		//titleTv.setText(title);
		wv1.loadData(content, "text/html", null);
		//contentTv.setText(content);
	}
}