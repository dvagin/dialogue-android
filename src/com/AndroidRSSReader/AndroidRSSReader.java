package com.AndroidRSSReader;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidRSSReader extends TabActivity {
	
	public static final String PREFS_NAME = "Dialogue prefs";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);
        /*View main = (View)findViewById(R.id.layout);
        main.setBackgroundColor(Color.WHITE);*/
        TabHost tabs = getTabHost();
        
        TabSpec newsreader = tabs.newTabSpec("News");
        Intent newsread = new Intent(this,AndroidRSSTab1.class);
        newsread.putExtra("Url","http://wpfdc.org/index.php?option=com_content&view=category&layout=blog&id=40&Itemid=93&lang=en&format=feed&type=rss");
        newsreader.setIndicator("News");
        newsreader.setContent(newsread);
        
        
        TabSpec politicsreader = tabs.newTabSpec("Topics");
        politicsreader.setIndicator("Topics");
        Intent politicsread = new Intent(this,AndroidRSSTab2.class);
        politicsread.putExtra("Url"," http://wpfdc.org/index.php?option=com_content&view=category&layout=blog&id=41&Itemid=92&lang=en&format=feed&type=rss");
        politicsreader.setContent(politicsread);
        
        TabSpec timereader = tabs.newTabSpec("Timetable");
        timereader.setIndicator("Timetable");
        Intent timeintent = new Intent(this,TimetableMain.class);
        timereader.setContent(timeintent);
        
        
        tabs.addTab(newsreader);
        tabs.addTab(politicsreader);
        tabs.addTab(timereader);
        
	}
}
