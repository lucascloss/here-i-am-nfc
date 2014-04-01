package com.hereiam.wsi;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hereiam.model.FavoritePlace;
import com.hereiam.model.Place;
import com.hereiam.model.User;
import com.hereiam.wsi.rest.BaseWSI;

public class FavoritePlaceWSI extends BaseWSI{
	
	public static final String FAVORITE_PLACES_URI = URI + "favplace";
	public static final String FAVORITE_PLACES_LIST = FAVORITE_PLACES_URI + "/list?";
	public static final String FAVORITE_PLACES_CREATE = FAVORITE_PLACES_URI + "/create?";
	public static final String FAVORITE_PLACES_DELETE = FAVORITE_PLACES_URI + "/delete?";
	public static final String FAVORITE_PLACES_FIND = FAVORITE_PLACES_URI + "/find?";

	public ArrayList<Place> getListFavoritePlace(int userId){
		httpClient = getHttpClient();
        String result = null;                
        ArrayList<Place> places = new ArrayList<Place>();
        try {
        	HttpGet httpGet = new HttpGet(FAVORITE_PLACES_LIST + "userid=" + userId);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);      
        	if(!result.equals("null")){
	        	JSONObject jsonObject = new JSONObject(result);        	
	        	Object check = jsonObject.get("place");
	        	if (check instanceof JSONArray) {
	        	    places = listFromJSON(jsonObject);
	        	}
	        	else if (check instanceof JSONObject) {       		
	        	    Place place = new Place();
	        	    place = placeFromJSON(jsonObject.getJSONObject("place"));
	        	    places.add(place);
	        	}
	        	else {        	    	     
	        	}
	        }        	        	        
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("getListFavoritePlace", "Erro método getListPlace");
        }
        return places;
	}
	
	public FavoritePlace createFavoritePlace(int userId, int placeId) {        
        httpClient = getHttpClient();
        String result = null;
        FavoritePlace favoritePlace = new FavoritePlace();
        try {
        	HttpGet httpGet = new HttpGet(FAVORITE_PLACES_CREATE + "userid=" + userId +
        			"&placeid=" + placeId);
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);
            
        	JSONObject jsonObject = new JSONObject(result);  
        	favoritePlace = favoritePlaceFromJSON(jsonObject);
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("createFavoritePlace", "Erro método createFavoritePlace em FavoritePlaceWSI");
        }        
        return favoritePlace;
    }
	
	public FavoritePlace findFavoritePlace(int userId, int placeId) {        
        httpClient = getHttpClient();
        String result = null;
        FavoritePlace favoritePlace = new FavoritePlace();
        try {
        	HttpGet httpGet = new HttpGet(FAVORITE_PLACES_FIND + "userid=" + userId +
        			"&placeid=" + placeId);
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);
            
        	JSONObject jsonObject = new JSONObject(result);  
        	favoritePlace = favoritePlaceFromJSON(jsonObject);
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("createFavoritePlace", "Erro método createFavoritePlace em FavoritePlaceWSI");
        }        
        return favoritePlace;
    }
	
	public boolean deleteFavoritePlace(int userId, int placeId) {        
        httpClient = getHttpClient();
        String resultJson = null;
        boolean result = false;
        FavoritePlace favoritePlace = new FavoritePlace();
        try {
        	HttpGet httpGet = new HttpGet(FAVORITE_PLACES_DELETE + "userid=" + userId +
        			"&placeid=" + placeId);
        	HttpResponse response = httpClient.execute(httpGet);
        	
        	InputStream resultStream = response.getEntity().getContent();
        	response.getEntity().getContentType();
        	resultJson = convertInputStreamToString(resultStream);
            
        	JSONObject jsonObject = new JSONObject(resultJson);  
        	favoritePlace = favoritePlaceFromJSON(jsonObject);
        	
        	if((favoritePlace.getUserId() == 0) && (favoritePlace.getPlaceId() == 0)){
        		result = true;
        	}else {
        		result = false;
        	}
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("createFavoritePlace", "Erro método createFavoritePlace em FavoritePlaceWSI");
        }        
        return result;
    }
	
	public FavoritePlace favoritePlaceFromJSON(JSONObject jsonObject){
    	FavoritePlace favoritePlace = new FavoritePlace();
    	
    	try {
    		favoritePlace.setFpId(jsonObject.getInt("fpId"));
    		favoritePlace.setUserId(jsonObject.getInt("userId"));
    		favoritePlace.setPlaceId(jsonObject.getInt("placeId"));
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("favoritePlaceFromJSON", "Erro ao executar favoritePlaceFromJSON");
		}    	    	
    	return favoritePlace;
    }
	
	
	public Place placeFromJSON(JSONObject jsonObject){
    	Place place = new Place();
    	
    	try {
    		place.setPlaceId(jsonObject.getInt("placeId"));
    		place.setPlaceName(decodeURL(jsonObject.getString("placeName")));
    		place.setPlaceType(decodeURL(jsonObject.getString("placeType")));
    		place.setPlaceIdNfc(decodeURL(jsonObject.getString("placeIdNfc")));
    		place.setPlaceInfo(decodeURL(jsonObject.getString("placeInfo")));
    		place.setPlaceLatitude(decodeURL(jsonObject.getString("placeLatitude")));
    		place.setPlaceLongitude(decodeURL(jsonObject.getString("placeLongitude")));
    		place.setPlaceEnvironmentName(decodeURL(jsonObject.getString("environmentName")));
    		place.setPlacePlaceAdmUn(decodeURL(jsonObject.getString("placeAdmUn")));
    		place.setImportant(jsonObject.getBoolean("important"));    		    	
			//place.setEnvtCalendar
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("favoritePlaceFromJSONPlace", "Erro ao executar favoritePlaceFromJSON");
		}    	    	
    	return place;
    }
	
	public ArrayList<Place> listFromJSON(JSONObject jsonObject){
    	ArrayList<Place> places = new ArrayList<Place>();
    	try {
    		JSONArray jsonArray = jsonObject.getJSONArray("place");
        	for(int x = 0; x <= jsonArray.length() - 1; ++x) {
        		Place place = new Place();       		    			
    			place.setPlaceId(jsonArray.getJSONObject(x).getInt("placeId"));
        		place.setPlaceName(decodeURL(jsonArray.getJSONObject(x).getString("placeName")));
        		place.setPlaceType(decodeURL(jsonArray.getJSONObject(x).getString("placeType")));
        		place.setPlaceIdNfc(decodeURL(jsonArray.getJSONObject(x).getString("placeIdNfc")));
        		place.setPlaceInfo(decodeURL(jsonArray.getJSONObject(x).getString("placeInfo")));
        		place.setPlaceLatitude(decodeURL(jsonArray.getJSONObject(x).getString("placeLatitude")));
        		place.setPlaceLongitude(decodeURL(jsonArray.getJSONObject(x).getString("placeLongitude")));
        		place.setPlaceEnvironmentName(decodeURL(jsonArray.getJSONObject(x).getString("environmentName")));
        		place.setPlacePlaceAdmUn(decodeURL(jsonArray.getJSONObject(x).getString("placeAdmUn")));
        		place.setImportant(jsonArray.getJSONObject(x).getBoolean("important"));    		    	
    			//place.setEnvtCalendar
    			places.add(place);
        	}    		
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("listFavoritePlaceFromJSONPlace", "Erro ao executar listFavoritePlaceFromJSON");
		}    	    	
    	return places;
    }
}
