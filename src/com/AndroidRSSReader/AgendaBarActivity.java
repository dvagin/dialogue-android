package com.AndroidRSSReader;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;





public class AgendaBarActivity extends Activity {
	
	private ViewFlow viewFlow;
	private ProgressDialog dialog;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.agendatabbar);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Updating timetable");
        if(this.getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0).getString("agenda_cache", "").equals("")){
        	if(InternetReader.checkConnection(this)){
        		InternetReader reader = new InternetReader(){
        			public void reqFinished(String answer){
        				Editor edit = getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0).edit();
        				edit.putString("agenda_cache", answer);
        				edit.commit();
        				AgendaBarActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								dialog.dismiss();
								viewFlow = (ViewFlow) findViewById(R.id.viewflow);
				                AgendaBarTab adapter = new AgendaBarTab(AgendaBarActivity.this,"");
				                viewFlow.setAdapter(adapter,0);
				                TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
				        		indicator.setTitleProvider(adapter);
				        		viewFlow.setFlowIndicator(indicator);
				        		indicator.setBackgroundColor(Color.BLACK);
				        		indicator.setTextColor(Color.GRAY);
				        		indicator.setHintTextColor(Color.BLUE);
							}
        					
        				});
        			}
        		};
        		dialog.show();
        		reader.setAsyncRequest("", "http://www.rocknglory.ru/1.xml");
        	}
        }else{
        	if(InternetReader.checkConnection(this)){
        		InternetReader reader = new InternetReader(){
        			public void reqFinished(String answer){
        				Editor edit = getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0).edit();
        				edit.putString("agenda_cache", answer);
        				edit.commit();
        				AgendaBarActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								dialog.dismiss();
								viewFlow = (ViewFlow) findViewById(R.id.viewflow);
				                AgendaBarTab adapter = new AgendaBarTab(AgendaBarActivity.this,"");
				                viewFlow.setAdapter(adapter,0);
				                TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
				        		indicator.setTitleProvider(adapter);
				        		viewFlow.setFlowIndicator(indicator);
				        		indicator.setBackgroundColor(Color.BLACK);
				        		indicator.setTextColor(Color.GRAY);
				        		indicator.setHintTextColor(Color.BLUE);
							}
        					
        				});
        			}
        		};
        		reader.setAsyncRequest("", "http://www.rocknglory.ru/1.xml");
        		dialog.show();
        	}else{
        		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
                AgendaBarTab adapter = new AgendaBarTab(this,"");
                viewFlow.setAdapter(adapter,0);
                TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
        		indicator.setTitleProvider(adapter);
        		viewFlow.setFlowIndicator(indicator);
        		indicator.setBackgroundColor(Color.BLACK);
        		indicator.setTextColor(Color.GRAY);
        		indicator.setHintTextColor(Color.BLUE);
        	}
        }
        
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		viewFlow.onConfigurationChanged(newConfig);
	}
}
