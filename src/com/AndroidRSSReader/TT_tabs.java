package com.AndroidRSSReader;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TT_tabs extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);
        
        TabHost tabs = getTabHost();
        TabSpec day1 = tabs.newTabSpec("D1");
        Intent day1in = new Intent(this,TTList.class);
        day1.setIndicator("D1");
        day1.setContent(day1in);
        TabSpec day2 = tabs.newTabSpec("D2");
        Intent day2in = new Intent(this,TTList.class);
        day2.setIndicator("D2");
        day2.setContent(day2in);
        TabSpec day3 = tabs.newTabSpec("D3");
        Intent day3in = new Intent(this,TTList.class);
        day3.setIndicator("D3");
        day3.setContent(day3in);
        TabSpec day4 = tabs.newTabSpec("D4");
        Intent day4in = new Intent(this,TTList.class);
        day4.setIndicator("D4");
        day4.setContent(day4in);
        TabSpec day5 = tabs.newTabSpec("D5");
        Intent day5in = new Intent(this,TTList.class);
        day5.setIndicator("D5");
        day5.setContent(day5in);
        TabSpec day6 = tabs.newTabSpec("D6");
        Intent day6in = new Intent(this,TTList.class);
        day6.setIndicator("D6");
        day6.setContent(day6in);
        
        tabs.addTab(day1);
        tabs.addTab(day2);
        tabs.addTab(day3);
        tabs.addTab(day4);
        tabs.addTab(day5);
        tabs.addTab(day6);
	}

}
