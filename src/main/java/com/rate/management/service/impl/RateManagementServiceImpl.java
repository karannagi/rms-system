package com.rate.management.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rate.management.client.VATSurchargeClient;
import com.rate.management.constants.ErrorCodes;
import com.rate.management.dto.RateDto;
import com.rate.management.dto.RateSearchResultDto;
import com.rate.management.dto.SurchargeDto;
import com.rate.management.entity.Rate;
import com.rate.management.exception.RateManagementException;
import com.rate.management.repo.RateRepo;
import com.rate.management.service.RateManagementService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RateManagementServiceImpl implements RateManagementService{

	private final RateRepo rateRepo;
	private final VATSurchargeClient vatSurchargeClient;

	@Override
	public RateSearchResultDto searchRate(Long id) throws RateManagementException {
		Rate rate = rateRepo.findByDateFilterandId(new Date(),id).orElseThrow(()->new RateManagementException(ErrorCodes.RATE_ID_NOT_FOUND));
		SurchargeDto surcharge;
		try {
			surcharge = vatSurchargeClient.getSurcharge();
			log.info("surcharge details fetched");
			return getRateResultData(rate,surcharge);
		} catch (Exception e) {
			throw new RateManagementException(ErrorCodes.INTERNAL_SERVER_ERROR);
		}

	}


	@Override
	public void addRate(RateDto rateDto) throws RateManagementException {

		try {
			Rate rate = new Rate();
			BeanUtils.copyProperties(rateDto, rate);
			rateRepo.save(rate);
			log.info("adding of rate successful");
		} catch (Exception e) {
			throw new RateManagementException(ErrorCodes.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void updateRate(RateDto rateDto,Long id) throws RateManagementException {
		Rate rate = rateRepo.findById(id).orElseThrow(()->new RateManagementException(ErrorCodes.RATE_ID_NOT_FOUND));
		try {
			BeanUtils.copyProperties(rateDto, rate);
			rateRepo.save(rate);
			log.info("update of rate successful");
		} catch (Exception e) {
			throw new RateManagementException(ErrorCodes.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void deleteRate(Long id) throws RateManagementException {
		rateRepo.findById(id).orElseThrow(()->new RateManagementException(ErrorCodes.RATE_ID_NOT_FOUND));
		try {
			rateRepo.deleteById(id);
			log.info("deletion of rate successful");
		} catch (Exception e) {
			throw new RateManagementException(ErrorCodes.INTERNAL_SERVER_ERROR);
		}

	}


	private RateSearchResultDto getRateResultData(Rate rate, SurchargeDto surcharge) {
		return RateSearchResultDto.builder()
				.amount(rate.getAmount())
				.rateDescription(rate.getRateDescription())
				.rateEffectiveDate(rate.getRateEffectiveDate())
				.rateExpirationDate(rate.getRateExpirationDate())
				.surchargeDescription(surcharge.getSurchargeDescription())
				.surchargeRate(surcharge.getSurchargeRate())
				.build()
				;
	}

}
