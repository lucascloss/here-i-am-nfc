package com.hereiam.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
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
	private String environmentName;
	private String environmentInfo;
	private int environmentId; 
	private String placeName;
	private String placeInfo;
	private int placeId;
	private ArrayList<String> previousAction = new ArrayList<String>();
	private int calendarPlaceId;
	private int calendarEnvironmentId;
	private String calendarPlace;
	private String calendarEnvironment;
	private String calendarEnvironmentInfo;
	private String calendarPlaceInfo;
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
			environmentName = getIntent().getStringExtra("ENVIRONMENT");
			environmentInfo = getIntent().getStringExtra("INFO");
			environmentId = getIntent().getIntExtra("ENVIRONMENT_ID", 0);
			
			if(getIntent().hasExtra("LATITUDE_ENVIRONMENT") && getIntent().hasExtra("LONGITUDE_ENVIRONMENT")){
				previousAction.add("SHOWMAP");
				previousAction.add("ENVIRONMENT");
				previousAction.add(getIntent().getStringExtra("ENVIRONMENT"));
				previousAction.add("LATITUDE_ENVIRONMENT");
				previousAction.add(getIntent().getStringExtra("LATITUDE_ENVIRONMENT"));
				previousAction.add("LONGITUDE_ENVIRONMENT");
				previousAction.add(getIntent().getStringExtra("LONGITUDE_ENVIRONMENT"));
			}
		}
		
		if(getIntent().hasExtra("PLACE")){
			placeName = getIntent().getStringExtra("PLACE");
			placeInfo = getIntent().getStringExtra("INFO");
			placeId = getIntent().getIntExtra("PLACE_ID", 0);								
			
			if(getIntent().hasExtra("PLACE")){
				previousAction.add("SHOWMAP");
				previousAction.add("PLACE");
				previousAction.add(getIntent().getStringExtra("PLACE"));
				previousAction.add("LATITUDE_PLACE");
				previousAction.add(getIntent().getStringExtra("LATITUDE_PLACE"));
				previousAction.add("LONGITUDE_PLACE");
				previousAction.add(getIntent().getStringExtra("LONGITUDE_PLACE"));
				//previousAction.add("PLACE_M");
				//previousAction.add(getIntent().getStringExtra("PLACE_M"));
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
			placeName = getIntent().getStringExtra("PLACE");
			placeInfo = getIntent().getStringExtra("INFO");
			placeId = getIntent().getIntExtra("PLACE_ID", 0);
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
		
		if(getIntent().hasExtra("NFC")){
			
		}
		
		if(getIntent().hasExtra("INFO_ENVIRONMENT")){
			calendarEnvironment = getIntent().getStringExtra("CALENDAR_ENVIRONMENT");
			calendarEnvironmentId = getIntent().getIntExtra("CALENDAR_ENVIRONMENT_ID", 0);
			calendarEnvironmentInfo = getIntent().getStringExtra("CALENDAR_ENVIRONMENT_INFO");
			getReferences(calendarEnvironment);
			startProgressDialog(getString(R.string.progresst_loading_calendar), getString(R.string.progressm_loading_environment_calendar));
			new GetEnvironmentCalendarFeedTask().execute();
		}
		
		if(getIntent().hasExtra("INFO_PLACE")){
			calendarPlace = getIntent().getStringExtra("CALENDAR_PLACE");
			calendarPlaceId = getIntent().getIntExtra("CALENDAR_PLACE_ID", 0);
			calendarPlaceInfo = getIntent().getStringExtra("CALENDAR_PLACE_INFO");
			getReferences(calendarPlace);
			startProgressDialog(getString(R.string.progresst_loading_calendar), getString(R.string.progressm_loading_place_calendar));
			new GetPlaceCalendarFeedTask().execute();
		}
	}

	public void getReferences(String name){
		showName = (TextView) findViewById(R.id.info_txt_name);
		
		linearLayoutName = (LinearLayout) findViewById(R.id.linear_layout_name);		
		
		if(getIntent().hasExtra("INFO_ENVIRONMENT")){
			showName.setText(calendarEnvironment);
			linearLayoutName.setBackgroundResource(R.drawable.color_environment);
		}else {
			showName.setText(calendarPlace);
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
    			navIntent.putExtra("INFO", environmentInfo);
    		}
    		if(previousAction.get(1).equals("PLACE")){
    			navIntent.putExtra(previousAction.get(1), previousAction.get(2));
    			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
    			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
    			//navIntent.putExtra(previousAction.get(7), previousAction.get(8));
    			navIntent.putExtra("INFO", placeInfo);
    		}
    		
    		if(previousAction.get(1).equals("IMPORTANT")){
    			navIntent.putExtra(previousAction.get(1), previousAction.get(2));
    			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
    			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
    			navIntent.putExtra("INFO", placeInfo);
    		}
    		if(previousAction.get(1).equals("FAVORITE")){
    			navIntent.putExtra(previousAction.get(1), previousAction.get(2));
    			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
    			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
    			navIntent.putExtra("INFO", placeInfo);
    		}
    	}
    	if(previousAction.get(0).equals("ROUTE")){
    		navIntent.putExtra(previousAction.get(0), true);
    		navIntent.putExtra(previousAction.get(1), previousAction.get(2));
			navIntent.putExtra(previousAction.get(3), previousAction.get(4));
			navIntent.putExtra(previousAction.get(5), previousAction.get(6));
			navIntent.putExtra(previousAction.get(7), previousAction.get(8));
			navIntent.putExtra(previousAction.get(9), previousAction.get(10));
			navIntent.putExtra(previousAction.get(11), previousAction.get(12));
			navIntent.putExtra("INFO", placeInfo);
    	}
    	
    	if(getIntent().hasExtra("INFO_ENVIRONMENT")){
    		navIntent.putExtra("INFO_ENVIRONMENT", true);
    		navIntent.putExtra("ENVIRONMENT", calendarEnvironment);
    		navIntent.putExtra("INFO", calendarEnvironmentInfo);
    		navIntent.putExtra("ENVIRONMENT_ID", calendarEnvironmentId);
    	}else{
    		navIntent.putExtra("INFO_PLACE", true);
    		navIntent.putExtra("PLACE", calendarPlace);    		
    		navIntent.putExtra("INFO", calendarPlaceInfo);
    		navIntent.putExtra("PLACE_ID", calendarPlaceId);
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
	public void onItemClick(AdapterView<?> arg0, View v, final int position, long id) {
		
		if(calendars.size() != 0){
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle(getStringResource(R.string.alertt_add_calendar));
			alert.setMessage(getStringResource(R.string.alertm_add_calendar) + " " + calendars.get(position).getCalendarEvent() + "?");
			alert.setPositiveButton(getStringResource(R.string.ok), new DialogInterface.OnClickListener() {	            	 
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	if(getIntent().hasExtra("CALENDAR_ENVIRONMENT")){
	            		addToCalendar(calendars.get(position).getCalendarEvent(), calendarEnvironment, 
	            					calendars.get(position).getCalendarInfo(), calendars.get(position).getCalendarStarts(), 
								 calendars.get(position).getCalendarEnds());
	            	}
	            	if(getIntent().hasExtra("CALENDAR_PLACE")){
	            		addToCalendar(calendars.get(position).getCalendarEvent(), calendarPlace, calendars.get(position).getCalendarInfo(), calendars.get(position).getCalendarStarts(), 
								 calendars.get(position).getCalendarEnds());
	            	}	            	
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
	
	public void addToCalendar(String eventName, String location, String info, String dateBegin, String dateEnds) {
		Intent calIntent = new Intent(Intent.ACTION_INSERT); 
		calIntent.setType("vnd.android.cursor.item/event");    
		calIntent.putExtra(Events.TITLE, eventName); 
		calIntent.putExtra(Events.EVENT_LOCATION, location); 
		calIntent.putExtra(Events.DESCRIPTION, info); 
		
		String[] splitBegin = dateBegin.split("#");
		String splitDateBegin = splitBegin[0];
		String[] separateDateBegin = splitBegin[0].split("/");
		
		String splitHourBegin = splitBegin[1];
		String[] separateHourBegin = splitHourBegin.split(":");				
				
		GregorianCalendar calDateBegin = new GregorianCalendar(Integer.parseInt(separateDateBegin[2]), 
				Integer.parseInt(separateDateBegin[1]) - 1, Integer.parseInt(separateDateBegin[0]), Integer.parseInt(separateHourBegin[0]),
				Integer.parseInt(separateHourBegin[1]));
						
		String[] splitEnds = dateEnds.split("#");
		String splitDateEnds = splitEnds[0];
		String[] separateDateEnds = splitEnds[0].split("/");
		
		String splitHourEnds = splitEnds[1];
		String[] separateHourEnds = splitHourEnds.split(":");
		
		GregorianCalendar calDateEnds = new GregorianCalendar(Integer.parseInt(separateDateEnds[2]), 
				Integer.parseInt(separateDateEnds[1]) - 1, Integer.parseInt(separateDateEnds[0]), Integer.parseInt(separateHourEnds[0]),
				Integer.parseInt(separateHourEnds[1]));
		 
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 
		     calDateBegin.getTimeInMillis()); 
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 
		     calDateEnds.getTimeInMillis()); 
		 
		//startActivity(calIntent);
		startActivityForResult(calIntent, 100);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode == RESULT_OK) {
	    	//Toast.makeText(context, "teste", Toast.LENGTH_LONG).show();	    
	    }
	}
}
