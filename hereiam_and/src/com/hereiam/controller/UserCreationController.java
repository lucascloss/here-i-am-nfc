package com.hereiam.controller;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.helper.Alerts;
import com.hereiam.helper.Security;
import com.hereiam.helper.Validator;
import com.hereiam.model.User;
import com.hereiam.wsi.UserWSI;

public class UserCreationController extends BaseActivity implements OnClickListener, Runnable{
	
	private Context context;
	final private Handler handler = new Handler();
	
	private EditText name;
	private EditText userName;
	private EditText email;
	private EditText password;
	private EditText confPassword;
	private Button loginSend;
	private String cryptPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		context = this;
				
		setContentView(R.layout.activity_usercreation);
		getReferences();
					
	}
	
	public void getReferences(){
		name = (EditText) findViewById(R.id.createUserETxtName);
		userName = (EditText) findViewById(R.id.createUserETxtUser);
		email = (EditText) findViewById(R.id.createUserETxtEmail);
		password = (EditText) findViewById(R.id.createUserETxtPassword);
		confPassword = (EditText) findViewById(R.id.createUserETxtConfirmPassword);
		loginSend = (Button) findViewById(R.id.createUserBtSend);
		loginSend.setOnClickListener(this);				
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.createUserBtSend:
			if(!Validator.hasInternetConnection()){			
				Alerts.createErrorAlert(1, context);
				return;
			}
			
			/*if(!Validator.isServiceOnline()){
				Alerts.createErrorAlert(8, context);
				return;
			}*/

			final EditText[] values = {name, userName, email, password, confPassword};
			if(Validator.isNullOrEmpty(values)) {	
				return;
			}

			if(!Validator.checkEmailAddress(email.getText().toString())) {
				Alerts.createErrorAlert(4, this);
				return;
			}

			if(!Validator.checkPassword(password, confPassword)) {
				clearPassword();
				Alerts.createErrorAlert(5, this);
				return;
			}
			startProgressDialog(getString(R.string.progresst_creation), getString(R.string.progressm_creation));
			new Thread(this).start();
			break;			
		}
	}
	
	@Override
	public void run() {
		try {
			UserWSI userWSI = new UserWSI();
			User user = null;			    	
			cryptPassword = Security.encrypt(Security.SEED, password.getText().toString());
    		user = userWSI.createUser(encodeUrl(name.getText().toString()), encodeUrl(userName.getText().toString()), encodeUrl(email.getText().toString()), encodeUrl(password.getText().toString()));
    		
    		if(user.getId() == 0){
    			handler.post(new Runnable(){
					@Override
					public void run() {
						Alerts.createErrorAlert(3, context);
					}   					
    			});    			
    		}else {    			
				finishProgressDialog();    				
				savePreferences(user.getId(), user.getName(), user.getPassword());				
				handler.post(new Runnable(){
					@Override
					public void run() {
						Intent navIntent = new Intent(context, DashBoardController.class);
						navIntent.putExtra("CREATION", true);						
						startActivity(navIntent);
					}									
				});
			}        		
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
    		finishProgressDialog();
    	}
	}

	@Override
	public void onPause() {
		super.onPause();
		finishProgressDialog();
	}
	
	public void clearPassword() {
		password.setText("");
		confPassword.setText("");
		password.requestFocus();
	}
}
