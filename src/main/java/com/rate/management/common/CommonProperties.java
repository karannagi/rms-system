package com.rate.management.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class CommonProperties {

	@Value("${app.surcharge.url}")
	private String vatUrl;
}
