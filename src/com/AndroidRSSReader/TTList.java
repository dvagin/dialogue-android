package com.AndroidRSSReader;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TTList extends Activity {
	ListView rssListView = null;
	ArrayList<String> rssItems = new ArrayList<String>();
	ArrayAdapter<String> aa = null;
	@Override
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tt_list);
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        rssItems.add("13:00-14:00 Lobby one \n\n Meeting");
        
        rssListView = (ListView) findViewById(R.id.listView1);
        
        aa = new ArrayAdapter<String>(this, R.layout.list_item, rssItems){
			@Override
			public View getView(int position, View convertView, ViewGroup parent){
				if (convertView == null) {
					LayoutInflater inflater = TTList.this.getLayoutInflater();
					convertView = inflater.inflate(R.layout.list_item, null, true);
					TextView label = (TextView) convertView.findViewById(R.id.labelview);
					String item = rssItems.get(position);
					label.setText(item);
				}
				return convertView;
			}
        };
        
        rssListView.setAdapter(aa);
        
	}
}
