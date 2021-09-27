package com.pankaj.service;

import org.springframework.web.client.RestClientException;

import com.pankaj.model.FigiList;

public interface FigiService {

	FigiList search() throws RestClientException;

	String error();

	String success(String message);

}