package com.AndroidRSSReader;



import java.util.ArrayList;

import org.taptwo.android.widget.TitleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.AndroidRSSReader.AgendaTab.DateItem;
import com.AndroidRSSReader.AgendaTab.ExpListAdapter;
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
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.TextView;

public class AgendaBarTab extends BaseAdapter implements TitleProvider {
	
	ExpandableListView listView;
	
	public class DateItem{
		public String DateName;
		public String Value;
		public String link;
		public String time;
		public DateItem(String Value,String DateName,String link,String time){
			this.Value = Value;
			this.DateName = DateName;
			this.link = link;
			this.time = time;
		}
	}
	
	public class ExpListAdapter extends BaseExpandableListAdapter implements OnChildClickListener, OnGroupCollapseListener,OnGroupClickListener {

		public ExpandableListView exp;
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
	            convertView = inflater.inflate(R.layout.group_view, null);
	        }

	        TextView textChild = (TextView) convertView.findViewById(R.id.textGroup);
	        textChild.setText(Html.fromHtml("<p style='color:green;'>"+mGroups.get(groupPosition).get(childPosition).time+"</p>"+mGroups.get(groupPosition).get(childPosition).Value));
	        Log.d("time","time is "+mGroups.get(groupPosition).get(childPosition).time);
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
	        	exp.collapseGroup(lastGroup);
	        }
	        //Log.d("group","group pos "+groupPosition +" prev group "+lastGroup);
	        //this.onGroupCollapse(groupPosition);
	        super.onGroupExpanded(groupPosition);           
	        lastGroup = groupPosition;
	    }

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			if(!mGroups.get(groupPosition).get(childPosition).link.equals("")){
				Intent start = new Intent(mContext,WebShower.class);
				start.putExtra("Url", mGroups.get(groupPosition).get(childPosition).link);
				activity.startActivity(start);
			}
			return false;
		}

		@Override
		public void onGroupCollapse(int groupPosition) {
			if(groupPosition==lastGroup)
				exp.expandGroup(groupPosition);
			
		}

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			int count =  this.getGroupCount();
            for (int i = 0; i <count ; i++)
              if(groupPosition!=i)
                exp.collapseGroup(i);
              else
                  exp.expandGroup(i);
            return false;
		}

	    
	};
	
	
	
	private LayoutInflater mInflater;
	
	private int hallNum = 1;
	
	protected ArrayList<ArrayList<DateItem>> groups;
	protected ExpListAdapter adapter;
	
	ProgressDialog dialog;
	private int selectedGroup=-1;
	private Context mContext;
	private Activity activity;
	private String answer="empty";
	
	private static final String[] names = {"Olympic Hall","Karphatos hall","TV Room","Leros hall","Kassos hall" };
	
	private static ArrayList<ArrayList<ArrayList<DateItem>>> groups_arr;
	
	public AgendaBarTab(Context context,String answer) {
		activity = (Activity)context;
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		dialog = new ProgressDialog(activity);
		dialog.setMessage("Refreshing timetable...");
		dialog.setCancelable(false);
		groups = new ArrayList<ArrayList<DateItem>>();
		groups_arr = new ArrayList<ArrayList<ArrayList<DateItem>>>();
		for(int i = 0; i<5;++i){
			groups_arr.add(refreshAdapter(mContext.getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0).getString("agenda_cache", ""),i));
		}
		//refresh();
		Log.d("groups","size is "+ groups_arr.size());
		
		
}
	
	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position; 
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.agendatab, null);
		}
		//((TextView) convertView.findViewById(R.id.textLabel)).setText(versions[position]);
		//ArrayList<DateItem> objs1 = new ArrayList<DateItem>();
		/*objs1.add(new DateItem("asdsad", "asdasdas", "asdasdasd"));
		groups.add(objs1);*/
		adapter = new ExpListAdapter(mContext, groups_arr.get(position));
		
		listView = (ExpandableListView) convertView.findViewById(R.id.expandableListView1);
		listView.setOnChildClickListener(adapter);
		
		adapter.exp = listView;
		//listView.setOnGroupExpandListener(adapter);

		activity.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				listView.setAdapter(adapter);
				hallNum = position;
				//refreshAdapter(answer);
				  adapter.notifyDataSetChanged();
				  listView.expandGroup(0);
				  //listView.setOnGroupClickListener(adapter);
				  //listView.setOnGroupCollapseListener(adapter);
				  //listView.setOnGroupClickListener(onGroupClickListener)
			}
			
		});



		//Log.d("answer",answer);
		return convertView;
	}

	/* (non-Javadoc)
	 * @see org.taptwo.android.widget.TitleProvider#getTitle(int)
	 */
	@Override
	public String getTitle(int position) {
		return names[position];
	}
	
	
	public ArrayList<ArrayList<DateItem>> refreshAdapter(final String answer,int hallNum){
		ArrayList<ArrayList<DateItem>> newgroups = new ArrayList<ArrayList<DateItem>>();
		Document doc = InternetReader.getDomElement(answer);
		   NodeList nl = doc.getElementsByTagName("Hall");
		   //Log.d("refresh","hall Num "+hallNum);
		   NodeList nlc = nl.item(hallNum).getChildNodes();
		   //Log.d("refresh",answer);
		   /*NodeList nlcl = nlc.item(1).getChildNodes();*/
		   //Log.d("tag","length "+nlc.getLength());
		   for(int i = 0;i<nlc.getLength();i++){
			   if(!nlc.item(i).getNodeName().equals("#text")){
				   NodeList inDate = nlc.item(i).getChildNodes();
				  // Log.d("indate","elems length "+inDate.getLength());
				   ArrayList<DateItem> buf = new ArrayList<DateItem>();
				   for(int j = 0; j<inDate.getLength();++j){
					   if(!inDate.item(j).getNodeName().equals("#text")){
						   if(inDate.item(j).getTextContent().length()<46){
							   buf.add(new DateItem(inDate.item(j).getTextContent()+"<br>",((Element)nlc.item(i)).getAttribute("value"),((Element)inDate.item(j)).getAttribute("id"),((Element)inDate.item(j)).getAttribute("Time")));
						   }else{
							   buf.add(new DateItem(inDate.item(j).getTextContent(),((Element)nlc.item(i)).getAttribute("value"),((Element)inDate.item(j)).getAttribute("id"),((Element)inDate.item(j)).getAttribute("Time")));
						   }
						   
						   
						   // Log.d("indate","name "+inDate.item(j).getNodeName() + " " + ((Element)nlc.item(i)).getAttribute("value") + " val " + inDate.item(j).getTextContent() +" id "+((Element)inDate.item(j)).getAttribute("id")); 
					   }
				   }
				   if(buf.size()==0){
					   buf.add(new DateItem("<br>There are no tables and no sessions.<br><br>",((Element)nlc.item(i)).getAttribute("value"),"",""));
					   buf.add(new DateItem("<br><br><br>   ",((Element)nlc.item(i)).getAttribute("value"),"",""));
				   }else if(buf.size()==1){
					   if(buf.get(0).Value.length()<88&&buf.get(0).Value.length()>41){
						   Log.d("buf1","buf 1 is "+buf.get(0).Value +"l iss  " + buf.get(0).Value.length());
						   buf.add(new DateItem("<br><br><br>   ",((Element)nlc.item(i)).getAttribute("value"),"",""));
					   }else if(buf.get(0).Value.length()<40){
						   buf.add(new DateItem("<br><br><br>   ",((Element)nlc.item(i)).getAttribute("value"),"",""));
					   }else{
						   buf.add(new DateItem("<br><br>   ",((Element)nlc.item(i)).getAttribute("value"),"",""));
					   }
				   }
				   newgroups.add(buf);
			   }
		   }
		  // Log.d("tag","finished " + newgroups.size());
		  /* groups.clear();
		   groups.addAll(newgroups);*/
		   
		   return newgroups;
	}
	
	
	public void refresh(){
		InternetReader read = new InternetReader(){
			public void reqFinished(String answer){

				SharedPreferences settings = mContext.getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0);
				Editor edit = settings.edit();
				edit.putString("agenda_cache", answer);
				edit.commit();
				AgendaBarTab.this.answer = answer;
				dialog.dismiss();
				/*refreshAdapter(answer);
					activity.runOnUiThread(new Runnable(){
					  public void run(){
						  adapter.notifyDataSetChanged();
						  dialog.dismiss();
						  listView.expandGroup(0);
					  }
				   });*/
				   
			}
		};
		SharedPreferences settings = mContext.getSharedPreferences(AndroidRSSReader.PREFS_NAME, 0);
		//if(settings.getString("agenda_cache", "").equals("")){
			if(InternetReader.checkConnection(mContext)){
				read.setAsyncRequest("", Links.LINKONE);
				dialog.show();
			}else{
				dialog.show();
				//refreshAdapter(settings.getString("agenda_cache", ""));
				adapter.notifyDataSetChanged();
				dialog.dismiss();

			}
		/*}else{
			Log.d("agenda",settings.getString("agenda_cache", ""));
			refreshAdapter(settings.getString("agenda_cache", ""));
		}*/

	}
}
