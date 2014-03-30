package com.hereiam.model;

import java.io.Serializable;

public class FavoritePlace implements Serializable{
	
	private static final long serialVersionUID = -208896070476633944L;
	private int fpId;
	private int userId;
	private int placeId;
	
	public FavoritePlace() {
		
	}

	public FavoritePlace(int fpId, int userId, int placeId) {
		super();
		this.fpId = fpId;
		this.userId = userId;
		this.placeId = placeId;
	}

	public int getFpId() {
		return fpId;
	}
	
	public void setFpId(int fpId) {
		this.fpId = fpId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getPlaceId() {
		return placeId;
	}
	
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
}
