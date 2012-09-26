package com.AndroidRSSReader;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AgendaActivity extends TabActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		TabHost tabs = getTabHost();
        TabSpec day1 = tabs.newTabSpec("1");
        Intent day1in = new Intent(this,AgendaTab.class);
        day1.setIndicator("1");
        day1.setContent(day1in);
        TabSpec day2 = tabs.newTabSpec("2");
        Intent day2in = new Intent(this,AgendaTab.class);
        day2.setIndicator("2");
        day2.setContent(day2in);
        TabSpec day3 = tabs.newTabSpec("3");
        Intent day3in = new Intent(this,AgendaTab.class);
        day3.setIndicator("3");
        day3.setContent(day3in);
        TabSpec day4 = tabs.newTabSpec("4");
        Intent day4in = new Intent(this,AgendaTab.class);
        day4.setIndicator("4");
        day4.setContent(day4in);
        TabSpec day5 = tabs.newTabSpec("5");
        Intent day5in = new Intent(this,AgendaTab.class);
        day5.setIndicator("5");
        day5.setContent(day5in);
        TabSpec day6 = tabs.newTabSpec("6");
        Intent day6in = new Intent(this,AgendaTab.class);
        day6.setIndicator("6");
        day6.setContent(day6in);
        
        tabs.addTab(day1);
        tabs.addTab(day2);
        tabs.addTab(day3);
        tabs.addTab(day4);
        tabs.addTab(day5);
        tabs.addTab(day6);
	}

}
