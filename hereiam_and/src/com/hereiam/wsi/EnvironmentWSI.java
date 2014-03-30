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
import com.hereiam.wsi.rest.BaseWSI;


public class EnvironmentWSI extends BaseWSI{
	
	public static final String ENVIRONMENT_URI = URI + "environment";
	public static final String ENVIRONMENT_LIST_URI = ENVIRONMENT_URI + "/list";
	public static final String ENVIRONMENT_FIND_URI = ENVIRONMENT_URI + "/find?";
	public static final String ENVIRONMENT_LIST_BY_ADM_URI = ENVIRONMENT_URI + "/list/byenvironmentadm?";
		
	public Environment getEnvironment(String name) {        
        httpClient = getHttpClient();
        String result = null;        
        Environment environment = new Environment();
        try {
        	HttpGet httpGet = new HttpGet(ENVIRONMENT_FIND_URI + "name=" + name);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);
        	JSONObject jsonObject = new JSONObject(result);  
        	environment = environmentFromJSON(jsonObject);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	Log.d("getEnvironment", "Erro método getEnvironment");
        }        
        return environment;
    }
	
	public ArrayList<Environment> getListEnvironment() {        
        httpClient = getHttpClient();
        String result = null;                
        ArrayList<Environment> environments = new ArrayList<Environment>();
        try {
        	HttpGet httpGet = new HttpGet(ENVIRONMENT_LIST_URI);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);           
        	JSONObject jsonObject = new JSONObject(result);        	
        	Object check = jsonObject.get("environment");
        	if (check instanceof JSONArray) {
        	    environments = listFromJSON(jsonObject);
        	}
        	else if (check instanceof JSONObject) {       		
        	    Environment environment = new Environment();
        	    environment = environmentFromJSON(jsonObject.getJSONObject("environment"));
        	    environments.add(environment);
        	}
        	else {        	    
        	}        	        	        
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("getListEnvironment", "Erro método getListEnvironment");
        }
        return environments;
    }
	
	public ArrayList<Environment> getListEnvironmentByAdm(String environmentAdmUn) {        
        httpClient = getHttpClient();
        String result = null;                
        ArrayList<Environment> environments = new ArrayList<Environment>();
        try {
        	HttpGet httpGet = new HttpGet(ENVIRONMENT_LIST_BY_ADM_URI + "environmentadmun=" + environmentAdmUn);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);           
        	JSONObject jsonObject = new JSONObject(result);        	
        	Object check = jsonObject.get("environment");
        	if (check instanceof JSONArray) {
        	    environments = listFromJSON(jsonObject);
        	}
        	else if (check instanceof JSONObject) {       		
        	    Environment environment = new Environment();
        	    environment = environmentFromJSON(jsonObject.getJSONObject("environment"));
        	    environments.add(environment);
        	}
        	else {        	    
        	}        	        	        
        } catch (Exception e) {
        	e.printStackTrace();
            Log.d("getListEnvironment", "Erro método getListEnvironmentByAdm");
        }
        return environments;
    }
	
	
	public Environment environmentFromJSON(JSONObject jsonObject){
    	Environment environment = new Environment();
    	
    	try {
			environment.setEnvtId(jsonObject.getInt("envtId"));
			environment.setEnvtName(decodeURL(jsonObject.getString("envtName")));
			environment.setEnvtType(decodeURL(jsonObject.getString("envtType")));
			environment.setEnvtInfo(decodeURL(jsonObject.getString("envtInfo")));
			environment.setEnvtLatitude(decodeURL(jsonObject.getString("envtLatitude")));
			environment.setEnvtLongitude(decodeURL(jsonObject.getString("envtLongitude")));
			environment.setEnvtStreet(decodeURL(jsonObject.getString("street")));
			environment.setEnvtDistrict(decodeURL(jsonObject.getString("district")));
			environment.setEnvtNumber(jsonObject.getInt("number"));
			environment.setEnvtCity(decodeURL(jsonObject.getString("city")));
			environment.setEnvtState(decodeURL(jsonObject.getString("state")));
			environment.setEnvtCountry(decodeURL(jsonObject.getString("country")));
			environment.setEnvtEnvironmentAdUn(decodeURL(jsonObject.getString("environmentAdmUn")));
			//environment.setEnvtCalendar
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("environmentFromJSONEnvironment", "Erro ao executar fromJSON");
		}    	    	
    	return environment;
    }
	
	public ArrayList<Environment> listFromJSON(JSONObject jsonObject){
    	ArrayList<Environment> environments = new ArrayList<Environment>();
    	try {
    		JSONArray jsonArray = jsonObject.getJSONArray("environment");
        	for(int x = 0; x <= jsonArray.length() - 1; ++x) {
        		Environment environment = new Environment();
        		environment.setEnvtId(jsonArray.getJSONObject(x).getInt("envtId"));
    			environment.setEnvtName(decodeURL(jsonArray.getJSONObject(x).getString("envtName")));
    			environment.setEnvtType(decodeURL(jsonArray.getJSONObject(x).getString("envtType")));
    			environment.setEnvtInfo(decodeURL(jsonArray.getJSONObject(x).getString("envtInfo")));
    			environment.setEnvtLatitude(decodeURL(jsonArray.getJSONObject(x).getString("envtLatitude")));
    			environment.setEnvtLongitude(decodeURL(jsonArray.getJSONObject(x).getString("envtLongitude")));
    			environment.setEnvtStreet(decodeURL(jsonArray.getJSONObject(x).getString("street")));
    			environment.setEnvtDistrict(decodeURL(jsonArray.getJSONObject(x).getString("district")));
    			environment.setEnvtNumber(jsonArray.getJSONObject(x).getInt("number"));
    			environment.setEnvtCity(decodeURL(jsonArray.getJSONObject(x).getString("city")));
    			environment.setEnvtState(decodeURL(jsonArray.getJSONObject(x).getString("state")));
    			environment.setEnvtCountry(decodeURL(jsonArray.getJSONObject(x).getString("country")));
    			environment.setEnvtEnvironmentAdUn(decodeURL(jsonArray.getJSONObject(x).getString("environmentAdmUn")));
    			//environment.setEnvtCalendar
    			environments.add(environment);
        	}    		
		} catch (JSONException e) {			
			e.printStackTrace();
			Log.d("listFromJSONEnvironment", "Erro ao executar fromJSON");
		}    	    	
    	return environments;
    }
}
