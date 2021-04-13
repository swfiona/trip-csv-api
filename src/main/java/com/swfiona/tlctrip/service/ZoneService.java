package com.swfiona.tlctrip.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.CaseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swfiona.tlctrip.model.Zone;
import com.swfiona.tlctrip.repository.ZoneRepository;

@Service
public class ZoneService {
	
	Logger logger = LoggerFactory.getLogger(ZoneService.class);
	
	@Autowired
	private ZoneRepository zoneRepo;
	
	/*
	 * Retrieve the unique location id for this borough
	 * 
	 * @param borough NYC borough name
	 * @return locId LocationID found for this borough
	 */
	public List<Integer> getLocationId(String borough) {
		List<Integer> locList =  new ArrayList<>();
		
		List<Zone> zoneList = zoneRepo.findByBorough(CaseUtils.toCamelCase(borough, true));
		for (Zone zone : zoneList) {
			locList.add(zone.getLocId());
		}	
		
		return locList;
	}

}
