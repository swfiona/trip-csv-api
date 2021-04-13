package com.swfiona.tlctrip.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.swfiona.tlctrip.model.FHVTrip;
import com.swfiona.tlctrip.model.GreenCabTrip;
import com.swfiona.tlctrip.model.TaxiTrip;

@Service
public class TripCsvReader {
	
	Logger logger = LoggerFactory.getLogger(TripCsvReader.class);
	
	@Autowired
	CsvMapper csvMapper;
	
	@Value("${file.path.green}")
	String greenCsvPath;
	
	@Value("${file.path.yellow}")
	String yellowCsvPath;
	
	@Value("${file.path.fhv}")
	String fhvCsvPath;
	
	private CsvSchema buildYellowTaxSchema() {
		return CsvSchema.builder()
				.addColumn("vendorId", CsvSchema.ColumnType.STRING)
				.addColumn("pickUpTime", CsvSchema.ColumnType.STRING)
				.addColumn("dropOffTime", CsvSchema.ColumnType.STRING)
				.addColumn("passengerCnt", CsvSchema.ColumnType.NUMBER)
				.addColumn("distance", CsvSchema.ColumnType.NUMBER)
				.addColumn("rateCode", CsvSchema.ColumnType.STRING)
				.addColumn("storeFwdFlag", CsvSchema.ColumnType.STRING)
				.addColumn("pickUpLoc", CsvSchema.ColumnType.NUMBER)
				.addColumn("dropOffLoc", CsvSchema.ColumnType.NUMBER)
				.addColumn("paymentType", CsvSchema.ColumnType.NUMBER)
				.addColumn("fareAmt", CsvSchema.ColumnType.NUMBER)
				.addColumn("extraAmt", CsvSchema.ColumnType.NUMBER)
				.addColumn("mtaTax", CsvSchema.ColumnType.NUMBER)
				.addColumn("tip", CsvSchema.ColumnType.NUMBER)
				.addColumn("toll", CsvSchema.ColumnType.NUMBER)
				.addColumn("impSurcharge", CsvSchema.ColumnType.NUMBER)
				.addColumn("totalAmt", CsvSchema.ColumnType.NUMBER)
				.build()
				.withSkipFirstDataRow(true);   
	}
	
	private CsvSchema buildGreenCabSchema() {
		return CsvSchema.builder()
				.addColumn("vendorId", CsvSchema.ColumnType.STRING)
				.addColumn("pickUpTime", CsvSchema.ColumnType.STRING)
				.addColumn("dropOffTime", CsvSchema.ColumnType.STRING)
				.addColumn("storeFwdFlag", CsvSchema.ColumnType.STRING)
				.addColumn("rateCode", CsvSchema.ColumnType.STRING)
				.addColumn("pickUpLoc", CsvSchema.ColumnType.NUMBER)
				.addColumn("dropOffLoc", CsvSchema.ColumnType.NUMBER)
				.addColumn("passengerCnt", CsvSchema.ColumnType.NUMBER)
				.addColumn("distance", CsvSchema.ColumnType.NUMBER)
				.addColumn("fareAmt", CsvSchema.ColumnType.NUMBER)
				.addColumn("extraAmt", CsvSchema.ColumnType.NUMBER)
				.addColumn("mtaTax", CsvSchema.ColumnType.NUMBER)
				.addColumn("tip", CsvSchema.ColumnType.NUMBER)
				.addColumn("toll", CsvSchema.ColumnType.NUMBER)
				.addColumn("ehailFee", CsvSchema.ColumnType.NUMBER)
				.addColumn("impSurcharge", CsvSchema.ColumnType.NUMBER)
				.addColumn("totalAmt", CsvSchema.ColumnType.NUMBER)
				.addColumn("paymentType", CsvSchema.ColumnType.NUMBER)
				.addColumn("tripType", CsvSchema.ColumnType.NUMBER)
				.addColumn("conSurcharge", CsvSchema.ColumnType.NUMBER)
				.build()
				.withSkipFirstDataRow(true);   
	}
	
	private CsvSchema buildFHVSchema() {
		return CsvSchema.builder()
				.addColumn("dispatchNum", CsvSchema.ColumnType.STRING)
				.addColumn("pickUpTime", CsvSchema.ColumnType.STRING)
				.addColumn("dropOffTime", CsvSchema.ColumnType.STRING)
				.addColumn("pickUpLoc", CsvSchema.ColumnType.NUMBER)
				.addColumn("dropOffLoc", CsvSchema.ColumnType.NUMBER)
				.addColumn("srFlag", CsvSchema.ColumnType.STRING)
				.build()
				.withSkipFirstDataRow(true); 
	}
	
	public MappingIterator<TaxiTrip> readYellowTaxiCsv() throws FileNotFoundException, IOException {
		File file = new ClassPathResource(yellowCsvPath).getFile();
		Reader reader = new FileReader(file);
		MappingIterator<TaxiTrip> mi = csvMapper.readerFor(TaxiTrip.class)
				.with(buildYellowTaxSchema())
				.readValues(reader); 
		
		return mi;
	}
	
	public MappingIterator<GreenCabTrip> readGreenCabCsv() throws FileNotFoundException, IOException {
		File file = new ClassPathResource(greenCsvPath).getFile();
		Reader reader = new FileReader(file);
		MappingIterator<GreenCabTrip> mi = csvMapper.readerFor(GreenCabTrip.class)
				.with(buildGreenCabSchema())
				.readValues(reader); 
		
		return mi;
	}
	
	public MappingIterator<FHVTrip> readFHVCsv() throws FileNotFoundException, IOException {
		File file = new ClassPathResource(fhvCsvPath).getFile();
		Reader reader = new FileReader(file);
		MappingIterator<FHVTrip> mi = csvMapper.readerFor(FHVTrip.class)
				.with(buildFHVSchema())
				.readValues(reader); 

		return mi;
	}
	
}
