package com.hereiam.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.helper.Alerts;
import com.hereiam.helper.NFCReader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class NFCReaderController extends BaseActivity{
	
	private Context context;
	
	public static final String MIME_TEXT_PLAIN = "text/plain";        
    private NfcManager nfcManager;
    private NfcAdapter nfcAdapter;
    private String nfcReaderResult;
    private Intent navIntent;
    private boolean update;
    private Intent mapIntent;
    
    private SharedPreferences preferences; 
	private SharedPreferences.Editor editor;    
    private JSONObject json;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        
        context = this;
        preferences = getApplicationContext().getSharedPreferences("STATE", MODE_PRIVATE);
        nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        nfcAdapter = nfcManager.getDefaultAdapter();
        update = getIntent().hasExtra("UPDATE");
        mapIntent = getIntent();
        //nfcAdapter = NfcAdapter.getDefaultAdapter(this);
                
        if (nfcAdapter == null) {
    		Alerts.createErrorAlert(6, context);
            finish();
            return;
        }
     
        if (!nfcAdapter.isEnabled()) {
    		Alerts.createErrorAlert(7, context);
        } else {
        	startProgressDialog(getString(R.string.progresst_nfc), getString(R.string.progressm_nfc));
        	resolveIntent(getIntent());
        }                
    }
     
    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, nfcAdapter);
    }
     
    @Override
    protected void onPause() {
        stopForegroundDispatch(this, nfcAdapter);
        finishProgressDialog(); 
        super.onPause();
    }
     
    @Override
    protected void onNewIntent(Intent intent) { 
    	setIntent(intent);
        resolveIntent(intent);
    }
     
    void resolveIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
        	Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NFCReader nfcReader = new NFCReader();
            nfcReaderResult = nfcReader.execute(tag);            
    		goDetails(nfcReaderResult);            
        }
    }
    
    public void goDetails(String nfcId){
    	navIntent = new Intent(this, MapViewController.class);
        navIntent.putExtra("NFC", true);
        navIntent.putExtra("NFC_ID", nfcId);
        if(mapIntent.hasExtra("UPDATE_ROUTE_NFC")){
        	navIntent.putExtra("UPDATE_ROUTE_NFC", mapIntent.getBooleanExtra("UPDATE_ROUTE_NFC", true));
        }
        if(mapIntent.hasExtra("UPDATE_POSITION_NFC")){
        	navIntent.putExtra("UPDATE_POSITION_NFC", mapIntent.getBooleanExtra("UPDATE_POSITION_NFC", true));
        }
//        this.finish();
        
        startActivity(navIntent);
        
        this.finish();
    }
         
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	finishProgressDialog();
    }
    
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 
        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
 
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};
 
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
        
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }
 
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }
}