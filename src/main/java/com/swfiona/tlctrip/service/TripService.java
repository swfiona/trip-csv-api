package com.swfiona.tlctrip.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.swfiona.tlctrip.csv.TripCsvReader;
import com.swfiona.tlctrip.model.FHVTrip;
import com.swfiona.tlctrip.model.GreenCabTrip;
import com.swfiona.tlctrip.model.TaxiTrip;
import com.swfiona.tlctrip.model.Trip;
import com.swfiona.tlctrip.model.Vehicle;

@Service
public class TripService {
	
	Logger logger = LoggerFactory.getLogger(TripService.class);	
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	TripCsvReader csvReader;
	
	public List<Trip> getTripData(String vehicleType, List<Integer> startLoc, List<Integer> endLoc, Date fromTime,
			Date toTime) throws Exception {
		List<Trip> results = new ArrayList<>();
		  
		MappingIterator<?> mi = null;
		try {
			switch (EnumUtils.getEnum(Vehicle.class, vehicleType.toUpperCase())) {
				case FHV:
					mi = csvReader.readFHVCsv();
					while (mi!=null && mi.hasNext()) { 
						FHVTrip data = (FHVTrip) mi.next();
						if (match(data, startLoc, endLoc, fromTime, toTime))
							results.add(data);
					}
					break;
				case GREEN:
					mi = csvReader.readGreenCabCsv();
					while (mi!=null && mi.hasNext()) { 
						GreenCabTrip data = (GreenCabTrip) mi.next();
						if (match(data, startLoc, endLoc, fromTime, toTime))
							results.add(data);
					}
					break;
				case YELLOW:
					mi = csvReader.readYellowTaxiCsv();
					while (mi!=null && mi.hasNext()) { 
						TaxiTrip data = (TaxiTrip) mi.next();
						if (match(data, startLoc, endLoc, fromTime, toTime))
							results.add(data);
					}
					break;
			}
		} catch (IOException ioe) {
			throw new Exception("Failed to read csv file", ioe);
		} catch (ParseException pe) {
			throw new Exception("Failed to read/parse pick up or drop off time", pe);
		}
		
		return results;
	}
	
	private boolean match(Trip data, List<Integer> startLoc, List<Integer> endLoc, Date fromTime,
			Date toTime) throws ParseException {
		Integer doLoc = data.getDropOffLoc();
		Integer puLoc = data.getPickUpLoc();
		Date doTime = format.parse(data.getDropOffTime());
		Date puTime = format.parse(data.getPickUpTime());
		if (matchLocation(startLoc, puLoc, endLoc, doLoc)
				&& matchDateTime(fromTime, puTime, toTime, doTime))
			return true;
		else return false;
	}
	
	/*
	 * Compare pick up location and drop off location return true
	 * if match
	 * 
	 * @param startLoc
	 * @param puLoc
	 * @param endLoc
	 * @param doLoc
	 * 
	 */
	private boolean matchLocation(List<Integer> startLoc, Integer puLoc, List<Integer> endLoc, Integer doLoc) {
		if (puLoc==null || doLoc==null)
			return false;
		else if (startLoc.contains(puLoc) && endLoc.contains(doLoc))
			return true;
		else return false;
	}
	
	/*
	 * Compare pick up time and drop off time return true if
	 * within range
	 * 
	 * @param fromTime start pick up time
	 * @param puTime actual pick up date and time
	 * @param toTime
	 * @param doTime actual drop off date and time
	 * 
	 */
	private boolean matchDateTime(Date fromTime, Date puTime, Date toTime, Date doTime) {
		if (fromTime==null && toTime==null)
			return true;
		else if (puTime.compareTo(fromTime)>=0 && doTime.compareTo(toTime)<=0)
			return true;
		else return false; 
	}

}
