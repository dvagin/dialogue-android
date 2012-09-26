package com.AndroidRSSReader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class TimetableMain extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable_main);
		
		final Button agenda = (Button)findViewById(R.id.button1);
		agenda.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent start = new Intent(TimetableMain.this,AgendaActivity.class);
				TimetableMain.this.startActivity(start);
			}
			
		});
		
		
		
		final Button themes = (Button)findViewById(R.id.button2);
		themes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent start = new Intent(TimetableMain.this,TT_tabs.class);
				TimetableMain.this.startActivity(start);
			}
			
		});
	}

}
