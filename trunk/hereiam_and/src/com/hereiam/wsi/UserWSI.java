package com.hereiam.wsi;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hereiam.model.User;
import com.hereiam.wsi.rest.BaseWSI;

public class UserWSI extends BaseWSI{

	public static final String USER_URI = URI + "user"; 
	public static final String USER_LIST_URI = USER_URI + "/list";
	public static final String USER_FIND_URI = USER_URI + "/find?";
	public static final String USER_CREATE_URI = USER_URI + "/create?";
	public static final String USER_DELETE_URI = USER_URI + "/delete?";
    
    public User getUser(String userName) {        
        httpClient = getHttpClient();
        String result = null;      
        User user = new User();
        try {
        	HttpGet httpGet = new HttpGet(USER_FIND_URI + "username=" + userName);            
        	HttpResponse response = httpClient.execute(httpGet);
        	InputStream resultStream = response.getEntity().getContent();
        	
        	result = convertInputStreamToString(resultStream);
            
        	JSONObject jsonObject = new JSONObject(result);  
        	user = userFromJSON(jsonObject);
        	   	        
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
}


