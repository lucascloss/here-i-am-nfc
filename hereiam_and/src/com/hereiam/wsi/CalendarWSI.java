package com.hereiam.wsi;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hereiam.model.Calendar;
import com.hereiam.model.Place;
import com.hereiam.wsi.rest.BaseWSI;

public class CalendarWSI extends BaseWSI{

	public static final String CALENDAR_URI = URI + "calendar";
	public static final String CALENDAR_LIST_E = CALENDAR_URI + "/list/byenvironment?";
	public static final String CALENDAR_LIST_P = CALENDAR_URI + "/list/byplace?";
	
	public ArrayList<Calendar> getListCalendarE(int environmentId){
		httpClient = getHttpClient();
        String result = null;                
        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
        try {
        	HttpGet httpGet = new HttpGet(CALENDAR_LIST_E + "environmentid=" + environmentId);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);      
        	if(!result.equals("null")){
	        	JSONObject jsonObject = new JSONObject(result);        	
	        	Object check = jsonObject.get("calendar");
	        	if (check instanceof JSONArray) {
	        	    calendars = calendarListFromJSONE(jsonObject);
	        	}
	        	else if (check instanceof JSONObject) {       		
	        	    Calendar calendar = new Calendar();
	        	    calendar = calendarFromJSONE(jsonObject.getJSONObject("calendar"));
	        	    calendars.add(calendar);
	        	}
	        	else {        	    	     
	        	}
	        }        	        	        
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("getListCalendarE", "Erro método getListCalendarE");
        }
        return calendars;
	}
	
	public ArrayList<Calendar> getListCalendarP(int placeId){
		httpClient = getHttpClient();
        String result = null;                
        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
        try {
        	HttpGet httpGet = new HttpGet(CALENDAR_LIST_P + "placeid=" + placeId);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);      
        	if(!result.equals("null")){
	        	JSONObject jsonObject = new JSONObject(result);        	
	        	Object check = jsonObject.get("calendar");
	        	if (check instanceof JSONArray) {
	        	    calendars = calendarListFromJSONP(jsonObject);
	        	}
	        	else if (check instanceof JSONObject) {       		
	        	    Calendar calendar = new Calendar();
	        	    calendar = calendarFromJSONP(jsonObject.getJSONObject("calendar"));
	        	    calendars.add(calendar);
	        	}
	        	else {        	    	     
	        	}
	        }        	        	        
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("getListCalendarE", "Erro método getListCalendarE");
        }
        return calendars;
	}
	
	public Calendar calendarFromJSONE(JSONObject jsonObject){
    	Calendar calendar = new Calendar();
    	
    	try {
    		calendar.setCalendarId(jsonObject.getInt("calendarId"));
    		calendar.setCalendarEvent(decodeURL(jsonObject.getString("calendarEvent")));
    		calendar.setCalendarInfo(decodeURL(jsonObject.getString("calendarInfo")));
    		calendar.setCalendarStarts(decodeURL(jsonObject.getString("calendarStarts")));
    		calendar.setCalendarEnds(decodeURL(jsonObject.getString("calendarEnds")));
    		calendar.setEnvironmentId(jsonObject.getInt("environmentId"));    		
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("calendarFromJSONE", "Erro ao executar calendarFromJSONE");
		}    	    	
    	return calendar;
    }
	
	public ArrayList<Calendar> calendarListFromJSONE(JSONObject jsonObject){
    	ArrayList<Calendar> calendars = new ArrayList<Calendar>();
    	try {
    		JSONArray jsonArray = jsonObject.getJSONArray("calendar");
        	for(int x = 0; x <= jsonArray.length() - 1; ++x) {
        		Calendar calendar = new Calendar();       		    			
        		calendar.setCalendarId(jsonArray.getJSONObject(x).getInt("calendarId"));
        		calendar.setCalendarEvent(decodeURL(jsonArray.getJSONObject(x).getString("calendarEvent")));
        		calendar.setCalendarInfo(decodeURL(jsonArray.getJSONObject(x).getString("calendarInfo")));
        		calendar.setCalendarStarts(decodeURL(jsonArray.getJSONObject(x).getString("calendarStarts")));
        		calendar.setCalendarEnds(decodeURL(jsonArray.getJSONObject(x).getString("calendarEnds")));
        		calendar.setEnvironmentId(jsonArray.getJSONObject(x).getInt("environmentId"));   
    			calendars.add(calendar);
        	}    		
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("calendarListCalendarE", "Erro ao executar calendarListCalendarE");
		}    	    	
    	return calendars;
    }
	
	public Calendar calendarFromJSONP(JSONObject jsonObject){
    	Calendar calendar = new Calendar();
    	
    	try {
    		calendar.setCalendarId(jsonObject.getInt("calendarId"));
    		calendar.setCalendarEvent(decodeURL(jsonObject.getString("calendarEvent")));
    		calendar.setCalendarInfo(decodeURL(jsonObject.getString("calendarInfo")));
    		calendar.setCalendarStarts(decodeURL(jsonObject.getString("calendarStarts")));
    		calendar.setCalendarEnds(decodeURL(jsonObject.getString("calendarEnds")));
    		calendar.setPlaceId(jsonObject.getInt("placeId"));    		
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("calendarFromJSONE", "Erro ao executar calendarFromJSONE");
		}    	    	
    	return calendar;
    }
	
	public ArrayList<Calendar> calendarListFromJSONP(JSONObject jsonObject){
    	ArrayList<Calendar> calendars = new ArrayList<Calendar>();
    	try {
    		JSONArray jsonArray = jsonObject.getJSONArray("calendar");
        	for(int x = 0; x <= jsonArray.length() - 1; ++x) {
        		Calendar calendar = new Calendar();       		    			
        		calendar.setCalendarId(jsonArray.getJSONObject(x).getInt("calendarId"));
        		calendar.setCalendarEvent(decodeURL(jsonArray.getJSONObject(x).getString("calendarEvent")));
        		calendar.setCalendarInfo(decodeURL(jsonArray.getJSONObject(x).getString("calendarInfo")));
        		calendar.setCalendarStarts(decodeURL(jsonArray.getJSONObject(x).getString("calendarStarts")));
        		calendar.setCalendarEnds(decodeURL(jsonArray.getJSONObject(x).getString("calendarEnds")));
        		calendar.setEnvironmentId(jsonArray.getJSONObject(x).getInt("placeId"));   
    			calendars.add(calendar);
        	}    		
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("calendarListCalendarE", "Erro ao executar calendarListCalendarE");
		}    	    	
    	return calendars;
    }
}
