package com.pankaj.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("figi")
public class AppProperties {

	private String searchUrl;
	private String header;
	private String key;

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public String getHeader() {
		return header;
	}

	public String getKey() {
		return key;
	}

}