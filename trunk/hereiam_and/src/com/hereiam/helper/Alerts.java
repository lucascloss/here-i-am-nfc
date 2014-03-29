package com.hereiam.helper;

import com.hereiam.R;

import android.app.AlertDialog;
import android.content.Context;

public class Alerts {

	// Errors
	private final static int WITHOUTCONNECTION = 1;
	private final static int WRONGUSERORPASSWORD = 2;
	private final static int INVALIDUSER = 3;
	private final static int INVALIDEMAIL = 4;
	private final static int INVALIDPASSWORD = 5;
	private final static int WITHOUTNFC = 6;
	private final static int NFCISOFF = 7;
	private final static int SERVICEISOFF = 8;
	//private final static int WSTIMEOUT = 4;
	//private final static int INVALIDEMAIL = 8;
	// private final static int NEWPASSWORDNOTCONFIRMED = 9;
	//private final static int BADREQUEST = 10;

	AlertDialog alertDialog;

	// Cria o alerta conforme o parâmetro
	public static void createErrorAlert(int typeAlert, Context context) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		switch (typeAlert) {
		case WITHOUTCONNECTION:
			alert.setTitle(R.string.alertt_without_connection);
			alert.setMessage(R.string.alertm_without_connection);
			break;
		case WRONGUSERORPASSWORD:
			alert.setTitle(R.string.alertt_wrong_user_or_password);
			alert.setMessage(R.string.alertm_wrong_user_or_password);
			break;
		case INVALIDUSER:
			alert.setTitle(R.string.alertt_invalid_user);
			alert.setMessage(R.string.alertm_invalid_user);
			break;
		case INVALIDEMAIL:
			alert.setTitle(R.string.alertt_invalid_email);
			alert.setMessage(R.string.alertm_invalid_email);
			break;
		case INVALIDPASSWORD:
			alert.setTitle(R.string.alertt_invalid_password);
			alert.setMessage(R.string.alertm_invalid_password);
			break;
		case WITHOUTNFC:
			alert.setTitle(R.string.alertt_without_nfc);
			alert.setMessage(R.string.alertm_without_nfc);
			break;
		case NFCISOFF:
			alert.setTitle(R.string.alertt_nfc_is_off);
			alert.setMessage(R.string.alertm_nfc_is_off);
			break;
		case SERVICEISOFF:
			alert.setTitle(R.string.alertt_service_is_off);
			alert.setMessage(R.string.alertm_service_is_off);
			break;
			//case WSTIMEOUT:
			//alert.setTitle(R.string.msgTimeOutTitle);
			//alert.setMessage(R.string.msgTimeOutContent);
			//break;
		
		//case BADREQUEST:
			//alert.setTitle(R.string.msgGeneralErrorTitle);
			//alert.setMessage(R.string.msgGeneralErrorContent);
			//break;
		}
		//alert.setNeutralButton(R.string.ok, null);
		alert.show();
	}

	// Cria alerta com mensagem customizada (parâmetro)
	public static void createErrorAlert(String errorMessage, Context context) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		//alert.setTitle(R.string.msgGeneralErrorTitle);
		alert.setMessage(errorMessage);
		alert.setNeutralButton(R.string.ok, null);
		alert.show();
	}
}
