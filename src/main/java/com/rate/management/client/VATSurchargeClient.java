package com.rate.management.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rate.management.common.CommonProperties;
import com.rate.management.dto.SurchargeDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class VATSurchargeClient {
	private final RestTemplate restTemplate;
	private final CommonProperties commonProperties;
	
	private final ObjectMapper mapper = new ObjectMapper();
	@HystrixCommand(fallbackMethod = "defaultSurcharge")
	public SurchargeDto getSurcharge() throws JsonMappingException, JsonProcessingException {
		log.info("fetching surcharge details");
		ResponseEntity<String> responseString = restTemplate.getForEntity(commonProperties.getVatUrl(), String.class);
		SurchargeDto surcharge = mapper.readValue(responseString.getBody(), SurchargeDto.class);
		log.info("fetching of surcharge details successful {}",surcharge);
		return surcharge;
		
	};
	public SurchargeDto defaultSurcharge() {
		log.info("fetching of surcharge details unsuccessful");
		return SurchargeDto.builder().surchargeDescription("Default surcharge").surchargeRate(0.0f).build();
	}
}
