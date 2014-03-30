package com.hereiam.wsi;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hereiam.model.Place;
import com.hereiam.model.User;
import com.hereiam.wsi.rest.BaseWSI;

public class UserWSI extends BaseWSI{

	public static final String USER_URI = URI + "user"; 
	public static final String USER_LIST_URI = USER_URI + "/list";
	public static final String USER_FIND_URI = USER_URI + "/find?";
	public static final String USER_CREATE_URI = USER_URI + "/create?";
	public static final String USER_DELETE_URI = USER_URI + "/delete?";
	public static final String USER_FAVORITE_PLACES = URI + "favplace/list?";
    
    public User getUser(String userName) {        
        httpClient = getHttpClient();
        String result = null;        
        User user = new User();
        
        ArrayList<Place> places = null;
        FavoritePlaceWSI favoritePlaceWSI = new FavoritePlaceWSI();
        try {
        	HttpGet httpGet = new HttpGet(USER_FIND_URI + "username=" + userName);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);
            
        	JSONObject jsonObject = new JSONObject(result);  
        	user = userFromJSON(jsonObject);
        	if(user.getId() != 0){
        		
        		user.setFavoritePlaces(favoritePlaceWSI.getListFavoritePlace(user.getId()));
        	}else {
        		user.setFavoritePlaces(places);
        	}
        	
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("getUser", "Erro método getUser em UserWSI");
        }        
        return user;
    }
    
    public User createUser(String name, String userName, String email,
    		String password) {        
        httpClient = getHttpClient();
        String result = null;
        User user = new User();
        try {
        	HttpGet httpGet = new HttpGet(USER_CREATE_URI + "name=" + name + "&username=" + userName +
            		"&email=" + email + "&password=" + password);
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);
            
        	JSONObject jsonObject = new JSONObject(result);  
        	user = userFromJSON(jsonObject);
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("createUser", "Erro método createUser em UserWSI");
        }        
        return user;
    }     
   
    public User userFromJSON(JSONObject jsonObject){
    	User user = new User();
    	try {
			user.setId(jsonObject.getInt("id"));
			user.setName(decodeURL(jsonObject.getString("name")));
			user.setEmail(decodeURL(jsonObject.getString("email")));
			user.setUserName(decodeURL(jsonObject.getString("userName")));
			user.setPassword(decodeURL(jsonObject.getString("password")));
			//user.setFavoritePlaces(favoritePlaces);
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("fromJSONUser", "Erro ao executar fromJSON");
		}    	    	
    	return user;
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
			Log.d("environmentFromJSONPlace", "Erro ao executar placeFromJSON");
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
			Log.d("listFromJSONPlace", "Erro ao executar listFromJSON");
		}    	    	
    	return places;
    }
}


