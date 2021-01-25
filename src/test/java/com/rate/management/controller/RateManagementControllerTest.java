package com.rate.management.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rate.management.dto.RateDto;
import com.rate.management.dto.RateSearchResultDto;
import com.rate.management.service.RateManagementService;

@ExtendWith(MockitoExtension.class)
public class RateManagementControllerTest {
	@Mock
	private RateManagementService rateManagementService;
	
	@InjectMocks
	private RateManagementController rateManagementController;

	
	@Test
	public void searchRateTest() {
		Mockito.when(rateManagementService.searchRate(Mockito.any())).thenReturn(new RateSearchResultDto());
		ResponseEntity<RateSearchResultDto> rateResult = rateManagementController.searchRate(1L);
		assertNotNull(rateResult.getBody());
	}
	@Test
	public void addRateTest() {
		Mockito.doNothing().when(rateManagementService).addRate(Mockito.any());;
		ResponseEntity<Void> rateResult = rateManagementController.addRate(new RateDto());
		assertEquals(HttpStatus.OK,rateResult.getStatusCode());
	}
	@Test
	public void updateRate() {
		Mockito.doNothing().when(rateManagementService).updateRate(Mockito.any(),Mockito.any());
		ResponseEntity<Void> rateResult = rateManagementController.updateRate(1L,new RateDto());
		assertEquals(HttpStatus.OK,rateResult.getStatusCode());
	}
	@Test
	public void deleteRate() {
		Mockito.doNothing().when(rateManagementService).deleteRate(Mockito.any());
		ResponseEntity<Void> rateResult = rateManagementController.deleteRate(1L);
		assertEquals(HttpStatus.OK,rateResult.getStatusCode());
	}

}
