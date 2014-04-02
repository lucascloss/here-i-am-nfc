package com.hereiam.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.helper.Alerts;
import com.hereiam.model.FavoritePlace;
import com.hereiam.wsi.FavoritePlaceWSI;
import com.hereiam.wsi.PlaceWSI;

public class ShowInfoController extends BaseActivity{
	
	private Context context;
	private Intent navIntent;
	private TextView showName;	
	private TextView showInfo;
	private String environmentName;
	private String environmentInfo;
	private int environmentId; 
	private String placeName;
	private String placeInfo;
	private int placeId;	
	private LinearLayout linearLayoutName;
	private Menu menu;
	private int userId;
	private String userName;
	private FavoritePlaceWSI favoritePlaceWSI;
	private FavoritePlace favoritePlace;
	private boolean result;
	private ArrayList<String> previousAction = new ArrayList<String>();
	private String latitudePlace;
	private String longitudePlace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		context = this;
		userId = getUserId();
        userName = getUserName();
		
		if(getIntent().hasExtra("ENVIRONMENT")){
			environmentName = getIntent().getStringExtra("ENVIRONMENT_NAME");
			environmentInfo = getIntent().getStringExtra("ENVIRONMENT_INFO");
			environmentId = getIntent().getIntExtra("ENVIRONMENT_ID", 0);						
		}
		
		if(getIntent().hasExtra("PLACE")){
			placeName = getIntent().getStringExtra("PLACE_NAME");
			placeInfo = getIntent().getStringExtra("PLACE_INFO");
			placeId = getIntent().getIntExtra("PLACE_ID", 0);														
		}
		
		if(getIntent().hasExtra("ROUTE")){
			placeName = getIntent().getStringExtra("PLACE");
			placeInfo = getIntent().getStringExtra("INFO");
			placeId = getIntent().getIntExtra("PLACE_ID", 0);			 
		}
		
