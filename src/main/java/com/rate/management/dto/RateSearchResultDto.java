package com.rate.management.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RateSearchResultDto {
	private String rateDescription;
	
	private Date rateEffectiveDate;
	
	private Date rateExpirationDate;
	
	private Integer amount;
	private Float surchargeRate;
	private String surchargeDescription;
}
