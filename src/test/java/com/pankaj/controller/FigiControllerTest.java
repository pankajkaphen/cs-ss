package com.pankaj.controller;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pankaj.model.Figi;
import com.pankaj.model.FigiList;
import com.pankaj.service.FigiServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FigiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FigiServiceImpl figiService;
    @InjectMocks
    private FigiController figiController;
    
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(figiController).build();
    }
	@Test
	public void testSearchSuccess() throws Exception {
        FigiList list = new FigiList();
	    list.getData().add(new Figi());
	    
        when(figiService.search()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/search/nifty50")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetMessage() throws Exception {
        when(figiService.success("hello")).thenReturn("success :hello");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/success/hello")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetError() throws Exception {
        when(figiService.error()).thenReturn("error");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/error")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Figi())))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
	}

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
