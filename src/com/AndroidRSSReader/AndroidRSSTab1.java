package com.AndroidRSSReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AndroidRSSTab1 extends Activity {

	public static RssItem selectedRssItem = null;
	String feedUrl = "";
	ListView rssListView = null;
	ArrayList<RssItem> rssItems = new ArrayList<RssItem>();
	ArrayAdapter<RssItem> aa = null;
	ProgressDialog dialog;

	public static final int RssItemDialog = 1;
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	        	 HttpGet httpRequest = null;

	             httpRequest = new HttpGet(urldisplay);
	                

				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
				
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
				InputStream instream = bufHttpEntity.getContent();
	            mIcon11 = BitmapFactory.decodeStream(instream);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	    		bmImage.setImageBitmap(result);
	    }
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dialog = new ProgressDialog(this);
		dialog.setMessage("Fetching RSS");
		dialog.setCancelable(false);
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
				if(InternetReader.checkConnection(getBaseContext())){
					refressRssList();
				}else{
					Toast.makeText(getApplicationContext(), "There are no internet connection", 1000).show();
				}
			}
		});

		// get the listview from layout.xml
		rssListView = (ListView) findViewById(R.id.rssListView);
		// here we specify what to execute when individual list items clicked
		rssListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int index,
					long arg3) {
				selectedRssItem = rssItems.get(index);

				// we call the other activity that shows a single rss item in
				// one page
				Intent intent = new Intent(
						"com.AndroidRSSReader.displayRssItem");
				startActivity(intent);
			}
		});

		aa = new ArrayAdapter<RssItem>(this, R.layout.list_item, rssItems){
			@Override
			public View getView(int position, View convertView, ViewGroup parent){
				//Html.fromHtml
				if (convertView == null) {
					LayoutInflater inflater = AndroidRSSTab1.this.getLayoutInflater();
					convertView = inflater.inflate(R.layout.list_item, null, true);
					TextView label = (TextView) convertView.findViewById(R.id.labelview);
					RssItem item = rssItems.get(position);
					label.setText(Html.fromHtml(item.toString()));
					ImageView im = (ImageView) convertView.findViewById(R.id.logo);
					new DownloadImageTask(im).execute(rssItems.get(position).getSrc());
				}
				return convertView;
			}
		};
		rssListView.setAdapter(aa);
		if(InternetReader.checkConnection(getBaseContext())){
			refressRssList();
		}else{
			Toast.makeText(getApplicationContext(), "There are no internet connection", 1000).show();
		}
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
				final ArrayList<RssItem> newItems = RssItem.getRssItems(feedUrl);
				AndroidRSSTab1.this.runOnUiThread(new Runnable(){
					@Override
					public void run(){
						dialog.dismiss();
						rssItems.clear();
						rssItems.addAll(newItems);

						aa.notifyDataSetChanged();
					}
				});
			}
		};
		thread.start();
		dialog.show();


	}
	

}