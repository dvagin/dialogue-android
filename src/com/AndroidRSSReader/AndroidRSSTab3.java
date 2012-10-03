package com.AndroidRSSReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class AndroidRSSTab3 extends Activity {
	public static RssItem selectedRssItem = null;
	String feedUrl = "";
	ListView rssListView = null;
	ArrayList<String> rssItems = new ArrayList<String>();
	ArrayAdapter<String> aa = null;
	ProgressDialog dialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3);
		rssItems.add("Programme");
		rssItems.add("Halls");
		rssItems.add("Videostream");
		
		rssListView = (ListView) findViewById(R.id.listView1);
		rssListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int index,
					long arg3) {
				switch(index){
				case 0:
					Intent start = new Intent(AndroidRSSTab3.this,AgendaBarActivity.class);
					AndroidRSSTab3.this.startActivity(start);
					break;
				case 1:
					Intent start2 = new Intent(AndroidRSSTab3.this,HallViewActivity.class);
					AndroidRSSTab3.this.startActivity(start2);
					break;
				case 2:
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rhodesforum.org/online/"));
					startActivity(browserIntent);
					break;
				}
			}
		});
		
		
		aa = new ArrayAdapter<String>(this, R.layout.topics_list_item, rssItems){
			@Override
			public View getView(int position, View convertView, ViewGroup parent){
				if (convertView == null) {
					LayoutInflater inflater = AndroidRSSTab3.this.getLayoutInflater();
					convertView = inflater.inflate(R.layout.tab3_list_item, null, true);
					//convertView.setBackgroundColor(Color.BLUE);
					
					//im.setImageDrawable(getResources().getDrawable(R.drawable.economics));
					TextView label = (TextView) convertView.findViewById(R.id.labelview2);
					//ImageSpan is = new ImageSpan(getContext(), R.drawable.economics);
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
						is = new ImageSpan(getContext(),R.drawable.timetable);
						break;
					case 1:
						is = new ImageSpan(getContext(),R.drawable.halls);
						break;
					case 2:
						is = new ImageSpan(getContext(),R.drawable.videostream);
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
		/*final Button agenda = (Button)findViewById(R.id.button1);
		agenda.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent start = new Intent(AndroidRSSTab3.this,AgendaActivity.class);
				AndroidRSSTab3.this.startActivity(start);
			}
			
		});
		
		
		
		final Button themes = (Button)findViewById(R.id.button2);
		themes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent start = new Intent(AndroidRSSTab3.this,TT_tabs.class);
				AndroidRSSTab3.this.startActivity(start);
			}
			
		});
		
		final Button hall_plan = (Button)findViewById(R.id.button3);
		hall_plan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent start = new Intent(AndroidRSSTab3.this,HallViewActivity.class);
				AndroidRSSTab3.this.startActivity(start);
			}
			
		});*/
	}

}