		if(getIntent().hasExtra("NFC")){
			
		}
		getReferences();		        
	}
	
	public void getReferences(){
		showName = (TextView) findViewById(R.id.info_txt_name);
		showInfo = (TextView) findViewById(R.id.info_txt_info);
		linearLayoutName = (LinearLayout) findViewById(R.id.linear_layout_name);					
		
		if(getIntent().hasExtra("ENVIRONMENT")){
			showName.setText(environmentName);
			showInfo.setText(environmentInfo);
			linearLayoutName.setBackgroundResource(R.drawable.color_environment);
		}else {
			showName.setText(placeName);
			showInfo.setText(placeInfo);
			linearLayoutName.setBackgroundResource(R.drawable.color_place);
		}		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
		getMenuInflater().inflate(R.menu.menu_info, menu);
		if(getIntent().hasExtra("ENVIRONMENT")){
			MenuItem menuAddFavorite = menu.findItem(R.id.action_add_favorite);
			menuAddFavorite.setVisible(false);
		}
		
		if(getIntent().hasExtra("PLACE")){
			if(getIntent().hasExtra("FAVORITE_ID")){
				MenuItem menuAddFavorite = menu.findItem(R.id.action_add_favorite);
				menuAddFavorite.setVisible(false);
				
				MenuItem menuDeleteFavorite = menu.findItem(R.id.action_delete_favorite);
				menuDeleteFavorite.setVisible(true);
			}
		}
        return true;
    }
	
	@Override
	public void onBackPressed(){
		navIntent = new Intent(context, MapViewController.class);
		if(previousAction.size() > 0){
			addIntent(navIntent);
			startActivity(navIntent);
		}else {
			super.onBackPressed();
		}
		
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){	        	        	
	        case R.id.action_add_favorite:
	        	startProgressDialog(getString(R.string.progresst_add_favorite), getString(R.string.progressm_add_favorite));	        			
	        	new CreateFavoritePlaceTask().execute();
	        	return true;
	        case R.id.action_delete_favorite:
	        	startProgressDialog(getString(R.string.progresst_delete_favorite), getString(R.string.progressm_delete_favorite));
	        	new DeleteFavoritePlaceTask().execute();
	        	return true;
	        case R.id.action_show_calendar:   
	        	navIntent = new Intent(context, CalendarController.class);
	    		/*if(getIntent().hasExtra("ENVIRONMENT")){
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT", environmentName);
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT_ID", environmentId);	
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT_INFO", environmentInfo);
	    		}
	        	
	    		if(getIntent().hasExtra("PLACE")){
	    			navIntent.putExtra("CALENDAR_PLACE", placeName);
	    			navIntent.putExtra("CALENDAR_PLACE_ID", placeId);
	    			navIntent.putExtra("CALENDAR_PLACE_INFO", placeInfo);
	    		}*/
	    		
	        	if(getIntent().hasExtra("INFO_ENVIRONMENT")){
	        		navIntent.putExtra("INFO_ENVIRONMENT", true);
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT", environmentName);
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT_ID", environmentId);	
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT_INFO", environmentInfo);
	        	}
        	
	        	if(getIntent().hasExtra("INFO_PLACE")){
	        		navIntent.putExtra("INFO_PLACE", true);
	        		navIntent.putExtra("CALENDAR_PLACE", placeName);
	    			navIntent.putExtra("CALENDAR_PLACE_ID", placeId);
	    			navIntent.putExtra("CALENDAR_PLACE_INFO", placeInfo);
	        	}
	        	
	    		if(previousAction.size() > 0){
	    			addIntent(navIntent);
	    		}
	    		
	        	startActivity(navIntent);
	        	return true;
	        case R.id.action_logout:
	        	clearPreferences();
	    		startActivity(LoginAuthController.class);   
	        	return true;
	        default:
	    		return super.onOptionsItemSelected(item);
        }	
    }
    
    private class CreateFavoritePlaceTask extends AsyncTask<String, Void, Void>{

    	@Override
    	protected Void doInBackground(String... placeName) {
			try{
				favoritePlaceWSI = new FavoritePlaceWSI();
	        	favoritePlace = favoritePlaceWSI.createFavoritePlace(userId, placeId);
			}finally {				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			if(favoritePlace.getFpId() != 0){
				finishProgressDialog();
        		Toast.makeText(context, getString(R.string.toast_added_favorite), Toast.LENGTH_SHORT).show();
        		navIntent = new Intent(context, ShowInfoController.class);
        		navIntent.putExtra("PLACE", placeName);
        		navIntent.putExtra("INFO", placeInfo);	
				navIntent.putExtra("PLACE_ID", placeId);
				navIntent.putExtra("FAVORITE_ID", favoritePlace.getFpId());
				if(previousAction.size() > 0){
					addIntent(navIntent);
				}
        		//finish();
        		
        		startActivity(navIntent);
        	}else {
        		finishProgressDialog();
        		Alerts.createErrorAlert(10, context);
        	}
        }
    }
    
    private class DeleteFavoritePlaceTask extends AsyncTask<String, Void, Void>{

    	@Override
    	protected Void doInBackground(String... placeName) {
			try{
				favoritePlaceWSI = new FavoritePlaceWSI();
	        	result = favoritePlaceWSI.deleteFavoritePlace(userId, placeId);
			}finally {				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			if(result){
				finishProgressDialog();
        		Toast.makeText(context, getString(R.string.toast_deleted_favorite), Toast.LENGTH_SHORT).show();
        		navIntent = new Intent(context, ShowInfoController.class);
        		navIntent.putExtra("PLACE", placeName);
        		navIntent.putExtra("INFO", placeInfo);	
				navIntent.putExtra("PLACE_ID", placeId);
        		
				if(previousAction.size() > 0){
					addIntentWithOutFavorite(navIntent);
				}
        		finish();
        		//navIntent = new Intent(context, MapViewController.class);
        		startActivity(navIntent);
        	}else {
        		finishProgressDialog();
        		Alerts.createErrorAlert(11, context);
        	}
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
    }
    
    public void addIntentWithOutFavorite(Intent intent){
    	
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
    }
}
