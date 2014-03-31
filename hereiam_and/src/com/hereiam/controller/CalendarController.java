package com.hereiam.controller;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.controller.adapter.AlertDialogAdapter;
import com.hereiam.model.Place;
import com.hereiam.wsi.PlaceWSI;

public class CalendarController extends BaseActivity{
	
	private Context context;
	private PlaceWSI placeWSI;
	private ArrayList<Place> places = new ArrayList<Place>();
	private ArrayList<String> listItens = new ArrayList<String>();
	private ListView listViewResults;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		context = this;
		//userId = getUserId();
        //userName = getUserName();
		listViewResults = (ListView) findViewById(R.id.listView);
        new SelectPlaceByImportanceAlertDialogFeedTask().execute(); 
	}

	 private class SelectPlaceByImportanceAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

	    	@Override
	    	protected Void doInBackground(Void... arg0) {
				try{
					placeWSI = new PlaceWSI();
					places = placeWSI.getListPlaceByImportance();	
					listItens.clear();				
					for (int i = 0; i < places.size(); i++) {
						listItens.add(places.get(i).getPlaceName());
					}
					adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, listItens);
					//listViewResults.setAdapter(adapter); 
				}finally {
					
				}
				return null;
			}
			
			@Override
	        protected void onPostExecute(Void res){
				//finishProgressDialog();
				listViewResults.setAdapter(adapter); 
	        }
	    }
}
