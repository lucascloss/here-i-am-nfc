package com.hereiam.wsi.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;


public class BaseWSI {

	//protected static final String URI = "http://locahost:8080/hereiamwse/";
	protected static final String URI = "http://ec2-54-186-1-128.us-west-2.compute.amazonaws.com:8080/hereiamwse/"; 
	protected static final int HTTP_TIME_OUT = 60*1000;
	protected static HttpClient httpClient;  
	
	protected static HttpClient getHttpClient(){
        if(httpClient == null){
            httpClient = new DefaultHttpClient(); 
            final HttpParams httpParams = httpClient.getParams(); 
            HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIME_OUT);
            HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIME_OUT);
            ConnManagerParams.setTimeout(httpParams, HTTP_TIME_OUT);
        }
        return httpClient;
    }
	
	public String decodeURL(String string){
		try {
			string = URLDecoder.decode(string, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		return string;
	}
	
	public String encodeURL(String string){
		try {
			string = URLEncoder.encode(string, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		return string;
	}
	
	protected static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }
        inputStream.close();
        return result;
    }
	
	public static int testService(){
		httpClient = new DefaultHttpClient();
		int result = 0;
		try {
			HttpGet httpGet = new HttpGet(URI);						
			HttpResponse response = httpClient.execute(httpGet);
			result = response.getStatusLine().getStatusCode();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return result;
	}
}
