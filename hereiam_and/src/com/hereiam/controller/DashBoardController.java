package com.hereiam.controller;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.controller.adapter.AlertDialogAdapter;
import com.hereiam.helper.Alerts;
import com.hereiam.helper.Validator;
import com.hereiam.model.Environment;
import com.hereiam.model.Place;
import com.hereiam.wsi.EnvironmentWSI;
import com.hereiam.wsi.PlaceWSI;

public class DashBoardController extends BaseActivity implements OnItemClickListener{

	private Context context;
	private Intent navIntent;
	
	///
	private EditText editTextSearch;
    private ListView listViewResults;
    private AlertDialog alertDialog;
    private AlertDialogAdapter alertDialogAdapter;
    private Builder alertDialogBuilder;
    private LinearLayout layout;
    private EnvironmentWSI environmentWSI;
	private ArrayList<Environment> environments;
	private ArrayList<String> listItens = new ArrayList<String>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		context = this;
        
        if(getIntent().getBooleanExtra("CREATION", false)){
        	Toast.makeText(context, getString(R.string.toast_creation), Toast.LENGTH_LONG).show();
        }	
	}
	 
	public void showMap(View view) {		
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startProgressDialog(getString(R.string.progresst_environment_list), getString(R.string.progressm_environment_list));
			createAlertDialog();
			navIntent = new Intent(this, MapViewController.class);
			navIntent.putExtra("SHOWMAP", true);			
			new SelectEnvironemntAlertDialogFeedTask().execute(1);			
		}else {
			finishProgressDialog();
			Alerts.createErrorAlert(1, context);
		}			
	}

	public void readNFC(View view) {
		if(Validator.hasInternetConnection()){
			//if(Validator.isServiceOnline())
			startActivity(new Intent(this, NFCReaderController.class));				
		}else {
			Alerts.createErrorAlert(1, context);
		}
	}

	public void listPlaces(View view) {
	
	}

	public void listFavorites(View view) {
	
	}
	
	public void startRoute(View view) {
	
	}
	
	public void listImportants(View view) {
	
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }
	    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){	        	
        	case R.id.action_logout:
        		clearPreferences();
        		startActivity(LoginAuthController.class);
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }	
    }
    
    @Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	}

    
    public void onBackPressed() {
		moveTaskToBack(true);
	}
    
    public void createAlertDialog(){
    	alertDialogBuilder = new AlertDialog.Builder(context);
    	editTextSearch = new EditText(context);
    	listViewResults = new ListView(context);
    	layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editTextSearch);
        layout.addView(listViewResults);
        ////// select especifico para cada ação
        listViewResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {				
				navIntent.putExtra("ENVIRONMENT", listItens.get(position));
				navIntent.putExtra("LATITUDE", environments.get(position).getEnvtLatitude());
				navIntent.putExtra("LONGITUDE", environments.get(position).getEnvtLongitude());
				startActivity(navIntent);
			}        	
		});
        
        alertDialogBuilder.setView(layout);	            
        alertDialogBuilder.setNegativeButton(getStringResource(R.string.cancel), new DialogInterface.OnClickListener() {	            	 
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
    
    private class SelectEnvironemntAlertDialogFeedTask extends AsyncTask<Integer, Void, Void>{

		@Override
		protected Void doInBackground(Integer... params) {
			try{
				//environmentWSI = new EnvironmentWSI();
				//environments = environmentWSI.getListEnvironment();	
				listItens.clear();
				
				PlaceWSI wsi = new PlaceWSI();
				ArrayList<Place> places = new ArrayList<Place>();
				places = wsi.getListPlace();
				
				for (int i = 0; i < places.size(); i++) {
					listItens.add(places.get(i).getPlaceName());
				}
				
				alertDialogAdapter = new AlertDialogAdapter(context, listItens);
			    listViewResults.setAdapter(alertDialogAdapter);
			}finally {
				
			}
			return null;
		}
		
		@Override
        protected void onPostExecute(Void res){
			finishProgressDialog();
	        alertDialog = alertDialogBuilder.show(); 
        }
    }

	
	
}
