package com.swfiona.tlctrip.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.swfiona.tlctrip.model.Zone;
import com.swfiona.tlctrip.repository.ZoneRepository;

@RunWith(MockitoJUnitRunner.class)
public class ZoneServiceTest {
	
	@InjectMocks
	private ZoneService zoneSvc;
	
	@Mock
	private ZoneRepository zoneRepo;
	
	private List<Integer> locationList = new ArrayList<>();
	private List<Zone> zones = new ArrayList<>();
	
	@Before
	public void init() {
		locationList.add(265);
		locationList.add(98);
		Zone zone = new Zone();
		zone.setLocId(265);
		zone.setBorough("Queens");
		zone.setSvcZone("Yellow Zone");
		zone.setZone("Bayside");
		zones.add(zone);
		zone = new Zone();
		zone.setLocId(98);
		zone.setBorough("Queens");
		zone.setSvcZone("Boro Zone");
		zone.setZone("Bay Terrace");
		zones.add(zone);
	}
	
	
	@Test
	public void getQueensLocationId() {
		when(zoneRepo.findByBorough(any(String.class))).thenReturn(zones);
		List<Integer> list = zoneSvc.getLocationId("Queens");
		assertEquals(2, list.size());
		assertEquals(265, list.get(0).intValue());
		assertEquals(98, list.get(1).intValue());
	}

	@Test
	public void noLocationAdded() {
		List<Zone> noZones = new ArrayList<>();
		when(zoneRepo.findByBorough(any(String.class))).thenReturn(noZones);
		List<Integer> list = zoneSvc.getLocationId("Boston");
		assertNotNull(list);
		assertEquals(0, list.size());
	}
}
