package com.pankaj.model.exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class FigiExceptionHandler implements  ResponseErrorHandler  {

	@Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus status = clientHttpResponse.getStatusCode();
        return status.is4xxClientError() || status.is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        String responseAsString = toString(clientHttpResponse.getBody());

        throw new CustomException(responseAsString);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse httpResponse) throws IOException {
        String responseAsString = toString(httpResponse.getBody());
        
        if (httpResponse.getRawStatusCode() >=400 && httpResponse.getRawStatusCode()<500 ) {
            throw HttpClientErrorException.create(httpResponse.getStatusCode(), httpResponse.getStatusText(), httpResponse.getHeaders(), responseAsString.getBytes(), null); 
        }else if(httpResponse.getRawStatusCode() >=500){
            throw HttpServerErrorException.create(httpResponse.getStatusCode(), httpResponse.getStatusText(), httpResponse.getHeaders(), responseAsString.getBytes(), null);
        }else {
            throw new CustomException(responseAsString);
        }

    }

	public String toString(InputStream inputStream) {
		try (Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
			return s.hasNext() ? s.next() : "";
		}
	}

    static class CustomException extends IOException {
		private static final long serialVersionUID = 1L;

		public CustomException(String message) {
            super(message);
        }
    }

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiError> resourceNotFound(BusinessException ex) {
		ApiError response = new ApiError();
		response.setMessage("Resouce not found excepiton ...");
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(value = Exception.class)
//	public ResponseEntity<Object> handleGenerticException(Exception ex, HttpServletRequest request,
//			HttpServletResponse response) {
//		return new ResponseEntity<>("Please try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
//	}

}