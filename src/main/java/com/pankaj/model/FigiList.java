package com.pankaj.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FigiList implements Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "FigiList [data=" + data + ", error=" + error + "]";
	}

	private List<Figi> data;
	private String error;

	public FigiList() {
		data= new ArrayList<>();
	}

	public FigiList(String error) {
	}

	public FigiList(List<Figi> data) {
		this.data = data;
	}

	public List<Figi> getData() {
		return data;
	}

	public void setData(List<Figi> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	

}
