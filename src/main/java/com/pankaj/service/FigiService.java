package com.pankaj.service;

import org.springframework.web.client.RestClientException;

import com.pankaj.model.FigiList;
import com.pankaj.model.Search;

public interface FigiService {

	FigiList search() throws RestClientException;

	String error();

	String success(String message);

	FigiList search(Search search);

}