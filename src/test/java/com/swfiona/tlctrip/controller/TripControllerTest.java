package com.swfiona.tlctrip.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swfiona.tlctrip.model.FHVTrip;
import com.swfiona.tlctrip.model.Result;
import com.swfiona.tlctrip.model.Trip;
import com.swfiona.tlctrip.service.TripService;
import com.swfiona.tlctrip.service.ZoneService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value=TripController.class)
public class TripControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    private TripService tripSvc;
	
	@MockBean
    private ZoneService zoneSvc;
	
	MultiValueMap<String, String> paraMap = new LinkedMultiValueMap<>();
	
	@Before
	public void init() {
		paraMap.add("vehicle", "fhv");
	    paraMap.add("fromBorough", "Queens");
	    paraMap.add("toBorough", "Queens");
	    paraMap.add("fromTime", "2018-01-09T07:00:00");
	    paraMap.add("toTime", "2018-01-10T07:00:00");
	}
	
	@Test
	public void testBadRequest() throws Exception {
		mockMvc.perform(get("/trips")
				.accept(MediaType.ALL))
        		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testBoroughNotFound() throws Exception {
		when(zoneSvc.getLocationId(any(String.class))).thenReturn(null);
		MvcResult resp = mockMvc.perform(get("/trips")
				.params(paraMap)
				.accept(MediaType.ALL))
        		.andExpect(status().isNotFound())
        		.andReturn();
		String contentAsString = resp.getResponse().getContentAsString();
		assertTrue(contentAsString.contains(
				"{\"status\":\"404 NOT_FOUND\",\"message\":\"Queens Not Found\","
				+ "\"time\":"));
	}
	
	@Test
	public void testInvalidVehicle() throws Exception {
		paraMap.remove("vehicle");
		paraMap.add("vehicle", "none");
		MvcResult resp = mockMvc.perform(get("/trips")
				.params(paraMap)
				.accept(MediaType.ALL))
        		.andExpect(status().isNotFound())
        		.andReturn();
		String contentAsString = resp.getResponse().getContentAsString();
		assertTrue(contentAsString.contains(
				"{\"status\":\"404 NOT_FOUND\","
				+ "\"message\":\"Invalid vehicle type none! Valid vehicles are FHV, YELLOW and GREEN.\","
				+ "\"time\":"));
	}
	
	@Test
	public void getTripOk() throws Exception {
		List<Integer> zones = new ArrayList<>();
		zones.add(99);
		zones.add(255);
		List<Trip> results = new ArrayList<>();
		FHVTrip trip = new FHVTrip();
		trip.setDispatchNum("B09480");
		trip.setDropOffLoc(99);
		trip.setPickUpLoc(255);
		trip.setPickUpTime("2018-01-09T07:00:00");
		trip.setDropOffTime("2018-01-11T07:00:00");
		trip.setSrFlag("1");
		results.add(trip);
		when(zoneSvc.getLocationId(any(String.class))).thenReturn(zones);
		when(tripSvc.getTripData(any(String.class), any(List.class), any(List.class), 
				any(Date.class), any(Date.class))).thenReturn(results);
		
		MvcResult result = mockMvc.perform(get("/trips")
				.params(paraMap)
				.accept(MediaType.ALL))
        		.andExpect(status().isOk())
        		.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		assertEquals(contentAsString, "{\"total\":1,\"tripResults\":[{\"pickUpLoc\":255,\"dropOffLoc\":99,"
				+ "\"pickUpTime\":\"2018-01-09T07:00:00\",\"dropOffTime\":\"2018-01-11T07:00:00\","
						+ "\"dispatchNum\":\"B09480\",\"srFlag\":\"1\"}]}");
	}
	
	@Test
	public void testNoTripFound() throws Exception {
		List<Integer> zones = new ArrayList<>();
		zones.add(99);
		zones.add(255);
		List<Trip> noTrip = new ArrayList<>();
		when(zoneSvc.getLocationId(any(String.class))).thenReturn(zones);
		when(tripSvc.getTripData(any(String.class), any(List.class), any(List.class), 
				any(Date.class), any(Date.class))).thenReturn(noTrip);
		
		MvcResult resp = mockMvc.perform(get("/trips")
				.params(paraMap)
				.accept(MediaType.ALL))
        		.andExpect(status().isOk())
        		.andReturn();
		String contentAsString = resp.getResponse().getContentAsString();
		assertEquals(contentAsString, "{\"total\":0,\"tripResults\":[]}");
	}

}
