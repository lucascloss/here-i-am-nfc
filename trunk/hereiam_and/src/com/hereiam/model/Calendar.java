package com.hereiam.model;

import java.io.Serializable;
import java.sql.Date;

public class Calendar implements Serializable{

	private static final long serialVersionUID = -1077102633529658047L;
	private int calendarId;
	private String calendarEvent;
	private String calendarInfo;
	private String calendarStarts;
	private String calendarEnds;
	private int environmentId;
	private int placeId;
	
	public Calendar() {
		
	}

	public Calendar(int calendarId, String calendarEvent, String calendarInfo,
			String calendarStarts, String calendarEnds, int environmentId,
			int placeId) {
		super();
		this.calendarId = calendarId;
		this.calendarEvent = calendarEvent;
		this.calendarInfo = calendarInfo;
		this.calendarStarts = calendarStarts;
		this.calendarEnds = calendarEnds;
		this.environmentId = environmentId;
		this.placeId = placeId;
	}

	public int getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public String getCalendarEvent() {
		return calendarEvent;
	}

	public void setCalendarEvent(String calendarEvent) {
		this.calendarEvent = calendarEvent;
	}

	public String getCalendarInfo() {
		return calendarInfo;
	}

	public void setCalendarInfo(String calendarInfo) {
		this.calendarInfo = calendarInfo;
	}

	public String getCalendarStarts() {
		return calendarStarts;
	}

	public void setCalendarStarts(String calendarStarts) {
		this.calendarStarts = calendarStarts;
	}

	public String getCalendarEnds() {
		return calendarEnds;
	}

	public void setCalendarEnds(String calendarEnds) {
		this.calendarEnds = calendarEnds;
	}

	public int getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(int environmentId) {
		this.environmentId = environmentId;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}	
}
