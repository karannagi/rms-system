package com.rate.management.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RateDto {
	
	@NotNull
	private String rateDescription;
	
	@NotNull
	private Date rateEffectiveDate;
	
	@NotNull
	private Date rateExpirationDate;
	
	@NotNull
	private Integer amount;
}
