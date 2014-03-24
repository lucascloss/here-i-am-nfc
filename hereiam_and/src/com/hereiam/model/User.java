package com.hereiam.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	private static final long serialVersionUID = 7571639809620033897L;
	private int id;
	private String name;
	private String userName;
	private String email;	
	private String password;
	private List<Place> favoritePlaces;
	
	public User() {
		
	}

	public User(String name, String userName, String email, String password,
			List<Place> favoritePlaces) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.favoritePlaces = favoritePlaces;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Place> getFavoritePlaces() {
		return favoritePlaces;
	}

	public void setFavoritePlaces(List<Place> favoritePlaces) {
		this.favoritePlaces = favoritePlaces;
	}
	
	public boolean isEmpty(User user){
		boolean result = false;
		
		if((user.getId()==0) && (user.getName().toString().equals("null"))&& (user.getUserName().toString().equals("null")) && (user.getEmail().toString().equals("null")) 
				&& (user.getPassword().toString().equals("null"))){					
			result = true;
		}
		return result;
	}
}
