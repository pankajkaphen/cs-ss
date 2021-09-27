package com.pankaj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pankaj.model.Figi;
import com.pankaj.model.FigiList;
import com.pankaj.model.Search;
import com.pankaj.model.exception.BusinessException;
import com.pankaj.service.FigiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FigiController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FigiController.class);

	@Autowired
	private FigiService figiService;
	
	@PostMapping("/api/v1/search/{id}")
	@ApiOperation(value = "Return Nifty50 figi details")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "app-id", value = "figi", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "figi") })
	@Cacheable(cacheNames = "figiCache",key="#id")
	public ResponseEntity<FigiList> search(@PathVariable String id) {
		LOGGER.info("Request received for /api/v1/search");
//		try {
			return new ResponseEntity<>(figiService.search(), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new FigiList(e.getMessage() + " : Please try again with correct input"), HttpStatus.BAD_REQUEST);
//		}
	}
	
	@GetMapping("/api/v1/success/{message}")
	@ApiOperation(value = "getMessage")
	@ApiImplicitParams({
		  @ApiImplicitParam(name = "app-id", value = "figi", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "figi")
		})
	public ResponseEntity<String> getMessage(@PathVariable String message) {
		LOGGER.info("Request received for /api/v1/success input: {}", message);
		return new ResponseEntity<>(figiService.success(message), HttpStatus.OK);
	}
	
	@PostMapping("/api/v1/error")
	@ApiOperation(value = "getError")
	@ApiImplicitParams({
		  @ApiImplicitParam(name = "app-id", value = "figi", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "figi")
		})
	public ResponseEntity<Object> getError(@RequestBody Figi figi) throws BusinessException, HttpRequestMethodNotSupportedException {
		LOGGER.info("Request received for /api/v1/error input: {}", figi);
		return new ResponseEntity<>(figiService.error(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/")
	public String swaggerUi() {
		LOGGER.info("Request received for /swagger-ui.html");
		return "redirect:/swagger-ui.html";
	}
	
	@PostMapping("/api/v2/search")
	@ApiOperation(value = "search")
	@ApiImplicitParams({
		  @ApiImplicitParam(name = "app-id", value = "figi", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "figi")
		})
	public ResponseEntity<Object> serchQuery(@RequestBody Search search) {
		LOGGER.info("Request received for /api/v2/search input: {}", search);
		return new ResponseEntity<>(figiService.search(search), HttpStatus.OK);
	}
}
