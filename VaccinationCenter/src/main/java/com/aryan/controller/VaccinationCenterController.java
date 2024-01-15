
package com.aryan.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aryan.entity.VaccinationCenter;
import com.aryan.model.Citizen;
import com.aryan.model.RequiredResponse;
import com.aryan.repo.VaccinationCenterRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/vaccinationCenter")
public class VaccinationCenterController {

	@Autowired
	private VaccinationCenterRepo centerRepo;

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/add")
	public ResponseEntity<VaccinationCenter> addCenter(@RequestBody VaccinationCenter newCenter) {
		VaccinationCenter center = centerRepo.save(newCenter);
		return new ResponseEntity<>(center, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	@HystrixCommand(fallbackMethod = "handleCitizenDownTime")
	public ResponseEntity<RequiredResponse> getdata(@PathVariable int id) {
		RequiredResponse requiredResponse = new RequiredResponse();

		// This is for Vaccination Center Details
		VaccinationCenter center = centerRepo.findById(id).get();
		requiredResponse.setCenter(center);

		// Then Get citizen registered to Vaccination Details
		@SuppressWarnings("unchecked")
		List<Citizen> listOfCitizens = restTemplate.getForObject("http://CITIZEN-SERVICE-MICROSERVICE/citizen/id/" + id,
				List.class);
		requiredResponse.setCitizens(listOfCitizens);
		return new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);

	}

	public ResponseEntity<RequiredResponse> handleCitizenDownTime(@PathVariable int id) {
		RequiredResponse requiredResponse = new RequiredResponse();
		// 1st Get Vaccination Center Details
		VaccinationCenter center = centerRepo.findById(id).get();
		requiredResponse.setCenter(center);
		return new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);
	}

	@GetMapping("/getVacc/{id}")
	public ResponseEntity<VaccinationCenter> getOne(@PathVariable int id) {
		RequiredResponse requiredResponse = new RequiredResponse();

		// This is for Vaccination Center Details
		VaccinationCenter center = centerRepo.findById(id).get();
		return new ResponseEntity<VaccinationCenter>(center, HttpStatus.OK);
	}
}
