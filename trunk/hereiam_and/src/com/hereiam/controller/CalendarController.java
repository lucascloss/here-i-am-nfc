package com.hereiam.controller;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.controller.adapter.AlertDialogAdapter;
import com.hereiam.model.Calendar;
import com.hereiam.model.Place;
import com.hereiam.wsi.CalendarWSI;
import com.hereiam.wsi.PlaceWSI;

public class CalendarController extends BaseActivity implements OnClickListener, OnItemClickListener{
	
	private Context context;
	private int userId;
	private String userName;
	private Intent navIntent;
	private TextView showName;
	private LinearLayout linearLayoutName;
	private CalendarWSI calendarWSI;
	private ArrayList<Calendar> calendars = new ArrayList<Calendar>();	
	private ListView listViewResults;
	private ArrayAdapter<String> adapter;
	private String name;
	private String info;
	private int placeId;
	private int environmentId;
	private ArrayList<String> previousAction = new ArrayList<String>();
	private int calendarPlaceId;
	private int calendarEnvironmentId;
	private String calendarPlace;
	private String calendarEnvironment;
	private ArrayList<String> listItens = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		context = this;
		userId = getUserId();
        userName = getUserName();
		listViewResults = (ListView) findViewById(R.id.listView); 
		listViewResults.setOnItemClickListener(this);
		
		if(getIntent().hasExtra("ENVIRONMENT")){
			name = getIntent().getStringExtra("ENVIRONMENT");
			info = getIntent().getStringExtra("INFO");
			
			if(getIntent().hasExtra("LATITUDE_ENVIRONMENT") && getIntent().hasExtra("LONGITUDE_ENVIRONMENT")){
				previousAction.add("SHOWMAP");
				previousAction.add("ENVIRONMENT");
				previousAction.add(getIntent().getStringExtra("ENVIRONMENT_M"));
				previousAction.add("LATITUDE_ENVIRONMENT");
				previousAction.add(getIntent().getStringExtra("LATITUDE_ENVIRONMENT"));
				previousAction.add("LONGITUDE_ENVIRONMENT");
				previousAction.add(getIntent().getStringExtra("LONGITUDE_ENVIRONMENT"));
			}
		}
		
		if(getIntent().hasExtra("PLACE")){
			name = getIntent().getStringExtra("PLACE");
			info = getIntent().getStringExtra("INFO");
			placeId = getIntent().getIntExtra("PLACEID", 0);	
			
			if(getIntent().hasExtra("PLACE_M")){
				previousAction.add("SHOWMAP");
				previousAction.add("PLACE");
				previousAction.add(getIntent().getStringExtra("PLACE_M"));
				previousAction.add("LATITUDE_PLACE");
				previousAction.add(getIntent().getStringExtra("LATITUDE_PLACE"));
				previousAction.add("LONGITUDE_PLACE");
				previousAction.add(getIntent().getStringExtra("LONGITUDE_PLACE"));
				previousAction.add("PLACE_M");
				previousAction.add(getIntent().getStringExtra("PLACE_M"));
			}
			
			if(getIntent().hasExtra("IMPORTANT")){
				previousAction.add("SHOWMAP");
				previousAction.add("IMPORTANT");
				previousAction.add(getIntent().getStringExtra("IMPORTANT"));
				previousAction.add("LATITUDE_IMPORTANT");
				previousAction.add(getIntent().getStringExtra("LATITUDE_IMPORTANT"));
				previousAction.add("LONGITUDE_IMPORTANT");
				previousAction.add(getIntent().getStringExtra("LONGITUDE_IMPORTANT"));
			}
			
			if(getIntent().hasExtra("FAVORITE")){
				previousAction.add("SHOWMAP");
				previousAction.add("FAVORITE");
				previousAction.add(getIntent().getStringExtra("FAVORITE"));
				previousAction.add("LATITUDE_FAVORITE");
				previousAction.add(getIntent().getStringExtra("LATITUDE_FAVORITE"));
				previousAction.add("LONGITUDE_FAVORITE");
				previousAction.add(getIntent().getStringExtra("LONGITUDE_FAVORITE"));
			}
		}
		
		if(getIntent().hasExtra("ROUTE")){
			previousAction.add("ROUTE");
			previousAction.add("PLACE_A");
			previousAction.add(getIntent().getStringExtra("PLACE_A"));
			previousAction.add("LATITUDE_A");
			previousAction.add(getIntent().getStringExtra("LATITUDE_A"));
			previousAction.add("LONGITUDE_A");
			previousAction.add(getIntent().getStringExtra("LONGITUDE_A"));        	
			previousAction.add("PLACE_B");
			previousAction.add(getIntent().getStringExtra("PLACE_B"));
			previousAction.add("LATITUDE_B");
			previousAction.add(getIntent().getStringExtra("LATITUDE_B"));
			previousAction.add("LONGITUDE_B");
			previousAction.add(getIntent().getStringExtra("LONGITUDE_B"));   
		}
		
		
		if(getIntent().hasExtra("CALENDAR_ENVIRONMENT")){
			calendarEnvironment = getIntent().getStringExtra("CALENDAR_ENVIRONMENT");
			calendarEnvironmentId = getIntent().getIntExtra("CALENDAR_ENVIRONMENT_ID", 0);
			getReferences(calendarEnvironment);
			startProgressDialog(getString(R.string.progresst_loading_calendar), getString(R.string.progressm_loading_environment_calendar));
			new GetEnvironmentCalendarFeedTask().execute();
		}
		
