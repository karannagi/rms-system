package com.rate.management.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rate.management.client.VATSurchargeClient;
import com.rate.management.constants.ErrorCodes;
import com.rate.management.dto.RateDto;
import com.rate.management.dto.RateSearchResultDto;
import com.rate.management.dto.SurchargeDto;
import com.rate.management.entity.Rate;
import com.rate.management.exception.RateManagementException;
import com.rate.management.repo.RateRepo;
import com.rate.management.service.impl.RateManagementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RateManagementServiceTest {
	@Mock
	private RateRepo rateRepo;

	@Mock
	private VATSurchargeClient vatSurchargeClient;

	@InjectMocks
	private RateManagementServiceImpl rateManagementService;

	private Rate rate = new Rate();

	@BeforeEach
	public void init() {
		rate.setAmount(12);
		rate.setId(1L);
		rate.setRateDescription("test");
		rate.setRateEffectiveDate(new Date());
		rate.setRateExpirationDate(new Date());
	}

	@Test
	public void searchRateTest() throws JsonMappingException, JsonProcessingException {
		Mockito.when(vatSurchargeClient.getSurcharge()).thenReturn(new SurchargeDto());
		Mockito.when(rateRepo.findByDateFilterandId(Mockito.any(), Mockito.any())).thenReturn(Optional.of(rate));
		RateSearchResultDto result = rateManagementService.searchRate(1L);
		assertEquals(result.getAmount(),rate.getAmount());
		assertEquals(result.getRateDescription(),rate.getRateDescription());
	}

	@Test
	public void addRateTest() {
		Mockito.when(rateRepo.save(Mockito.any())).thenReturn(new Rate());
		rateManagementService.addRate(new RateDto());

	}

	@Test
	public void updateRateTest() {
		Mockito.when(rateRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(rate));
		Mockito.when(rateRepo.save(Mockito.any())).thenReturn(rate);
		rateManagementService.updateRate(new RateDto(), 1L);
	}

	@Test
	public void updateRateTestFail() {
		Mockito.when(rateRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			rateManagementService.updateRate(new RateDto(), 1L);
		}
		catch(RateManagementException e) {
			assertEquals(e.getErrorCode(),ErrorCodes.RATE_ID_NOT_FOUND.getErrorCode());
		}
	}
	
	@Test
	public void updateRateTestFailedInternal() {
		Mockito.when(rateRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(rate));
		Mockito.when(rateRepo.save(Mockito.any())).thenThrow(new RateManagementException(ErrorCodes.INTERNAL_SERVER_ERROR));
		try {
			rateManagementService.updateRate(new RateDto(), 1L);
		}
		catch(RateManagementException e) {
			assertEquals(e.getErrorCode(),ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode());
		}
	}

	@Test
	public void deleteRateTest() {
		Mockito.when(rateRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(rate));
		rateManagementService.deleteRate(1L);
	}

	@Test
	public void deleteRateTestFailed() {
		Mockito.when(rateRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			rateManagementService.deleteRate(1L);
		}
		catch(RateManagementException e) {
			assertEquals(e.getErrorCode(),ErrorCodes.RATE_ID_NOT_FOUND.getErrorCode());
		}
	}
	
	@Test
	public void deleteRateTestFailedInternal() {
		Mockito.when(rateRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(rate));
		Mockito.doThrow(new RateManagementException(ErrorCodes.INTERNAL_SERVER_ERROR)).when(rateRepo).deleteById(Mockito.anyLong());
		try {
			rateManagementService.deleteRate(1L);
		}
		catch(RateManagementException e) {
			assertEquals(e.getErrorCode(),ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode());
		}
	}
}
