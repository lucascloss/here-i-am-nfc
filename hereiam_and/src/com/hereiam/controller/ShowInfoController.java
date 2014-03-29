package com.hereiam.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;

public class ShowInfoController extends BaseActivity{
	
	private Context context;
	private TextView showName;
	private String name;
	private TextView showInfo;
	private String info;
	private LinearLayout linearLayoutName;
	private Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		context = this;
		
		if(getIntent().hasExtra("ENVIRONMENT")){
			name = getIntent().getStringExtra("ENVIRONMENT");
			info = getIntent().getStringExtra("INFO");
		}
		
		if(getIntent().hasExtra("PLACE")){
			name = getIntent().getStringExtra("PLACE");
			info = getIntent().getStringExtra("INFO");
		}
		getReferences();
		
        
	}
	
	public void getReferences(){
		showName = (TextView) findViewById(R.id.info_txt_name);
		showInfo = (TextView) findViewById(R.id.info_txt_info);
		linearLayoutName = (LinearLayout) findViewById(R.id.linear_layout_name);
		
		showName.setText(name);
		showInfo.setText(info);
		if(getIntent().hasExtra("ENVIRONMENT")){
			linearLayoutName.setBackgroundResource(R.drawable.color_environment);
		}else {
			linearLayoutName.setBackgroundResource(R.drawable.color_place);
		}
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
		getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }
	    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){	        	        	
        case R.id.action_add_favorite:
        	return true;
        case R.id.action_delete_favorite:
        	return true;
        case R.id.action_show_calendar:        	
        	return true;
        case R.id.action_logout:
        	return true;
	        default:
        		return super.onOptionsItemSelected(item);
        }	
    }
}
