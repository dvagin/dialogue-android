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
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
		setContentView(R.layout.tab2);
		dialog = new ProgressDialog(this);
		dialog.setMessage("Fetching RSS");
		// get textview from our layout.xml
		//final TextView rssURLTV = (TextView) findViewById(R.id.rssURL);
		//rssURLTV.setText(getIntent().getStringExtra("Url"));
		feedUrl = getIntent().getStringExtra("Url");
		// get button from layout.xml
		/*Button fetchRss = (Button) findViewById(R.id.fetchRss);

		// define the action that will be executed when the button is clicked.
		fetchRss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// feedUrl = rssURLTV.getText().toString();
				//feedUrl = rssURLTV.getText().toString();
				refressRssList();
			}
		});*/

		// get the listview from layout.xml
		rssListView = (ListView) findViewById(R.id.rssListView);
		// here we specify what to execute when individual list items clicked
		rssListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int index,
					long arg3) {

				// we call the other activity that shows a single rss item in
				// one page
				Intent intent = new Intent(AndroidRSSTab2.this,AndroidRSSTab1.class);
				switch(index){
				case 0:
					intent.putExtra("Url","http://wpfdc.org/index.php?option=com_content&view=category&layout=blog&id=40&Itemid=93&lang=en&format=feed&type=rss");
					break;
				case 1:
					intent.putExtra("Url","http://wpfdc.org/index.php?option=com_content&view=category&layout=blog&id=41&Itemid=92&lang=en&format=feed&type=rss");
					break;
				case 2:
					intent.putExtra("Url","http://wpfdc.org/index.php?option=com_content&view=category&layout=blog&id=68&Itemid=94&lang=en&format=feed&type=rss");
					break;
				case 3:
					intent.putExtra("Url","http://wpfdc.org/index.php?option=com_content&view=category&layout=blog&id=16&Itemid=95&lang=en&format=feed&type=rss");
					break;
				}
				startActivity(intent);
			}
		});

		aa = new ArrayAdapter<String>(this, R.layout.topics_list_item, rssItems){
			@Override
			public View getView(int position, View convertView, ViewGroup parent){
				if (convertView == null) {
					LayoutInflater inflater = AndroidRSSTab2.this.getLayoutInflater();
					convertView = inflater.inflate(R.layout.topics_list_item, null, true);
					//convertView.setBackgroundColor(Color.BLUE);
					
					//im.setImageDrawable(getResources().getDrawable(R.drawable.economics));
					TextView label = (TextView) convertView.findViewById(R.id.labelview2);
					int[] colors = null;
					switch(position){
					case 0:
						colors = new int[] {0xFF4BABD6,0xFF0087B7};
						break;
					case 1:
						colors = new int[] {0xFF52B6E4,0xFF2795C5};
						break;
					case 2:
						colors = new int[] {0xFF71C8F0,0xFF56AFD7};
						break;
					case 3:
						colors = new int[] {0xFF8CD0F3,0xFF62BDE7};
						break;
					}
					GradientDrawable gd = new GradientDrawable(
				            GradientDrawable.Orientation.TOP_BOTTOM,colors
				            );
				    gd.setCornerRadius(0f);
				    convertView.setBackgroundDrawable(gd);
					String item = rssItems.get(position);
					//SpannableString text = (SpannableString) Html.fromHtml(item);
					ImageSpan is = null;
					switch(position){
					case 0:
						is = new ImageSpan(getContext(),R.drawable.politics);
						break;
					case 1:
						is = new ImageSpan(getContext(),R.drawable.economics);
						break;
					case 2:
						is = new ImageSpan(getContext(),R.drawable.society);
						break;
					case 3:
						is = new ImageSpan(getContext(),R.drawable.flag);
						break;
					}
					
					SpannableString text = new SpannableString("    "+item);
					text.setSpan(is, 0, 1, 0);
					label.setText(text);
				}
				return convertView;
			}
		};
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