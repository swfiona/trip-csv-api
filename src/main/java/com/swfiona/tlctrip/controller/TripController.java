package com.swfiona.tlctrip.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.swfiona.tlctrip.exception.BoroughNotFoundException;
import com.swfiona.tlctrip.exception.VehicleNotFoundException;
import com.swfiona.tlctrip.model.Result;
import com.swfiona.tlctrip.model.Trip;
import com.swfiona.tlctrip.model.Vehicle;
import com.swfiona.tlctrip.service.TripService;
import com.swfiona.tlctrip.service.ZoneService;

@RestController
public class TripController {
	
	Logger logger = LoggerFactory.getLogger(TripController.class);	
	
	@Autowired
	private TripService tripSvc;
	
	@Autowired 
	private ZoneService zoneSvc;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final static int MAX_RESULTS_SIZE = 50;
	
	@GetMapping("/trips")
	@ResponseStatus(HttpStatus.OK)
	public Result getTrips(@RequestParam String vehicle, 
			@RequestParam String fromBorough, 
			@RequestParam String toBorough,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date fromTime,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date toTime,
			@RequestParam(defaultValue="0") Integer offset) throws Exception {
		Result results = new Result();
		
		//invalid vehicle type?
		if (!EnumUtils.isValidEnumIgnoreCase(Vehicle.class, vehicle))
			throw new VehicleNotFoundException(vehicle);
		
		//location id lookup from given borough
		List<Integer> startLoc = zoneSvc.getLocationId(fromBorough);
		if (CollectionUtils.isEmpty(startLoc)) 
			throw new BoroughNotFoundException(fromBorough);
		
		List<Integer> endLoc = zoneSvc.getLocationId(toBorough);
		if (CollectionUtils.isEmpty(endLoc)) 
			throw new BoroughNotFoundException(toBorough);
		
		List<Trip> trips = new ArrayList<>();
		//query vehicle trip data from file
		trips = tripSvc.getTripData(vehicle, startLoc, endLoc, fromTime, toTime);
		logger.info("Total trips found: {}", trips.size());
		
    	results.setTotal(trips.size());
	    //return 50 results at a time
	    if (trips.size()>MAX_RESULTS_SIZE) {
	    	logger.info("Only returning " + MAX_RESULTS_SIZE);
	    	results.setTripResults(trips.subList(offset, offset+MAX_RESULTS_SIZE));
	    } else results.setTripResults(trips);
		
	    return results;
	}
}
