package com.AndroidRSSReader;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

public class RssItemDisplayer extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_item_displayer);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		RssItem selectedRssItem = com.AndroidRSSReader.AndroidRSSTab1.selectedRssItem;
		//Bundle extras = getIntent().getExtras();
		//TextView titleTv = (TextView)findViewById(R.id.titleTextView);	
		//TextView contentTv = (TextView)findViewById(R.id.contentTextView);	
		WebView wv1 = (WebView) findViewById(R.id.webView1);
		String title = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
		/*title = "\n" + selectedRssItem.getTitle() + "   ( "
				+ sdf.format(selectedRssItem.getPubDate()) + " )\n\n";
		*/
		
		StringBuffer s1 = new StringBuffer(selectedRssItem.getDetail());
		s1.insert(selectedRssItem.getDetail().indexOf("</p>")+4, "</br><div style='clear:both'></div>");
		String detail = s1.toString();
		Log.d("tag","detail is "+detail);
		//String detail = selectedRssItem.getDetail().indexOf("</p>")
		String content = "<p><span style='font-size:12px; color:grey;'>"+sdf.format(selectedRssItem.getPubDate())+"</span><span style='font-size:12px; float:right; color:green;'>"+selectedRssItem.getAuthor()+"</span></p>";
		content += detail + "<p><a href=\""
				+ selectedRssItem.getLink() + "\">Read full article</a></p>";
		
		//titleTv.setText(title);
		wv1.loadData(content, "text/html", null);
		//contentTv.setText(content);
	}
}