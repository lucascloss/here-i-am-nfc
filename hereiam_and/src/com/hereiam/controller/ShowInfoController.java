package com.hereiam.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	private boolean result = false;	
	private SharedPreferences preferences; 
	private SharedPreferences.Editor editor;
	private JSONObject json;
	private JSONObject oldJson;
	private String action;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		context = this;
		userId = getUserId();
        userName = getUserName();
        preferences = getApplicationContext().getSharedPreferences("STATE", MODE_PRIVATE);
		
        try {
			json = new JSONObject(preferences.getString("MAP_OVERLAY", ""));
			action = json.getString("ACTION");
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
			
			getReferences();
		} catch (JSONException e) {			
			e.printStackTrace();
		}        
	}
	
	public void getReferences(){
		showName = (TextView) findViewById(R.id.info_txt_name);
		showInfo = (TextView) findViewById(R.id.info_txt_info);
		linearLayoutName = (LinearLayout) findViewById(R.id.linear_layout_name);					
		
		if(getIntent().hasExtra("ENVIRONMENT")){
			showName.setText(environmentName);
			showInfo.setText(environmentInfo);
			linearLayoutName.setBackgroundResource(R.drawable.color_environment);
		}
		
		if(getIntent().hasExtra("PLACE")){
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
			if(getIntent().hasExtra("IS_FAVORITE")){
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
		
		/*if(getIntent().hasExtra("DELETE")){
			editor = preferences.edit();
		    
		    json = new JSONObject();
		   
		    try {
		    	oldJson = new JSONObject(preferences.getString("MAP_OVERLAY", ""));	
		    	if(oldJson.get("ACTION").equals("PLACE")){
			    	json.put("SHOWMAP", true);
					json.put("ACTION", "PLACE");
					json.put("PLACE_ID", oldJson.getInt("FAVORITE_ID"));
				    json.put("PLACE_NAME", oldJson.getString("FAVORITE_NAME"));
				    json.put("PLACE_INFO", oldJson.getString("FAVORITE_INFO"));			    
				    json.put("PLACE_LATITUDE", oldJson.getString("FAVORITE_LATITUDE"));
				    json.put("PLACE_LONGITUDE", oldJson.getString("FAVORITE_LONGITUDE"));
		    	}
			} catch (JSONException e) {				
				e.printStackTrace();
			}
		    		    
		    editor.putString("MAP_OVERLAY", json.toString());
		    editor.commit();
    	}
		
		if(getIntent().hasExtra("ADD")){
			editor = preferences.edit();
		    
		    json = new JSONObject();
		   
		    try {
		    	oldJson = new JSONObject(preferences.getString("MAP_OVERLAY", ""));
		    	
				json.put("SHOWMAP", true);
				json.put("ACTION", "FAVORITE");
				json.put("FAVORITE_ID", oldJson.getInt("PLACE_ID"));
			    json.put("FAVORITE_NAME", oldJson.getString("PLACE_NAME"));
			    json.put("FAVORITE_INFO", oldJson.getString("PLACE_INFO"));			    
			    json.put("FAVORITE_LATITUDE", oldJson.getString("PLACE_LATITUDE"));
			    json.put("FAVORITE_LONGITUDE", oldJson.getString("PLACE_LONGITUDE"));			    
			} catch (JSONException e) {				
				e.printStackTrace();
			}
		    		    
		    editor.putString("MAP_OVERLAY", json.toString());
		    editor.commit();
		}*/
		startActivity(navIntent);		
		//super.onBackPressed();		
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
	    			navIntent.putExtra("ENVIRONMENT", true);
	    			navIntent.putExtra("ENVIRONMENT_NAME", environmentName);
	    			navIntent.putExtra("ENVIRONMENT_ID", environmentId);	
	    			navIntent.putExtra("ENVIRONMENT_INFO", environmentInfo);
	    		}
	        	
	    		if(getIntent().hasExtra("PLACE")){
	    			navIntent.putExtra("PLACE", true);
	    			navIntent.putExtra("PLACE_NAME", placeName);
	    			navIntent.putExtra("PLACE_ID", placeId);
	    			navIntent.putExtra("PLACE_INFO", placeInfo);
	    			if(getIntent().hasExtra("IS_FAVORITE")){
	    				navIntent.putExtra("IS_FAVORITE", true);
	    			}
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
        		
        		if(action.equals("ROUTE")){
        			navIntent = new Intent(context, MapViewController.class);
        		}else {        		
	        		navIntent = new Intent(context, ShowInfoController.class);
	        		navIntent.putExtra("PLACE", true);
	        		navIntent.putExtra("PLACE_NAME", placeName);
	        		navIntent.putExtra("PLACE_INFO", placeInfo);	
					navIntent.putExtra("PLACE_ID", placeId);
					navIntent.putExtra("IS_FAVORITE", true);
					navIntent.putExtra("ADD", true);
					
					editor = preferences.edit();
				    
				    json = new JSONObject();
				   
				    try {
				    	oldJson = new JSONObject(preferences.getString("MAP_OVERLAY", ""));
				    	
						json.put("SHOWMAP", true);
						json.put("ACTION", "FAVORITE");
						json.put("FAVORITE_ID", oldJson.getInt("PLACE_ID"));
					    json.put("FAVORITE_NAME", oldJson.getString("PLACE_NAME"));
					    json.put("FAVORITE_INFO", oldJson.getString("PLACE_INFO"));			    
					    json.put("FAVORITE_LATITUDE", oldJson.getString("PLACE_LATITUDE"));
					    json.put("FAVORITE_LONGITUDE", oldJson.getString("PLACE_LONGITUDE"));			    
					} catch (JSONException e) {				
						e.printStackTrace();
					}
				    		    
				    editor.putString("MAP_OVERLAY", json.toString());
				    editor.commit();
        		}
        		
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
	        	boolean teste = false;
	        	teste = true;
			}finally {				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			if(result){
				finishProgressDialog();
        		Toast.makeText(context, getString(R.string.toast_deleted_favorite), Toast.LENGTH_SHORT).show();
        		
        		if(action.equals("ROUTE")){
        			navIntent = new Intent(context, MapViewController.class);
        		}else {
	        		navIntent = new Intent(context, ShowInfoController.class);
	        		navIntent.putExtra("PLACE", true);
	        		navIntent.putExtra("PLACE_NAME", placeName);
	        		navIntent.putExtra("PLACE_INFO", placeInfo);	
					navIntent.putExtra("PLACE_ID", placeId);
					navIntent.putExtra("DELETE", true);
					
					editor = preferences.edit();
				    
				    json = new JSONObject();
				   
				    try {
				    	oldJson = new JSONObject(preferences.getString("MAP_OVERLAY", ""));	
				    	
				    	json.put("SHOWMAP", true);
						json.put("ACTION", "PLACE");
						json.put("PLACE_ID", oldJson.getInt("FAVORITE_ID"));
					    json.put("PLACE_NAME", oldJson.getString("FAVORITE_NAME"));
					    json.put("PLACE_INFO", oldJson.getString("FAVORITE_INFO"));			    
					    json.put("PLACE_LATITUDE", oldJson.getString("FAVORITE_LATITUDE"));
					    json.put("PLACE_LONGITUDE", oldJson.getString("FAVORITE_LONGITUDE"));
				    	
					} catch (JSONException e) {				
						e.printStackTrace();
					}
				    		    
				    editor.putString("MAP_OVERLAY", json.toString());
				    editor.commit();
        		}
					
        		startActivity(navIntent);
        	}else {
        		finishProgressDialog();
        		Alerts.createErrorAlert(11, context);
        	}
        }
    }       
}
