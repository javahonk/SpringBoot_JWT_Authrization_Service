package com.digital.realty.jwt.model;

import java.io.Serializable;

public class ResponseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String jwttoken;

	public ResponseModel(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}