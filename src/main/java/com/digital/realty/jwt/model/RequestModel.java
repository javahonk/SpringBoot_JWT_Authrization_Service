package com.digital.realty.jwt.model;

import java.io.Serializable;

public class RequestModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	//need default constructor for JSON Parsing
	public RequestModel() {}

	public RequestModel(String userName, String password) {
		this.setUserName(userName);
		this.setPassword(password);
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}