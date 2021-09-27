package com.pankaj.model.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String error;

	public BusinessException(String error) {
		super(error);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
