//package com.pankaj.model.exception;
//
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//
//import java.time.LocalDateTime;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.client.ResponseErrorHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class FigiExceptionHandler2 extends ResponseEntityExceptionHandler {
//
//	/**
//	 * Customize the response for HttpRequestMethodNotSupportedException.
//	 */
//	@Override
//	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		return buildResponseEntity(new ApiError(BAD_REQUEST, "method not supported exception ....", ex));
//	}
//
//	/**
//	 * Handle NoHandlerFoundException.
//	 */
//	@Override
//	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		ApiError apiError = new ApiError(BAD_REQUEST);
//		apiError.setMessage("Could not find the method" + ex.getHttpMethod());
//		return buildResponseEntity(apiError);
//	}
//
//	@ExceptionHandler(BusinessException.class)
//	public ResponseEntity<ApiError> resourceNotFound(BusinessException ex) {
//		ApiError response = new ApiError();
//		response.setMessage("Resouce not found excepiton ...");
//		response.setTimestamp(LocalDateTime.now());
//
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//	}
//
//	@ExceptionHandler(value = Exception.class)
//	public ResponseEntity<Object> handleGenerticException(Exception ex, HttpServletRequest request,
//			HttpServletResponse response) {
//		return new ResponseEntity<>("Please try after some time", HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//
//    
//	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
//		return new ResponseEntity<>(apiError, apiError.getStatus());
//	}
//
//}