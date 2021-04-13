package com.swfiona.tlctrip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Zone {
	
	@Id
	@Column(name = "locationid")
	private Integer locId;
	
	private String borough;
	
	private String zone;
	
	@Column(name = "service_zone")
	private String svcZone;

	public Integer getLocId() {
		return locId;
	}

	public void setLocId(Integer locId) {
		this.locId = locId;
	}

	public String getBorough() {
		return borough;
	}

	public void setBorough(String borough) {
		this.borough = borough;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSvcZone() {
		return svcZone;
	}

	public void setSvcZone(String svcZone) {
		this.svcZone = svcZone;
	}

}
