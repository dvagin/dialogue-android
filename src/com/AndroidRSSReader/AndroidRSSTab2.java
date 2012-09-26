package com.AndroidRSSReader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AndroidRSSTab2 extends Activity {

	public static RssItem selectedRssItem = null;
	String feedUrl = "";
	ListView rssListView = null;
	ArrayList<String> rssItems = new ArrayList<String>();
	ArrayAdapter<String> aa = null;
	ProgressDialog dialog;

	public static final int RssItemDialog = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dialog = new ProgressDialog(this);
		dialog.setMessage("Fetching RSS");
		// get textview from our layout.xml
		//final TextView rssURLTV = (TextView) findViewById(R.id.rssURL);
		//rssURLTV.setText(getIntent().getStringExtra("Url"));
		feedUrl = getIntent().getStringExtra("Url");
		// get button from layout.xml
		Button fetchRss = (Button) findViewById(R.id.fetchRss);

		// define the action that will be executed when the button is clicked.
		fetchRss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// feedUrl = rssURLTV.getText().toString();
				//feedUrl = rssURLTV.getText().toString();
				refressRssList();
			}
		});

		// get the listview from layout.xml
		rssListView = (ListView) findViewById(R.id.rssListView);
		// here we specify what to execute when individual list items clicked
		rssListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int index,
					long arg3) {

				// we call the other activity that shows a single rss item in
				// one page
				Intent intent = new Intent(AndroidRSSTab2.this,TopicDisplayer.class);
				startActivity(intent);
			}
		});

		aa = new ArrayAdapter<String>(this, R.layout.list_item, rssItems);
		rssListView.setAdapter(aa);

		refressRssList();
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		switch (id) {
		case RssItemDialog: {
			LayoutInflater li = LayoutInflater.from(this);
			View rssDetails = li.inflate(R.layout.rss_details, null);

			AlertDialog.Builder rssDialog = new AlertDialog.Builder(this);
			rssDialog.setTitle("Rss Item");
			rssDialog.setView(rssDetails);

			return rssDialog.create();
		}
		}

		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		Log.d("taaaaaag","on prepare");
		// TODO Auto-generated method stub
		switch (id) {
		case RssItemDialog: {
			AlertDialog rssDialog = (AlertDialog) dialog;

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
			rssDialog.setTitle(selectedRssItem.getTitle() + " : "
					+ sdf.format(selectedRssItem.getPubDate()));

			String text = selectedRssItem.getDescription() + " : "
					+ selectedRssItem.getLink();
			TextView tv = (TextView) rssDialog
					.findViewById(R.id.rssDetailsTextView);
			tv.setText(text);
		
			//wv.loadData(text, "text/html", null);
			//wv.loadUrl("http://www.wpfdc.org/index.php?format=feed&type=rss&lang=en");
		}
		}
	}

	private void refressRssList() {
		Thread thread = new Thread(){
			@Override
			public void run(){
				//final ArrayList<RssItem> newItems = RssItem.getRssItems(feedUrl);
				AndroidRSSTab2.this.runOnUiThread(new Runnable(){
					@Override
					public void run(){
						dialog.dismiss();
						rssItems.clear();
						rssItems.add("Politics");
						rssItems.add("Economics");
						rssItems.add("Society");
						rssItems.add("Events");
						//rssItems.addAll(newItems);

						aa.notifyDataSetChanged();
					}
				});
			}
		};
		thread.start();
		dialog.show();


	}

}