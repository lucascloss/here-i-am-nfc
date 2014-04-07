package com.hereiam.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.helper.Alerts;
import com.hereiam.helper.Security;
import com.hereiam.helper.Validator;
import com.hereiam.model.Environment;
import com.hereiam.model.User;
import com.hereiam.wsi.EnvironmentWSI;
import com.hereiam.wsi.UserWSI;

public class LoginAuthController extends BaseActivity implements OnClickListener, Runnable{

	private Context context;
	final private Handler handler = new Handler();
	
	private EditText loginName;
	private EditText loginPassword;
	private Button loginSend;
	private TextView loginNew;
	private String decrypt;
	private String password;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		context = this;
		
		if (isLogged()){
			startActivity(DashBoardController.class);
		} else {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_loginauth);
			getReferences();
		}					
	}
	
	public void getReferences(){
		loginName = (EditText) findViewById(R.id.loginName);
		loginPassword = (EditText) findViewById(R.id.loginPassword);
		
		loginSend = (Button) findViewById(R.id.loginSend);
		loginSend.setOnClickListener(this);
		
		loginNew = (TextView) findViewById(R.id.loginNew);
		loginNew.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginSend:
			final EditText[] values = {loginName, loginPassword};
			
			if (Validator.isNullOrEmpty(values)) {	
				return;
			}
			
			if(Validator.hasInternetConnection()){			
				//if(Validator.isServiceOnline())
				startProgressDialog(getString(R.string.progresst_login), getString(R.string.progressm_login));
				new Thread(this).start();
			}else {
				Alerts.createErrorAlert(1, context);
			}
			break;			
		case R.id.loginNew:
			startActivity(UserCreationController.class);
			break;
		}
	}
	
	@Override
	public void run() {			
		try {					
			UserWSI userWSI = new UserWSI();
			User user = null;			    	
    		user = userWSI.getUser(encodeUrl(loginName.getText().toString()));
    		
    		if(user.getId() == 0){
    			handler.post(new Runnable(){
					@Override
					public void run() {
						Alerts.createErrorAlert(2, context);
					}   					
    			});    			
    		}else {
    			password = user.getPassword();
    			decrypt = Security.decipher(Security.secret, password);
    			if(decrypt.equals(loginPassword.getText().toString())){
    				finishProgressDialog();    				
    				savePreferences(user.getId(), user.getName(), user.getPassword());
    				startActivity(DashBoardController.class);
    			}else {
    				handler.post(new Runnable(){
    					@Override
    					public void run() {
    						Alerts.createErrorAlert(2, context);
    					}   					
        			}); 
    			}
    		}    		
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
    		finishProgressDialog();
    	}
	}
	
	public boolean isLogged(){
		SharedPreferences preferences = this.getSharedPreferences("INFO", MODE_PRIVATE);		
		boolean isLogged = preferences.getBoolean("islogged", false);
		if (isLogged) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		finishProgressDialog();
	}

	public void onBackPressed() {
		moveTaskToBack(true);
	}
	
	// Validate the textfields
	private boolean fieldsValidate() {
		final EditText[] values = {loginName, loginName};
		if (Validator.isNullOrEmpty(values)) {
			return false;
		} else {
			return true;
		}
	}

	
}
