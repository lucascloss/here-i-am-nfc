package com.hereiam.model;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Environment implements Serializable{

	private static final long serialVersionUID = 4013998116759434809L;
	private int envtId;
	private String envtName;
	private String envtType;
	private String envtInfo;
	private String envtLatitude;	
	private String envtLongitude;
	private String envtStreet;
	private String envtDistrict;
	private int envtNumber;
	private String envtCity;
	private String envtState;
	private String envtCountry;
	private String envtEnvironmentAdUn;
	//private String envtIDFoursquare;
	private List<Calendar> envtCalendar;
	//private List<ReferencePoint> envtReferencePoints;
	
	public Environment() {
	
	}

	public Environment(int envtId, String envtName, String envtType,
			String envtInfo, String envtLatitude, String envtLongitude,
			String envtStreet, String envtDistrict, int envtNumber,
			String envtCity, String envtState, String envtCountry,
			String envtEnvironmentAdUn, List<Calendar> envtCalendar) {
		super();
		this.envtId = envtId;
		this.envtName = envtName;
		this.envtType = envtType;
		this.envtInfo = envtInfo;
		this.envtLatitude = envtLatitude;
		this.envtLongitude = envtLongitude;
		this.envtStreet = envtStreet;
		this.envtDistrict = envtDistrict;
		this.envtNumber = envtNumber;
		this.envtCity = envtCity;
		this.envtState = envtState;
		this.envtCountry = envtCountry;
		this.envtEnvironmentAdUn = envtEnvironmentAdUn;
		this.envtCalendar = envtCalendar;
	}

	public int getEnvtId() {
		return envtId;
	}

	public void setEnvtId(int envtId) {
		this.envtId = envtId;
	}

	public String getEnvtName() {
		return envtName;
	}

	public void setEnvtName(String envtName) {
		this.envtName = envtName;
	}

	public String getEnvtType() {
		return envtType;
	}

	public void setEnvtType(String envtType) {
		this.envtType = envtType;
	}

	public String getEnvtInfo() {
		return envtInfo;
	}

	public void setEnvtInfo(String envtInfo) {
		this.envtInfo = envtInfo;
	}

	public String getEnvtLatitude() {
		return envtLatitude;
	}

	public void setEnvtLatitude(String envtLatitude) {
		this.envtLatitude = envtLatitude;
	}

	public String getEnvtLongitude() {
		return envtLongitude;
	}

	public void setEnvtLongitude(String envtLongitude) {
		this.envtLongitude = envtLongitude;
	}

	public String getEnvtStreet() {
		return envtStreet;
	}

	public void setEnvtStreet(String envtStreet) {
		this.envtStreet = envtStreet;
	}

	public String getEnvtDistrict() {
		return envtDistrict;
	}

	public void setEnvtDistrict(String envtDistrict) {
		this.envtDistrict = envtDistrict;
	}

	public int getEnvtNumber() {
		return envtNumber;
	}

	public void setEnvtNumber(int envtNumber) {
		this.envtNumber = envtNumber;
	}

	public String getEnvtCity() {
		return envtCity;
	}

	public void setEnvtCity(String envtCity) {
		this.envtCity = envtCity;
	}

	public String getEnvtState() {
		return envtState;
	}

	public void setEnvtState(String envtState) {
		this.envtState = envtState;
	}

	public String getEnvtCountry() {
		return envtCountry;
	}

	public void setEnvtCountry(String envtCountry) {
		this.envtCountry = envtCountry;
	}

	public String getEnvtEnvironmentAdUn() {
		return envtEnvironmentAdUn;
	}

	public void setEnvtEnvironmentAdUn(String envtEnvironmentAdUn) {
		this.envtEnvironmentAdUn = envtEnvironmentAdUn;
	}

	public List<Calendar> getEnvtCalendar() {
		return envtCalendar;
	}

	public void setEnvtCalendar(List<Calendar> envtCalendar) {
		this.envtCalendar = envtCalendar;
	}
}