package com.pankaj.model;

import java.io.Serializable;

public class Search implements Serializable{
	private static final long serialVersionUID = 1L;
	private String query;
	private String exchCode;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getExchCode() {
		return exchCode;
	}

	public void setExchCode(String exchCode) {
		this.exchCode = exchCode;
	}

}
