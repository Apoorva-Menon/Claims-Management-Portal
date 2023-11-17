package com.mfpe.claimService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfpe.claimService.client.AuthClient;
import com.mfpe.claimService.dto.ClaimStatusDTO;
import com.mfpe.claimService.exceptions.InvalidTokenException;
import com.mfpe.claimService.model.Claim;
import com.mfpe.claimService.service.ClaimStatusService;
import com.mfpe.claimService.service.SubmitClaimService;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/claimModule")
public class ClaimsController {

	@Autowired
	private ClaimStatusService claimStatusService;
	@Autowired
	private SubmitClaimService submitClaimService;
	
	@Autowired
	private AuthClient authClient;
	
	@GetMapping(path="/getClaimStatus/{id}")
	public ResponseEntity<ClaimStatusDTO> getClaimStatus(@PathVariable("id") String id,@RequestHeader(name = "Authorization", required = true) String token)throws InvalidTokenException {
		log.info(token);
		try {
		if (!authClient.getsValidity(token).isValidStatus()) {
			
			throw new InvalidTokenException("Token is either expired or invalid...");
		}
		
		}
		catch(FeignException e) {
			throw new InvalidTokenException("Token is either expired or invalid...");
			
		}
		
		
		log.info("inside the get Claim Status : BEGIN");
		return claimStatusService.getClaimStatus(id); 
	}
	
	/*
	@GetMapping(path="/getClaimStatus/{id}")
	public ResponseEntity<ClaimStatusDTO> getClaimStatus(@PathVariable("id") String id)throws InvalidTokenException {
	
		
		
		log.info("inside the get Claim Status : BEGIN");
		return claimStatusService.getClaimStatus(id); 
	}
	*/
	
	@PostMapping(path ="/submitClaim")
	public ResponseEntity<ClaimStatusDTO> submitClaim(@RequestBody Claim claim,@RequestHeader(name = "Authorization", required = true) String token)throws InvalidTokenException {
		log.info(token);
		try {
		if (!authClient.getsValidity(token).isValidStatus()) {
			
			throw new InvalidTokenException("Token is either expired or invalid...");
		}
		
		}
		catch(FeignException e) {
			throw new InvalidTokenException("Token is either expired or invalid...");
			
		}
		
		log.info("inside the submit Claim : BEGIN");
		return submitClaimService.submitClaim(claim,token);
	}
	
}
