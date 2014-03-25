package com.hereiam.controller;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.controller.adapter.AlertDialogAdapter;
import com.hereiam.helper.Alerts;
import com.hereiam.helper.Validator;
import com.hereiam.model.Environment;
import com.hereiam.model.Place;
import com.hereiam.wsi.EnvironmentWSI;
import com.hereiam.wsi.PlaceWSI;

public class DashBoardController extends BaseActivity implements OnItemClickListener{

	private final static int LIST_ENVIRONMENTS = 1;
	private final static int LIST_PLACES = 2;
	private final static int ROUTE_A = 3;
	private final static int ROUTE_B = 4;
	
	private Context context;
	private Intent navIntent;
	
	///	
    private ListView listViewResults;
    private AlertDialog alertDialog;
    private AlertDialogAdapter alertDialogAdapter;
    private Builder alertDialogBuilder;
    private LinearLayout layout;
    private EnvironmentWSI environmentWSI;
	private ArrayList<Environment> environments;
	private PlaceWSI placeWSI;
	private ArrayList<Place> places;
	private ArrayList<String> listItens = new ArrayList<String>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		context = this;
        
        if(getIntent().getBooleanExtra("CREATION", false)){
        	Toast.makeText(context, getString(R.string.toast_creation), Toast.LENGTH_LONG).show();
        }	
	}
	 
	public void showMap(View view) {		
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_environment_list), getString(R.string.progressm_environment_list));
			createAlertDialog(LIST_ENVIRONMENTS);
			navIntent = new Intent(this, MapViewController.class);
			navIntent.putExtra("SHOWMAP", true);						
			new SelectEnvironemntAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}			
	}

	public void readNFC(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startActivity(new Intent(this, NFCReaderController.class));				
		}else {
			Alerts.createErrorAlert(1, context);
		}
	}

	public void listPlaces(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
			createAlertDialog(LIST_PLACES);
			navIntent = new Intent(this, MapViewController.class);
			navIntent.putExtra("SHOWMAP", true);			
			new SelectPlaceAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}
	}

	public void listFavorites(View view) {
	
	}
	
	public void startRoute(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
			createAlertDialog(ROUTE_A);
			navIntent = new Intent(this, MapViewController.class);
			//navIntent.putExtra("SHOWMAP", true);			
			new SelectPlaceAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}
	}
	
	public void listImportants(View view) {
	
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }
	    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){	        	
        	case R.id.action_logout:
        		clearPreferences();
        		startActivity(LoginAuthController.class);
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }	
    }
    
    @Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	}

    
    public void onBackPressed() {
		moveTaskToBack(true);
	}
    
    public void createAlertDialog(int type){
    	alertDialogBuilder = new AlertDialog.Builder(context);    	
    	listViewResults = new ListView(context);
    	layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);        
        layout.addView(listViewResults);      
        switch (type) {
		case LIST_ENVIRONMENTS:
	        listViewResults.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int position,
						long id) {				
					navIntent.putExtra("ENVIRONMENT", listItens.get(position));
					navIntent.putExtra("LATITUDE", environments.get(position).getEnvtLatitude());
					navIntent.putExtra("LONGITUDE", environments.get(position).getEnvtLongitude());
					startActivity(navIntent);
				}        	
			});
	        alertDialogBuilder.setTitle(R.string.alertt_environment_list);
			break;
		case LIST_PLACES:
	        listViewResults.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int position,
						long id) {				
					navIntent.putExtra("PLACE", listItens.get(position));
					navIntent.putExtra("LATITUDE", places.get(position).getPlaceLatitude());
					navIntent.putExtra("LONGITUDE", places.get(position).getPlaceLongitude());
					startActivity(navIntent);
				}        	
			});
	        alertDialogBuilder.setTitle(R.string.alertt_place_list);
			break;
		case ROUTE_A:
			listViewResults.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int position,
						long id) {				
					navIntent.putExtra("ROUTE", true);
					navIntent.putExtra("PLACE_A", listItens.get(position));
					navIntent.putExtra("LATITUDE_A", places.get(position).getPlaceLatitude());
					navIntent.putExtra("LONGITUDE_A", places.get(position).getPlaceLongitude());
					listItens.remove(position);
					createAlertDialog(ROUTE_B);
				}        	
			});
	        alertDialogBuilder.setTitle(R.string.alertt_route_A_list);			
			break;
			
		case ROUTE_B:
			
			alertDialogAdapter = new AlertDialogAdapter(context, listItens);
		    listViewResults.setAdapter(alertDialogAdapter);
			listViewResults.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int position,
						long id) {				
					navIntent.putExtra("PLACE_B", listItens.get(position));
					navIntent.putExtra("LATITUDE_B", places.get(position).getPlaceLatitude());
					navIntent.putExtra("LONGITUDE_B", places.get(position).getPlaceLongitude());
				}        	
			});
	        alertDialogBuilder.setTitle(R.string.alertt_route_B_list);
		default:
			break;
		}

        
        alertDialogBuilder.setView(layout);	            
        alertDialogBuilder.setNegativeButton(getStringResource(R.string.cancel), new DialogInterface.OnClickListener() {	            	 
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
    
    private class SelectEnvironemntAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			try{
				environmentWSI = new EnvironmentWSI();
				environments = environmentWSI.getListEnvironment();	
				listItens.clear();				
				for (int i = 0; i < environments.size(); i++) {
					listItens.add(environments.get(i).getEnvtName());
				}
				
				alertDialogAdapter = new AlertDialogAdapter(context, listItens);
			    listViewResults.setAdapter(alertDialogAdapter);
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        alertDialog = alertDialogBuilder.show(); 
        }
    }

    private class SelectPlaceAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
				placeWSI = new PlaceWSI();
				places = placeWSI.getListPlace();	
				listItens.clear();				
				for (int i = 0; i < places.size(); i++) {
					listItens.add(places.get(i).getPlaceName());
				}
				
				alertDialogAdapter = new AlertDialogAdapter(context, listItens);
			    listViewResults.setAdapter(alertDialogAdapter);
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        alertDialog = alertDialogBuilder.show(); 
        }
    }
	
}
