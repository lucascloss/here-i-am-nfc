package com.hereiam.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.hereiam.R;
import com.hereiam.controller.activity.BaseActivity;
import com.hereiam.helper.Alerts;
import com.hereiam.helper.NFCReader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    public TextView mTextView;
    private NfcManager nfcManager;
    private NfcAdapter nfcAdapter;
    private String nfcReaderResult;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        
        context = this;
        nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        nfcAdapter = nfcManager.getDefaultAdapter();
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
            //handleIntent(getIntent());    
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
        //handleIntent(intent);
    	setIntent(intent);
        resolveIntent(intent);
    }
     
    void resolveIntent(Intent intent) {
        // Parse the intent
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
        	Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NFCReader nfcReader = new NFCReader();
            nfcReaderResult = nfcReader.execute(tag);
        	//NdefMessage[] messages = getNdefMessages(getIntent());
            //byte[] payload = messages[0].getRecords()[0].getPayload();
            //String id = new String(payload);
            goDetails(nfcReaderResult);
        }
    }
    
    public void goDetails(String id){
    	final Intent intent = new Intent(this, MapViewController.class);
    	//int id_int = Integer.valueOf(id);
        intent.putExtra("NFC", id);
        //startActivity(intent);
        Toast.makeText(context, id, Toast.LENGTH_LONG).show();
        this.finish();
        startActivity(intent);
    }
    
    NdefMessage[] getNdefMessages(Intent intent) {
        // Parse the intent
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
            || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = 
                intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = 
                    new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {
                    record
                });
                msgs = new NdefMessage[] {
                    msg
                };
            }
        } else {
            
            finish();
        }
        return msgs;
    }
    
    
    private void handleIntent(Intent intent) {
    	String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
             
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {     
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                //NFCReader nfcReader = new NFCReader();
                //nfcReaderResult = nfcReader.execute(tag);
                new NFCReaderTask().execute(tag);
                
                /*Intent navIntent = new Intent(context, MapViewController.class);
                navIntent.putExtra("NFC", nfcReaderResult);
                finishProgressDialog();
                startActivity(navIntent);*/
            } /*else {
                
            }*/
        }
    }
         
    @Override
    public void onDestroy(){
    	//finishProgressDialog();
    	super.onDestroy();
    	finishProgressDialog();
    }
    
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 
        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
 
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};
 
        // Notice that this is the same filter as in our manifest.
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

    private class NFCReaderTask extends AsyncTask<Tag, Void, String>{

    	@Override
    	protected void onPreExecute() {
    		// inicia progressDialog
    	}
    	
    	@Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];
             
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag. 
                return null;
            }
     
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
     
            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                    	nfcReaderResult = readText(ndefRecord);
                    	return nfcReaderResult;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
     
            return null;
        }
         
        private String readText(NdefRecord record) throws UnsupportedEncodingException {
                            
            byte[] payload = record.getPayload();
     
            // Get the Text Encoding
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
     
            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;
             
            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"
             
            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }
         
        @Override 	
        protected void onPostExecute(String result) {
            
        	// finaliza progressDialog
        	if (result != null) {
                //mTextView.setText("Read content: " + result);
        		startMap(result);
            	
            }
        }
    }

    public void startMap(String string){
    	Intent navIntent = new Intent(context, MapViewController.class);
        navIntent.putExtra("NFC", nfcReaderResult);
        finishProgressDialog();
        
        startActivity(navIntent);
        this.finish();
    }
    
}