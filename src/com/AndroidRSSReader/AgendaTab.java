package com.AndroidRSSReader;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.AndroidRSSReader.InternetReader.Links;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class AgendaTab extends Activity {
	
	public class DateItem{
		public String DateName;
		public String Value;
		public DateItem(String Value,String DateName){
			this.Value = Value;
			this.DateName = DateName;
		}
	}
	
	public class ExpListAdapter extends BaseExpandableListAdapter {

	    private ArrayList<ArrayList<DateItem>> mGroups;
	    private Context mContext;
	  
	    public ExpListAdapter (Context context,ArrayList<ArrayList<DateItem>> groups){
	        mContext = context;
	        mGroups = groups;
	    }
	    
	    @Override
	    public int getGroupCount() {
	        return mGroups.size();
	    }

	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return mGroups.get(groupPosition).size();
	    }

	    @Override
	    public Object getGroup(int groupPosition) {
	        return mGroups.get(groupPosition);
	    }

	    @Override
	    public Object getChild(int groupPosition, int childPosition) {
	        return mGroups.get(groupPosition).get(childPosition);
	    }

	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }

	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }

	    @Override
	    public boolean hasStableIds() {
	        return true;
	    }

	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
	                             ViewGroup parent) {

	        if (convertView == null) {
	            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.group_view, null);
	        }

	        if (isExpanded){
	           //Изменяем что-нибудь, если текущая Group раскрыта
	        }
	        else{
	            //Изменяем что-нибудь, если текущая Group скрыта
	        }

	        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
	        textGroup.setText(mGroups.get(groupPosition).get(0).DateName);

	        return convertView;

	    }

	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
	                             View convertView, ViewGroup parent) {
	        if (convertView == null) {
	            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.group_view, null);
	        }

	        TextView textChild = (TextView) convertView.findViewById(R.id.textGroup);
	        textChild.setText(mGroups.get(groupPosition).get(childPosition).Value);

	        return convertView;
	    }

	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agendatab);
		ExpandableListView listView = (ExpandableListView)findViewById(R.id.expandableListView1);
		refresh();
		
		ArrayList<ArrayList<DateItem>> groups = new ArrayList<ArrayList<DateItem>>();
        ArrayList<DateItem> children1 = new ArrayList<DateItem>();
        ArrayList<DateItem> children2 = new ArrayList<DateItem>();
        ArrayList<DateItem> children3 = new ArrayList<DateItem>();
        ArrayList<DateItem> children4 = new ArrayList<DateItem>();
        children1.add(new DateItem("9:00-13:00 Opening plenary session - \"Socio-political transformation: chance or challenge for dialogue?\"","October 4"));
        children1.add(new DateItem("15:00-19:00 Thematic plenary session 1 - \"Global Peace and Justice\"","October 4"));
        groups.add(children1);
        children2.add(new DateItem("9:00-13:30 Thematic plenary session 2 - \"Visions of a New Earth: Responding to the Ecological Challenge\"","October 5"));
        /*children2.add("Child_2");
        children2.add("Child_3");*/
        groups.add(children2);     
        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);
        
        
		
	}
	
	public void refresh(){
		InternetReader read = new InternetReader(){
			public void reqFinished(String answer){
				Log.d("tag",answer);
				Document doc = InternetReader.getDomElement(answer);
				   NodeList nl = doc.getElementsByTagName("Hall");
				   NodeList nlc = nl.item(0).getChildNodes();
				   /*NodeList nlcl = nlc.item(1).getChildNodes();*/
				   Log.d("tag","length "+nlc.getLength());
				   for(int i = 0;i<nlc.getLength();i++){
					   Log.d("ekv","name "+nlc.item(i).getNodeName()/*+" value "+InternetReader.getValue((Element)nl.item(0),nlc.item(i).getNodeName())*/);
				   }
			}
		};
		if(InternetReader.checkConnection(getBaseContext())){
			read.setAsyncRequest("", Links.LINKONE);
		}
	}

}
