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
			alert.setTitle(R.string.alertt_withoutconnection);
			alert.setMessage(R.string.alertm_withoutconnection);
			break;
		case WRONGUSERORPASSWORD:
			alert.setTitle(R.string.alertt_wronguserorpassword);
			alert.setMessage(R.string.alertm_wronguserorpassword);
			break;
		case INVALIDUSER:
			alert.setTitle(R.string.alertt_invaliduser);
			alert.setMessage(R.string.alertm_invalidauser);
			break;
		case INVALIDEMAIL:
			alert.setTitle(R.string.alertt_invalidemail);
			alert.setMessage(R.string.alertm_invalidemail);
			break;
		case INVALIDPASSWORD:
			alert.setTitle(R.string.alertt_invalidpassword);
			alert.setMessage(R.string.alertm_invalidpassword);
			break;
		case WITHOUTNFC:
			alert.setTitle(R.string.alertt_withoutnfc);
			alert.setMessage(R.string.alertm_withoutnfc);
			break;
		case NFCISOFF:
			alert.setTitle(R.string.alertt_nfcisoff);
			alert.setMessage(R.string.alertm_nfcisoff);
			break;
		case SERVICEISOFF:
			alert.setTitle(R.string.alertt_serviceisoff);
			alert.setMessage(R.string.alertm_serviceisoff);
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
