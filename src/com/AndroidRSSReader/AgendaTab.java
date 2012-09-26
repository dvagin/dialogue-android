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
	
	protected ArrayList<ArrayList<DateItem>> groups;
	protected ExpListAdapter adapter;
	ExpandableListView listView;
	
	public int hallNum;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agendatab);
		listView = (ExpandableListView)findViewById(R.id.expandableListView1);
		refresh();
		hallNum = getIntent().getExtras().getInt("HallNum");
		groups = new ArrayList<ArrayList<DateItem>>();
        ArrayList<DateItem> children1 = new ArrayList<DateItem>();
        ArrayList<DateItem> children2 = new ArrayList<DateItem>();
        children1.add(new DateItem("9:00-13:00 Opening plenary session - \"Socio-political transformation: chance or challenge for dialogue?\"","October 4"));
        children1.add(new DateItem("15:00-19:00 Thematic plenary session 1 - \"Global Peace and Justice\"","October 4"));
        groups.add(children1);
        children2.add(new DateItem("9:00-13:30 Thematic plenary session 2 - \"Visions of a New Earth: Responding to the Ecological Challenge\"","October 5"));
        /*children2.add("Child_2");
        children2.add("Child_3");*/
        groups.add(children2);     
        adapter = new ExpListAdapter(getApplicationContext(), groups);
        
        listView.setAdapter(adapter);
        
        //adapter.notifyDataSetChanged();
        
        
		
	}
	
	public void refresh(){
		InternetReader read = new InternetReader(){
			public void reqFinished(String answer){
				Log.d("tag",answer);
				ArrayList<ArrayList<DateItem>> newgroups = new ArrayList<ArrayList<DateItem>>();
				Document doc = InternetReader.getDomElement(answer);
				doc.getDocumentElement().normalize();
				   NodeList nl = doc.getElementsByTagName("Hall");
				   NodeList nlc = nl.item(hallNum-1).getChildNodes();
				   /*NodeList nlcl = nlc.item(1).getChildNodes();*/
				   Log.d("tag","length "+nlc.getLength());
				   for(int i = 0;i<nlc.getLength();i++){
					   if(!nlc.item(i).getNodeName().equals("#text")){
						   NodeList inDate = nlc.item(i).getChildNodes();
						   Log.d("indate","elems length "+inDate.getLength());
						   ArrayList<DateItem> buf = new ArrayList<DateItem>();
						   for(int j = 0; j<inDate.getLength();++j){
							   if(!inDate.item(j).getNodeName().equals("#text")){
								   buf.add(new DateItem(inDate.item(j).getTextContent(),((Element)nlc.item(i)).getAttribute("value")));
								   Log.d("indate","name "+inDate.item(j).getNodeName() + " " + ((Element)nlc.item(i)).getAttribute("value") + " val " + inDate.item(j).getTextContent()); 
							   }
						   }
						   if(buf.size()==0){
							   buf.add(new DateItem("There are no tables and no sessions.",((Element)nlc.item(i)).getAttribute("value")));
						   }
						   newgroups.add(buf);
					   }
				   }
				   Log.d("tag","finished " + newgroups.size());
				   groups.clear();
				   groups.addAll(newgroups);
				   AgendaTab.this.runOnUiThread(new Runnable(){
					  public void run(){
						  //listView.invalidate();
						  adapter.notifyDataSetChanged();
					  }
				   });
				   
			}
		};
		if(InternetReader.checkConnection(getBaseContext())){
			read.setAsyncRequest("", Links.LINKONE);
		}
	}

}
