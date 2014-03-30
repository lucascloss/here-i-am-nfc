package com.hereiam.controller;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
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
import com.hereiam.wsi.FavoritePlaceWSI;
import com.hereiam.wsi.PlaceWSI;

public class DashBoardController extends BaseActivity implements OnItemClickListener{

	private final static int VIEW_MAP = 1;
	private final static int LIST_PLACES = 2;
	private final static int MAKE_ROUTE_A = 3;
	private final static int MAKE_ROUTE_B = 4;
	private final static int LIST_FAVORITE = 5;
	private final static int LIST_IMPORTANT = 6;
	
	private int selectedMenu;
	private Context context;
	private Intent navIntent;
	private int userId;
	private String userName;
	
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
	private FavoritePlaceWSI favoritePlaceWSI;
	private NfcManager nfcManager;
    private NfcAdapter nfcAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		context = this;
		userId = getUserId();
        userName = getUserName();
        
        if(getIntent().getBooleanExtra("CREATION", false)){
        	Toast.makeText(context, getString(R.string.toast_creation), Toast.LENGTH_SHORT).show();
        }	 
	}
	 
	public void showMap(View view) {		
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_environment_list), getString(R.string.progressm_environment_list));
			createAlertDialog(VIEW_MAP);
			navIntent = new Intent(this, MapViewController.class);
			navIntent.putExtra("SHOWMAP", true);	
			selectedMenu = VIEW_MAP;
			new SelectEnvironemntAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}			
	}

	public void readNFC(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
	        nfcAdapter = nfcManager.getDefaultAdapter();
	        //nfcAdapter = NfcAdapter.getDefaultAdapter(this);
	 
	        if (nfcAdapter == null) {
	    		Alerts.createErrorAlert(6, context);
	            finish();
	            return;
	        }else{
	        	startActivity(new Intent(this, NFCReaderController.class));
	        }
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
			selectedMenu = LIST_PLACES;
			new SelectPlaceRouteAAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}
	}

	public void listFavorites(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_favorites_list), getString(R.string.progressm_favorites_list));
			createAlertDialog(LIST_FAVORITE);
			navIntent = new Intent(this, MapViewController.class);
			navIntent.putExtra("SHOWMAP", true);
			selectedMenu = LIST_FAVORITE;
			new SelectFavoritePlaceAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}
	}
	
	public void startRoute(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_places_list), getString(R.string.progressm_places_list));
			createAlertDialog(MAKE_ROUTE_A);
			navIntent = new Intent(this, MapViewController.class);
			selectedMenu = MAKE_ROUTE_A;
			new SelectPlaceAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}
	}
	
	public void listImportants(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_importants_list), getString(R.string.progressm_importants_list));
			createAlertDialog(LIST_IMPORTANT);
			navIntent = new Intent(this, MapViewController.class);		
			navIntent.putExtra("SHOWMAP", true);
			selectedMenu = LIST_IMPORTANT;
			new SelectPlaceByImportanceAlertDialogFeedTask().execute();			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}
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
	
    	switch (selectedMenu) {
		case VIEW_MAP:
			navIntent.putExtra("ENVIRONMENT", listItens.get(position));
			navIntent.putExtra("LATITUDE_ENVIRONMENT", environments.get(position).getEnvtLatitude());
			navIntent.putExtra("LONGITUDE_ENVIRONMENT", environments.get(position).getEnvtLongitude());
			alertDialog.dismiss();
			startActivity(navIntent);
			break;
		case LIST_PLACES:
			navIntent.putExtra("PLACE", listItens.get(position));
			navIntent.putExtra("LATITUDE_PLACE", places.get(position).getPlaceLatitude());
			navIntent.putExtra("LONGITUDE_PLACE", places.get(position).getPlaceLongitude());
			alertDialog.dismiss();
			startActivity(navIntent);
			break;	
		case MAKE_ROUTE_A:
			navIntent.putExtra("ROUTE", true);
			navIntent.putExtra("PLACE_A", listItens.get(position));
			navIntent.putExtra("LATITUDE_A", places.get(position).getPlaceLatitude());
			navIntent.putExtra("LONGITUDE_A", places.get(position).getPlaceLongitude());
			listItens.remove(position);
			places.remove(position);
			alertDialog.dismiss();
			
			selectedMenu = MAKE_ROUTE_B;
			createAlertDialog(MAKE_ROUTE_B);
			break;
		case MAKE_ROUTE_B:
			navIntent.putExtra("PLACE_B", listItens.get(position));
			navIntent.putExtra("LATITUDE_B", places.get(position).getPlaceLatitude());
			navIntent.putExtra("LONGITUDE_B", places.get(position).getPlaceLongitude());
			alertDialog.dismiss();
			startActivity(navIntent);
			break;
		case LIST_FAVORITE:
			navIntent.putExtra("FAVORITE", listItens.get(position));
			navIntent.putExtra("LATITUDE_FAVORITE", places.get(position).getPlaceLatitude());
			navIntent.putExtra("LONGITUDE_FAVORITE", places.get(position).getPlaceLongitude());
			alertDialog.dismiss();
			startActivity(navIntent);
			break;			
		case LIST_IMPORTANT:
			navIntent.putExtra("IMPORTANT", listItens.get(position));
			navIntent.putExtra("LATITUDE_IMPORTANT", places.get(position).getPlaceLatitude());
			navIntent.putExtra("LONGITUDE_IMPORTANT", places.get(position).getPlaceLongitude());
			alertDialog.dismiss();
			startActivity(navIntent);
			break;
		default:
			break;
		}
    }

    
    public void onBackPressed() {
		moveTaskToBack(true);
	}
    
    public void createAlertDialog(int type){
    	alertDialogBuilder = new AlertDialog.Builder(context);    	
    	listViewResults = new ListView(context);
    	LinearLayout layout = new LinearLayout(context);
    	
        layout.setOrientation(LinearLayout.VERTICAL);        
        layout.addView(listViewResults);   
        listViewResults.setOnItemClickListener(this);
        switch (type) {
		case VIEW_MAP:
	        alertDialogBuilder.setTitle(R.string.alertt_environment_list);
			break;
		case LIST_PLACES:        
	        alertDialogBuilder.setTitle(R.string.alertt_place_list);
			break;
		case MAKE_ROUTE_A:		
	        alertDialogBuilder.setTitle(R.string.alertt_route_A_list);			
			break;
		case MAKE_ROUTE_B:			
	        alertDialogBuilder.setTitle(R.string.alertt_route_B_list);
	        new SelectPlaceRouteBAlertDialogFeedTask().execute();
	        break;
		case LIST_FAVORITE:
			alertDialogBuilder.setTitle(R.string.alertt_favorites_list);
			break;
		case LIST_IMPORTANT:
			alertDialogBuilder.setTitle(R.string.alertt_importants_list);
			break;
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
    
    private class SelectPlaceRouteAAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

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
	
    private class SelectPlaceRouteBAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			try{			
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
    
    private class SelectFavoritePlaceAlertDialogFeedTask extends AsyncTask<Void, Void, Void>{

    	@Override
    	protected Void doInBackground(Void... arg0) {
			try{
				favoritePlaceWSI = new FavoritePlaceWSI();
				places = favoritePlaceWSI.getListFavoritePlace(userId);	
				listItens.clear();		
				if(places.size() != 0){
					for (int i = 0; i < places.size(); i++) {
					listItens.add(places.get(i).getPlaceName());
					alertDialogAdapter = new AlertDialogAdapter(context, listItens);
				    listViewResults.setAdapter(alertDialogAdapter);
					}
				}else {
					
				}				
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        if(places.size() != 0){
	        	alertDialog = alertDialogBuilder.show();
	        }else{
	        	Alerts.createErrorAlert(9, context);
	        }
	        
        }
    }
}
