package com.hereiam.controller;

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
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
            handleIntent(getIntent());
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
        handleIntent(intent);
    }
     
    private void handleIntent(Intent intent) {
    	String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
             
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {     
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NFCReader nfcReader = new NFCReader();
                nfcReaderResult = nfcReader.execute(tag);
                
                Intent navIntent = new Intent(context, MapViewController.class);
                navIntent.putExtra("NFC_WITHOUT_ROUTE", nfcReaderResult);
                startActivity(navIntent);
            } /*else {
                
            }*/
        } /*else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {             
            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();
             
            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                	NFCReader nfcReader = new NFCReader();
                    nfcReader.execute(tag);
                    Log.d(TAG, nfcReader.teste);                   
                    break;
                }
            }
        }*/
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

    /*private class NFCReaderTask extends AsyncTask<Tag, Void, String>{

    	private static final String TAG = null;

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
                    	return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }
     
            return null;
        }
         
        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            /*
             * See NFC forum specification for "Text Record Type Definition" at 3.2.1 
             * 
             * http://www.nfc-forum.org/specs/
             * 
             * bit_7 defines encoding
             * bit_6 reserved for future use, must be 0
             * bit_5..0 length of IANA language code
             */
     
            /*byte[] payload = record.getPayload();
     
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
            	Log.v(TAG, result);
            }
        }
    }*/

}