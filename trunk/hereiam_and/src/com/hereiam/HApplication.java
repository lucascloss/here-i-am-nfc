package com.hereiam;

import android.app.Application;
import android.content.Context;

public class HApplication extends Application{

	private static Context context;
	
	public void onCreate() {
		super.onCreate();
		HApplication.context = getApplicationContext();
	}
	
	public static Context getAppContext() {
		return HApplication.context;
	}
	
	public static String getStringResource(int id) {
		return getAppContext().getResources().getString(id);
	}
}
