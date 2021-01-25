package com.rate.management.service;

import com.rate.management.dto.RateDto;
import com.rate.management.dto.RateSearchResultDto;
import com.rate.management.exception.RateManagementException;

public interface RateManagementService {

	public RateSearchResultDto searchRate(Long id) throws RateManagementException;
	public void addRate(RateDto rate) throws RateManagementException;
	public void updateRate(RateDto rate,Long id) throws RateManagementException;
	public void deleteRate(Long id) throws RateManagementException;
}
