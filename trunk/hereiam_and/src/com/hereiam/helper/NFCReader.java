package com.hereiam.helper;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.hereiam.R;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

public class NFCReader{

	private static final String TAG = null;	

    public String execute(Tag... params) {
        Tag tag = params[0];
         
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
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
        byte[] payload = record.getPayload();
 
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
 
        int languageCodeLength = payload[0] & 0063;
         
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }
    
    public static NdefMessage[] getNdefMessages(Intent intent) {
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
            Log.d(TAG, "Unknown intent.");
        }
        return msgs;
    }
}
