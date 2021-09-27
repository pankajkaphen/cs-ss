package com.pankaj.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pankaj.model.AppProperties;
import com.pankaj.model.FigiList;
import com.pankaj.model.exception.FigiExceptionHandler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class FigiServiceImpl implements FigiService {
    private static final String BACKEND_FIGI = "backendFigi";
	private static final Logger LOGGER = LoggerFactory.getLogger(FigiServiceImpl.class);

	private static final HttpEntity<String> HTTP_SEARCH_ENTITY = getEntity();
	
	@Autowired
	private AppProperties app;

	private RestTemplate restTemplate;

	static {
		disableSslVerification();
	}

    @Override
	@CircuitBreaker(name = BACKEND_FIGI)
    @Retry(name = BACKEND_FIGI)
	public FigiList search() throws RestClientException {
		LOGGER.info("Inside search method");
		return this.restTemplate.postForObject(app.getSearchUrl(), HTTP_SEARCH_ENTITY, FigiList.class);
	}

    @Override
	@CircuitBreaker(name = BACKEND_FIGI)
    @Retry(name = BACKEND_FIGI)
    public String error() {
		LOGGER.info("Inside error method");
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "This is a remote exception");
    }
    
    @Override
	@CircuitBreaker(name = BACKEND_FIGI)
    @Retry(name = BACKEND_FIGI)
    public String success(String message) {
		LOGGER.info("Inside search method input {}", message);
        return "success :"+message;
    }
    
	private static HttpEntity<String> getEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>("{\"query\":\"NIFTY50\", \"exchCode\":\"IN\"}", headers);
		return entity;
	}

	private static void disableSslVerification() {
		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

	public FigiServiceImpl() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(getSSLContext(), new NoopHostnameVerifier());
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		this.restTemplate = new RestTemplateBuilder()
				.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
				.errorHandler(new FigiExceptionHandler()).build();
         
// 		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
// 		requestFactory.setHttpClient(httpClient);
//		this.restTemplate = new RestTemplate(requestFactory);
	}

	private SSLContext getSSLContext() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
						throws CertificateException {

				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			return sc;
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}
}
