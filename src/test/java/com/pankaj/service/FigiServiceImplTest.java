package com.pankaj.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.pankaj.model.AppProperties;
import com.pankaj.model.Figi;
import com.pankaj.model.FigiList;

public class FigiServiceImplTest {
	@InjectMocks
	private FigiServiceImpl figiService;

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private AppProperties app;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSearch() {
		FigiList list = new FigiList();
		list.getData().add(new Figi());
		when(app.getSearchUrl()).thenReturn("https://");
		when(restTemplate.postForObject(any(), any(), any())).thenReturn(list);
		FigiList actualList = figiService.search();
		Assert.assertNotNull(list);
	}

	@Test(expected=HttpServerErrorException.class)
	public void testError() {
		figiService.error();
	}

	@Test
	public void testSuccess() {
		String message = figiService.success("pankaj");
		assertEquals("success :pankaj", message);
	}
}
