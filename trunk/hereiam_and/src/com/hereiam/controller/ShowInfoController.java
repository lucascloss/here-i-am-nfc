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
	private String name;
	private TextView showInfo;
	private String info;
	private int placeId;
	private int environmentId; 
	private LinearLayout linearLayoutName;
	private Menu menu;
	private int userId;
	private String userName;
	private FavoritePlaceWSI favoritePlaceWSI;
	private FavoritePlace favoritePlace;
	private boolean result;
	private ArrayList<String> previousAction = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		context = this;
		userId = getUserId();
        userName = getUserName();
		
		if(getIntent().hasExtra("ENVIRONMENT")){
			name = getIntent().getStringExtra("ENVIRONMENT");
			info = getIntent().getStringExtra("INFO");
			environmentId = getIntent().getIntExtra("ENVIRONMENT_ID", 0);
			
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
			placeId = getIntent().getIntExtra("PLACE_ID", 0);	
			
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
		getReferences();		        
	}
	
	public void getReferences(){
		showName = (TextView) findViewById(R.id.info_txt_name);
		showInfo = (TextView) findViewById(R.id.info_txt_info);
		linearLayoutName = (LinearLayout) findViewById(R.id.linear_layout_name);
		
		showName.setText(name);
		showInfo.setText(info);
		
		
		if(getIntent().hasExtra("ENVIRONMENT")){
			linearLayoutName.setBackgroundResource(R.drawable.color_environment);
		}else {
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
	    		if(getIntent().hasExtra("ENVIRONMENT")){
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT", name);
	    			navIntent.putExtra("CALENDAR_ENVIRONMENT_ID", environmentId);	    				    			
	    		}
	        	
	    		if(getIntent().hasExtra("PLACE")){
	    			navIntent.putExtra("CALENDAR_PLACE", name);
	    			navIntent.putExtra("CALENDAR_PLACE_ID", placeId);
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
        		/*navIntent = new Intent(context, ShowInfoController.class);
        		navIntent.putExtra("PLACE", name);
        		navIntent.putExtra("INFO", info);	
				navIntent.putExtra("PLACE_ID", placeId);
				navIntent.putExtra("FAVORITE_ID", favoritePlace.getFpId());
				if(previousAction.size() > 0){
					addIntent(navIntent);
				}*/
        		//finish();
        		navIntent = new Intent(context, MapViewController.class);
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
        		/*navIntent = new Intent(context, ShowInfoController.class);
        		navIntent.putExtra("PLACE", name);
        		navIntent.putExtra("INFO", info);	
				navIntent.putExtra("PLACE_ID", placeId);	
				if(previousAction.size() > 0){
					addIntent(navIntent);
				}*/
        		//finish();
        		navIntent = new Intent(context, MapViewController.class);
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
}
