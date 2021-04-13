package com.swfiona.tlctrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

@Configuration
public class Config {

	@Bean
	public CsvMapper createCsvMapper() {
		CsvMapper csvMapper = new CsvMapper(); 
		csvMapper.configure(Feature.IGNORE_UNKNOWN, true);
		csvMapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
		csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);
		
		return csvMapper;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
}
