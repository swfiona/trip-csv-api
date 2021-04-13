package com.swfiona.tlctrip.model;

public class TaxiTrip extends Trip {
	
	private String vendorId;
	
	private String storeFwdFlag;
	
	private String rateCode;
	
	private Integer PassengerCnt;
	
	private Double distance;
	
	private Double fareAmt;
	
	private Double extraAmt;
	
	private Double mtaTax;
	
	private Double tip;
	
	private Double toll;
	
	private Double impSurcharge;
	
	private Double totalAmt;
	
	private Integer paymentType;

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getStoreFwdFlag() {
		return storeFwdFlag;
	}

	public void setStoreFwdFlag(String storeFwdFlag) {
		this.storeFwdFlag = storeFwdFlag;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public Integer getPassengerCnt() {
		return PassengerCnt;
	}

	public void setPassengerCnt(Integer passengerCnt) {
		PassengerCnt = passengerCnt;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getFareAmt() {
		return fareAmt;
	}

	public void setFareAmt(Double fareAmt) {
		this.fareAmt = fareAmt;
	}

	public Double getExtraAmt() {
		return extraAmt;
	}

	public void setExtraAmt(Double extraAmt) {
		this.extraAmt = extraAmt;
	}

	public Double getMtaTax() {
		return mtaTax;
	}

	public void setMtaTax(Double mtaTax) {
		this.mtaTax = mtaTax;
	}

	public Double getTip() {
		return tip;
	}

	public void setTip(Double tip) {
		this.tip = tip;
	}

	public Double getToll() {
		return toll;
	}

	public void setToll(Double toll) {
		this.toll = toll;
	}

	public Double getImpSurcharge() {
		return impSurcharge;
	}

	public void setImpSurcharge(Double impSurcharge) {
		this.impSurcharge = impSurcharge;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	
}
