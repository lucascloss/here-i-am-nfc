package com.hereiam.model;

import java.io.Serializable;
import java.sql.Date;

public class Calendar implements Serializable{

	private static final long serialVersionUID = -1077102633529658047L;
	private int calendarId;
	private String calendarEvent;
	private String calendarInfo;
	private Date calendarStarts;
	private Date calendarEnds;
	
	private Calendar() {
		
	}

	public Calendar(int calendarId, String calendarEvent, String calendarInfo,
			Date calendarStarts, Date calendarEnds) {
		super();
		this.calendarId = calendarId;
		this.calendarEvent = calendarEvent;
		this.calendarInfo = calendarInfo;
		this.calendarStarts = calendarStarts;
		this.calendarEnds = calendarEnds;
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

	public Date getCalendarStarts() {
		return calendarStarts;
	}

	public void setCalendarStarts(Date calendarStarts) {
		this.calendarStarts = calendarStarts;
	}

	public Date getCalendarEnds() {
		return calendarEnds;
	}

	public void setCalendarEnds(Date calendarEnds) {
		this.calendarEnds = calendarEnds;
	}

	
	
}
