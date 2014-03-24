package com.hereiam.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;

import com.hereiam.wsi.rest.BaseWSI;
import com.hereiam.HApplication;
import com.hereiam.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.EditText;


public class Validator {

	// Verifica se o campo (parâmetro) está Vazio ou Nulo
	public static boolean isNullOrEmpty(EditText editText) {
		boolean notOk = false;
		if (editText.getText().toString().equals("")) {
			editText.setError(HApplication.getStringResource(R.string.required_field));
			editText.requestFocus();
			notOk = true;
		}
		return notOk;
	}

	// Verifica se os campos (parâmentro) está Vazio ou Nulo
	public static boolean isNullOrEmpty(EditText... editTexts) {
		boolean notOk = false;
		for (EditText editText : editTexts) {
			if (isNullOrEmpty(editText)) {
				notOk = true;
				return true;
			}
		}
		return notOk;
	}

	// Verifica se a senha confirma
	public static boolean checkPassword(EditText editText1, EditText editText2) {
		if (editText1.getText().toString().equals(editText2.getText().toString())) {
			return true;
		} else {
			return false;
		}
	}

	// Verifica se o e-mail, é um e-mail válido
	public static boolean checkEmailAddress(String email) {
		boolean isValid = false;

		final String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		final CharSequence inputStr = email;

		final Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	// Verifica se o dispositivo está conectado a Internet
	public static boolean hasInternetConnection() {
		final ConnectivityManager cm = (ConnectivityManager) HApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isServiceOnline(){
		BaseWSI baseWSI = new BaseWSI();
		if(baseWSI.testService() == 200){
			return true;
		}
		return false;
	}
}