		if(getIntent().hasExtra("CALENDAR_PLACE")){
			calendarPlace = getIntent().getStringExtra("CALENDAR_PLACE");
			calendarPlaceId = getIntent().getIntExtra("CALENDAR_PLACE_ID", 0);
			getReferences(calendarPlace);
			startProgressDialog(getString(R.string.progresst_loading_calendar), getString(R.string.progressm_loading_place_calendar));
			new GetPlaceCalendarFeedTask().execute();
		}
	}

	public void getReferences(String name){
		showName = (TextView) findViewById(R.id.info_txt_name);
		
		linearLayoutName = (LinearLayout) findViewById(R.id.linear_layout_name);
		
		showName.setText(name);

		if(getIntent().hasExtra("CALENDAR_ENVIRONMENT")){
			linearLayoutName.setBackgroundResource(R.drawable.color_environment);
		}else {
			linearLayoutName.setBackgroundResource(R.drawable.color_place);
		}		
	}
	
	private class GetPlaceCalendarFeedTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
				calendarWSI = new CalendarWSI();
				calendars = calendarWSI.getListCalendarP(calendarPlaceId);
				listItens.clear();				
				for (int i = 0; i < calendars.size(); i++) {
					listItens.add(calendars.get(i).getCalendarEvent());
				}
				
				if(calendars.size() == 0){
					listItens.add(getString(R.string.calendar_no_results));
				}
				
				adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, listItens);
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
			listViewResults.setAdapter(adapter); 
        }
    }
	
	private class GetEnvironmentCalendarFeedTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
				calendarWSI = new CalendarWSI();
				calendars = calendarWSI.getListCalendarE(calendarEnvironmentId);
				listItens.clear();				
				for (int i = 0; i < calendars.size(); i++) {
					listItens.add(calendars.get(i).getCalendarEvent());
				}
				
				if(calendars.size() == 0){
					listItens.add(getString(R.string.calendar_no_results));
				}
				
				adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, listItens);
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
			listViewResults.setAdapter(adapter);
			
        }
    }
	
	public void addIntent(Intent intent){
    	if(previousAction.get(0).equals("SHOWMAP")){
    		navIntent.putExtra(previousAction.get(0), true);
    		if(previousAction.get(1).equals("ENVIRONMENT")){
    			navIntent.putExtra(previousAction.get(1), previousAction.get(2));
    			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
    			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
    		}
    		if(previousAction.get(1).equals("PLACE")){
    			navIntent.putExtra(previousAction.get(1), previousAction.get(2));
    			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
    			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
    			navIntent.putExtra(previousAction.get(7), previousAction.get(8));
    		}
    		
    		if(previousAction.get(1).equals("IMPORTANT")){
    			navIntent.putExtra(previousAction.get(1), previousAction.get(2));
    			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
    			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
    		}
    		if(previousAction.get(1).equals("FAVORITE")){
    			navIntent.putExtra(previousAction.get(1), previousAction.get(2));
    			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
    			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
    		}
    	}else {
    		navIntent.putExtra(previousAction.get(0), true);
    		navIntent.putExtra(previousAction.get(1), previousAction.get(2));
			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
			navIntent.putExtra(previousAction.get(7), previousAction.get(8));
			navIntent.putExtra(previousAction.get(9), previousAction.get(10));
			navIntent.putExtra(previousAction.get(11), previousAction.get(12));
    	}
    }	
	
	@Override
	public void onBackPressed(){
		navIntent = new Intent(context, ShowInfoController.class);
		if(previousAction.size() > 0){
			addIntent(navIntent);
			startActivity(navIntent);
		}else {
			super.onBackPressed();
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		
		if(calendars.size() != 0){
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle(getStringResource(R.string.alertt_add_calendar));
			alert.setMessage(getStringResource(R.string.alertm_add_calendar) + " " + calendars.get(position).getCalendarEvent() + "?");
			alert.setPositiveButton(getStringResource(R.string.ok), new DialogInterface.OnClickListener() {	            	 
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	addToCalendar();
	            }
	        });
			alert.setNegativeButton(getStringResource(R.string.cancel), new DialogInterface.OnClickListener() {	            	 
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	            }
	        });
			alert.show();
		}
	}
	
	public void addToCalendar(){
		
	}
}
