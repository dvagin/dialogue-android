package com.AndroidRSSReader;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.AndroidRSSReader.InternetReader.Links;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.TextView;

public class AgendaTab extends Activity implements ExpandableListView.OnChildClickListener {
	
	public class DateItem{
		public String DateName;
		public String Value;
		public String link;
		public DateItem(String Value,String DateName,String link){
			this.Value = Value;
			this.DateName = DateName;
			this.link = link;
		}
	}
	
	public class ExpListAdapter extends BaseExpandableListAdapter {

	    private ArrayList<ArrayList<DateItem>> mGroups;
	    private Context mContext;
	    private int lastGroup = -1;
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
	        //Log.d("tag","clicked "+isExpanded);
	        if (isExpanded){
	        	
	           //Изменяем что-нибудь, если текущая Group раскрыта
	           convertView.setBackgroundColor(0xFFC0D999);
	        }
	        else{
	        	GradientDrawable gd = new GradientDrawable(
			            GradientDrawable.Orientation.TOP_BOTTOM,new int[] {0xFF4DB2E0,0xFF2D99C9}
			            );
			    gd.setCornerRadius(0f);
			    convertView.setBackgroundDrawable(gd);
	            //Изменяем что-нибудь, если текущая Group скрыта
	        }

	        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
	        textGroup.setTextColor(Color.WHITE);
	        textGroup.setText(mGroups.get(groupPosition).get(0).DateName);
	       
	        return convertView;

	    }

	    @Override
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
	                             View convertView, ViewGroup parent) {
	        if (convertView == null) {
	            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = inflater.inflate(R.layout.child_view, null);
	        }

	        TextView textChild = (TextView) convertView.findViewById(R.id.textGroup);
	        textChild.setText(Html.fromHtml(mGroups.get(groupPosition).get(childPosition).Value));

	        return convertView;
	    }

	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }


	    
	    @Override
	    public void onGroupExpanded(int groupPosition){
	        //collapse the old expanded group, if not the same
	        //as new group to expand
	        if(groupPosition != lastGroup){
	        	listView.collapseGroup(lastGroup);
	        }
	        

	        super.onGroupExpanded(groupPosition);           
	        lastGroup = groupPosition;
	    }

	    
	};
	
	protected ArrayList<ArrayList<DateItem>> groups;
	protected ExpListAdapter adapter;
	ExpandableListView listView;
	ProgressDialog dialog;
	private int selectedGroup=-1;
	
	public int hallNum;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agendatab);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Refreshing timetable...");
		dialog.setCancelable(false);
		listView = (ExpandableListView)findViewById(R.id.expandableListView1);
		listView.setOnChildClickListener(this);


		hallNum = getIntent().getExtras().getInt("HallNum");
		groups = new ArrayList<ArrayList<DateItem>>();

        /*ArrayList<DateItem> children1 = new ArrayList<DateItem>();
        ArrayList<DateItem> children2 = new ArrayList<DateItem>();
        children1.add(new DateItem("9:00-13:00 Opening plenary session - \"Socio-political transformation: chance or challenge for dialogue?\"","October 4"));
        children1.add(new DateItem("15:00-19:00 Thematic plenary session 1 - \"Global Peace and Justice\"","October 4"));
        groups.add(children1);
        children2.add(new DateItem("9:00-13:30 Thematic plenary session 2 - \"Visions of a New Earth: Responding to the Ecological Challenge\"","October 5"));*/
        /*children2.add("Child_2");
        children2.add("Child_3");*/
        /*groups.add(children2);*/     
        adapter = new ExpListAdapter(getApplicationContext(), groups);
        
        listView.setAdapter(adapter);
		refresh();
		listView.setOnGroupCollapseListener(new OnGroupCollapseListener(){

			@Override
			public void onGroupCollapse(int groupPosition) {
				//listView.expandGroup(groupPosition);
			}
			
		});
        //adapter.notifyDataSetChanged();
        
        
		
	}
	
	
	public void refreshAdapter(final String answer){
		ArrayList<ArrayList<DateItem>> newgroups = new ArrayList<ArrayList<DateItem>>();
		Document doc = InternetReader.getDomElement(answer);
		   NodeList nl = doc.getElementsByTagName("Hall");
		   Log.d("refresh","hall Num "+hallNum);
		   NodeList nlc = nl.item(hallNum-1).getChildNodes();
		   Log.d("refresh",answer);
		   /*NodeList nlcl = nlc.item(1).getChildNodes();*/
		   Log.d("tag","length "+nlc.getLength());
		   for(int i = 0;i<nlc.getLength();i++){
			   if(!nlc.item(i).getNodeName().equals("#text")){
				   NodeList inDate = nlc.item(i).getChildNodes();
				   Log.d("indate","elems length "+inDate.getLength());
				   ArrayList<DateItem> buf = new ArrayList<DateItem>();
				   for(int j = 0; j<inDate.getLength();++j){
					   if(!inDate.item(j).getNodeName().equals("#text")){
						   buf.add(new DateItem(inDate.item(j).getTextContent(),((Element)nlc.item(i)).getAttribute("value"),((Element)inDate.item(j)).getAttribute("id")));
						   Log.d("indate","name "+inDate.item(j).getNodeName() + " " + ((Element)nlc.item(i)).getAttribute("value") + " val " + inDate.item(j).getTextContent() +" id "+((Element)inDate.item(j)).getAttribute("id")); 
					   }
				   }
				   if(buf.size()==0){
					   buf.add(new DateItem("There are no tables and no sessions.",((Element)nlc.item(i)).getAttribute("value"),""));
					   buf.add(new DateItem("   ",((Element)nlc.item(i)).getAttribute("value"),""));
				   }else if(buf.size()==1){
					   buf.add(new DateItem("   ",((Element)nlc.item(i)).getAttribute("value"),""));
				   }
				   newgroups.add(buf);
			   }
		   }
		   Log.d("tag","finished " + newgroups.size());
		   groups.clear();
		   groups.addAll(newgroups);
	}
	
	public void refresh(){
		InternetReader read = new InternetReader(){
			public void reqFinished(String answer){

				SharedPreferences settings = AgendaTab.this.getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0);
				Editor edit = settings.edit();
				edit.putString("agenda_cache", answer);
				edit.commit();
				refreshAdapter(answer);
				   AgendaTab.this.runOnUiThread(new Runnable(){
					  public void run(){
						  adapter.notifyDataSetChanged();
						  dialog.dismiss();
						  listView.expandGroup(0);
					  }
				   });
				   
			}
		};
		SharedPreferences settings = AgendaTab.this.getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0);
		//if(settings.getString("agenda_cache", "").equals("")){
			if(InternetReader.checkConnection(getBaseContext())){
				read.setAsyncRequest("", Links.LINKONE);
				dialog.show();
			}else{
				dialog.show();
				refreshAdapter(settings.getString("agenda_cache", ""));
				adapter.notifyDataSetChanged();
				dialog.dismiss();

			}
		/*}else{
			Log.d("agenda",settings.getString("agenda_cache", ""));
			refreshAdapter(settings.getString("agenda_cache", ""));
		}*/

	}


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Intent start = new Intent(this,WebShower.class);
		if(!groups.get(groupPosition).get(childPosition).link.equals("")){
			start.putExtra("url", groups.get(groupPosition).get(childPosition).link);
		//Log.d("tag",groups.get(groupPosition).get(childPosition).link);
		startActivity(start);
		}
		return false;
	}
	
	

}
