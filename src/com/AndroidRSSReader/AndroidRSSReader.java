package com.AndroidRSSReader;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class AndroidRSSReader extends TabActivity {
	
	public static final String PREFS_NAME = "Dialogue prefs";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.maintabs);
        /*View main = (View)findViewById(R.id.layout);
        main.setBackgroundColor(Color.WHITE);*/
        TabHost tabs = getTabHost();
        
        TabSpec newsreader = tabs.newTabSpec("News");
        Intent newsread = new Intent(this,AndroidRSSTab1.class);
        newsread.putExtra("Url","http://www.wpfdc.org/index.php?format=feed&type=rss&lang=en");
        newsreader.setIndicator("News", getResources().getDrawable(R.drawable.ic_news_drawable));
        newsreader.setContent(newsread);

        
        TabSpec politicsreader = tabs.newTabSpec("Topics");
        politicsreader.setIndicator("Topics", getResources().getDrawable(R.drawable.ic_topic_drawable));
        Intent politicsread = new Intent(this,AndroidRSSTab2.class);
        politicsread.putExtra("Url"," http://wpfdc.org/index.php?option=com_content&view=category&layout=blog&id=41&Itemid=92&lang=en&format=feed&type=rss");
        politicsreader.setContent(politicsread);
        
        TabSpec timereader = tabs.newTabSpec("Timetable");
        timereader.setIndicator("Forum", getResources().getDrawable(R.drawable.ic_forum_drawable));
        Intent timeintent = new Intent(this,AndroidRSSTab3.class);
        timereader.setContent(timeintent);
        
        TabSpec aboutreader = tabs.newTabSpec("About");
        aboutreader.setIndicator("About", getResources().getDrawable(R.drawable.ic_about_drawable));
        Intent aboutintent = new Intent(this,AndroidRSSTab4.class);
        aboutreader.setContent(aboutintent);        
        
        tabs.addTab(newsreader);
        tabs.addTab(politicsreader);
        tabs.addTab(timereader);
        tabs.addTab(aboutreader);
        
        /*final TextView tv = (TextView) tabs.getChildAt(0).findViewById(android.R.id.title);        
        tv.setTextColor(this.getResources().getColorStateList(R.drawable.ic_tab_text));
        final TextView tv1 = (TextView) tabs.getChildAt(1).findViewById(android.R.id.title);        
        tv1.setTextColor(this.getResources().getColorStateList(R.drawable.ic_tab_text));
        final TextView tv2 = (TextView) tabs.getChildAt(2).findViewById(android.R.id.title);        
        tv2.setTextColor(this.getResources().getColorStateList(R.drawable.ic_tab_text));*/
        /*final TextView tv3 = (TextView) tabs.getChildAt(3).findViewById(android.R.id.title);        
        tv3.setTextColor(this.getResources().getColorStateList(R.drawable.ic_tab_text));*/
        
	}
}
