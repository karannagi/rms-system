package com.rate.management.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rate.management.dto.RateDto;
import com.rate.management.dto.RateSearchResultDto;
import com.rate.management.service.RateManagementService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rate")
@AllArgsConstructor
@Slf4j
public class RateManagementController {

	private final RateManagementService rateManagementService;
	@GetMapping("/{id}")
	public ResponseEntity<RateSearchResultDto> searchRate(@PathVariable Long id) {
		log.info("Search api called for id {}",id);
		return new ResponseEntity<>(rateManagementService.searchRate(id),HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Void> addRate(@Valid @RequestBody RateDto rateDto){
		log.info("save api called for rate: {}",rateDto);
		rateManagementService.addRate(rateDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateRate(@PathVariable Long id,@Valid @RequestBody RateDto rateDto){
		log.info("update api called for rate: {} and id: {}",rateDto,id);
		rateManagementService.updateRate(rateDto,id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRate(@PathVariable Long id){
		log.info("delete api called for id {}",id);
		rateManagementService.deleteRate(id);;
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
