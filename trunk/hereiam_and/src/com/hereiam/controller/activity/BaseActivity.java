package com.hereiam.controller.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BaseActivity extends Activity{
	
	private ProgressDialog progressDialog;
	private Intent nav;
	
	public BaseActivity() {

	}
	
	protected void startProgressDialog(final String headerMessage, final String message) {
		progressDialog = ProgressDialog.show(this, headerMessage, message, false, true);
	}
	
	protected void finishProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
	protected String getStringResource(int id) {
		return this.getResources().getString(id);
	}
	
	// Salva as credenciais do usuário em SharedPreferences
	protected void savePreferences(final int userId, final String userName, final String password) {
		SharedPreferences preferences = this.getSharedPreferences("INFO", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("userid", userId);
		editor.putString("usernama", userName);
		editor.putString("password", password);
		editor.putBoolean("islogged", true);
		editor.commit();		
	}
	
	// Salva as credenciais do usuário em SharedPreferences
	protected void clearPreferences() {
		SharedPreferences preferences = this.getSharedPreferences("INFO", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("userid", 0);
		editor.putString("usernama", "");
		editor.putString("password", "");
		editor.putBoolean("islogged", false);
		editor.commit();		
	}
	
	// Salva a senha do usuário
	protected void savePassword() {
		
	}		
	
	public void startActivity(@SuppressWarnings("rawtypes") Class activityClass) {
		final Intent nav = new Intent(this, activityClass);
		this.startActivity(nav);
	}
	
	public String encodeUrl(String string) throws UnsupportedEncodingException{
		return URLEncoder.encode(string, "ISO-8859-1");
	}
	
}
