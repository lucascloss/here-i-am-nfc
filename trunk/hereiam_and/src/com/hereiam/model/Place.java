package com.hereiam.model;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Place implements Serializable{

	private static final long serialVersionUID = 3281745057696231251L;
	private int placeId;
	private String placeName;	
	private String placeType;
	private String placeIdNfc;
	private String placeInfo;
	private String placeLatitude;
	private String placeLongitude;
	private String placeEnvironmentName;
	private String placePlaceAdmUn;
	private boolean important;		
	//private String placeIDFoursquare;	
	private List<Calendar> placeCalendar;	
	
	public Place () {
	}

	public Place(int placeId, String placeName, String placeType,
			String placeIdNfc, String placeInfo, String placeLatitude,
			String placeLongitude, String placeEnvironmentName,
			String placePlaceAdmUn, boolean important,
			List<Calendar> placeCalendar) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.placeType = placeType;
		this.placeIdNfc = placeIdNfc;
		this.placeInfo = placeInfo;
		this.placeLatitude = placeLatitude;
		this.placeLongitude = placeLongitude;
		this.placeEnvironmentName = placeEnvironmentName;
		this.placePlaceAdmUn = placePlaceAdmUn;
		this.important = important;
		this.placeCalendar = placeCalendar;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getPlaceIdNfc() {
		return placeIdNfc;
	}

	public void setPlaceIdNfc(String placeIdNfc) {
		this.placeIdNfc = placeIdNfc;
	}

	public String getPlaceInfo() {
		return placeInfo;
	}

	public void setPlaceInfo(String placeInfo) {
		this.placeInfo = placeInfo;
	}

	public String getPlaceLatitude() {
		return placeLatitude;
	}

	public void setPlaceLatitude(String placeLatitude) {
		this.placeLatitude = placeLatitude;
	}

	public String getPlaceLongitude() {
		return placeLongitude;
	}

	public void setPlaceLongitude(String placeLongitude) {
		this.placeLongitude = placeLongitude;
	}

	public String getPlaceEnvironmentName() {
		return placeEnvironmentName;
	}

	public void setPlaceEnvironmentName(String placeEnvironmentName) {
		this.placeEnvironmentName = placeEnvironmentName;
	}

	public String getPlacePlaceAdmUn() {
		return placePlaceAdmUn;
	}

	public void setPlacePlaceAdmUn(String placePlaceAdmUn) {
		this.placePlaceAdmUn = placePlaceAdmUn;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public List<Calendar> getPlaceCalendar() {
		return placeCalendar;
	}

	public void setPlaceCalendar(List<Calendar> placeCalendar) {
		this.placeCalendar = placeCalendar;
	}
}