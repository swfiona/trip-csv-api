package com.swfiona.tlctrip.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.MappingIterator;
import com.swfiona.tlctrip.csv.TripCsvReader;
import com.swfiona.tlctrip.model.FHVTrip;
import com.swfiona.tlctrip.model.Trip;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

	@InjectMocks
	private TripService tripSvc;
	
	@Mock
	private TripCsvReader csvReader;
	
	FHVTrip trip = new FHVTrip();
	List<Integer> locs = new ArrayList<>();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Before
	public void init() throws ParseException {		
		trip.setDispatchNum("B09480");
		trip.setDropOffLoc(99);
		trip.setPickUpLoc(255);
		trip.setPickUpTime("2018-01-09 07:10:00");
		trip.setDropOffTime("2018-01-11 07:00:00");
		trip.setSrFlag("1");
		locs.add(98);
		locs.add(99);
	}
	
	@Test
	public void getFHVTripData() throws Exception {
		
		MappingIterator<FHVTrip> mockIterator = mock(MappingIterator.class);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(trip);
		when(csvReader.readFHVCsv()).thenReturn(mockIterator);
		locs.add(255);
		List<Trip> trips = tripSvc.getTripData("fhv", locs, locs, 
				format.parse("2018-01-09 07:00:00"), format.parse("2018-01-11 07:00:00"));
		assertNotNull(trips);
		assertEquals(1, trips.size());
	}
	
	@Test
	public void tripLocationNotMatching() throws Exception {
		MappingIterator<FHVTrip> mockIterator = mock(MappingIterator.class);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(trip);
		when(csvReader.readFHVCsv()).thenReturn(mockIterator);
		locs.remove(Integer.valueOf(255));
		List<Trip> trips = tripSvc.getTripData("fhv", locs, locs, 
				format.parse("2018-01-09 07:00:00"), format.parse("2018-01-10 07:00:00"));
		assertNotNull(trips);
		assertEquals(0, trips.size());
	}
	
	@Test
	public void tripDateTimeNotMatching() throws Exception {
		MappingIterator<FHVTrip> mockIterator = mock(MappingIterator.class);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(trip);
		when(csvReader.readFHVCsv()).thenReturn(mockIterator);
		locs.add(255);
		List<Trip> trips = tripSvc.getTripData("fhv", locs, locs, 
				format.parse("2018-01-12 07:00:00"), format.parse("2018-01-14 07:00:00"));
		assertNotNull(trips);
		assertEquals(0, trips.size());
	}
	
	@Test(expected=Exception.class)
	public void throwParseException() throws Exception {
		FHVTrip badTrip = new FHVTrip();
		badTrip.setDispatchNum("B09480");
		badTrip.setDropOffLoc(99);
		badTrip.setPickUpLoc(255);
		badTrip.setPickUpTime("2018");
		badTrip.setDropOffTime("2018-01-11 07");
		badTrip.setSrFlag("1");
		MappingIterator<FHVTrip> mockIterator = mock(MappingIterator.class);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(badTrip);
		when(csvReader.readFHVCsv()).thenReturn(mockIterator);
		List<Trip> trips = tripSvc.getTripData("fhv", locs, locs, 
				format.parse("2018-01-09 07:00:00"), format.parse("2018-01-10 07:00:00"));
		assertEquals(0, trips.size());
	}

}
