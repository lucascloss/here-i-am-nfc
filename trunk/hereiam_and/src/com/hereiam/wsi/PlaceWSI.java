package com.hereiam.wsi;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hereiam.model.Environment;
import com.hereiam.model.Place;
import com.hereiam.wsi.rest.BaseWSI;

public class PlaceWSI extends BaseWSI{

	public static final String PLACE_URI = URI + "place";
	public static final String PLACE_LIST_URI = PLACE_URI + "/list";
	public static final String PLACE_FIND_URI = PLACE_URI + "/find?";
	public static final String PLACE_CREATE_URI = PLACE_URI + "/create?";
	public static final String PLACE_DELETE_URI = PLACE_URI + "/delete?";
	
	public Place getPlace(String name) {        
        httpClient = getHttpClient();
        String result = null;        
        Place place = new Place();
        try {
        	HttpGet httpGet = new HttpGet(PLACE_URI + "name=" + name);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);
        	JSONObject jsonObject = new JSONObject(result);  
        	place = placeFromJSON(jsonObject);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	Log.d("getPlace", "Erro método getPlace");
        }        
        return place;
    }
	
	public ArrayList<Place> getListPlace() {        
        httpClient = getHttpClient();
        String result = null;                
        ArrayList<Place> places = new ArrayList<Place>();
        try {
        	HttpGet httpGet = new HttpGet(PLACE_LIST_URI);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);           
        	JSONObject jsonObject = new JSONObject(result);        	
        	Object check = jsonObject.get("place");
        	if (check instanceof JSONArray) {
        	    places = listFromJSON(jsonObject);//aqui
        	}
        	else if (check instanceof JSONObject) {       		
        	    Place place = new Place();
        	    place = placeFromJSON(jsonObject.getJSONObject("place"));
        	    places.add(place);
        	}
        	else {        	    
        	}        	        	        
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("getListPlace", "Erro método getListPlace");
        }
        return places;
    }
	
	//environmentname
	
	//placeadm
	
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
