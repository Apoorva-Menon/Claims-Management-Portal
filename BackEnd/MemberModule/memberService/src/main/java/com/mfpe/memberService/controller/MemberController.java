package com.mfpe.memberService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mfpe.memberService.dto.ClaimStatusDTO;
import com.mfpe.memberService.model.Bills;
import com.mfpe.memberService.model.ClaimDetails;
import com.mfpe.memberService.service.ClaimStatusAndDetails;
import com.mfpe.memberService.exceptions.InvalidTokenException;

import feign.FeignException;

import com.mfpe.memberService.client.AuthClient;

import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/memberModule")
public class MemberController {

	private ClaimStatusDTO claimStatusDTO = new ClaimStatusDTO();
	
	@Autowired
	private ClaimStatusAndDetails ClaimStatusAndDetails;
	
	 @Autowired
     private AuthClient authClient;
	
	@GetMapping(value="/viewBills/{memberId}/{policyId}")
	public ResponseEntity<Bills> getBills(@PathVariable("policyId") String policyId, @PathVariable("memberId") String memberId,
			@RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException
	{
		log.info("Inside the getBills EndPoint : Begin");
		
		// Checking the validity of user
		try {
			if (!authClient.getsValidity(token).isValidStatus())  {
				
				throw new InvalidTokenException("Token is either expired or invalid...");
			}
			
			}
			catch(FeignException e) {
				throw new InvalidTokenException("Token is either expired or invalid...");
				
			}
		
		try {
		return new ResponseEntity<>( ClaimStatusAndDetails.fetchBills(memberId, policyId), HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>( ClaimStatusAndDetails.fetchBills(memberId, policyId), HttpStatus.FORBIDDEN);
		}
	}
	
	
	@GetMapping(value="/getClaimStatus/{claimId}/{policyId}/{memberId}")
	public ResponseEntity<ClaimStatusDTO> returnClaimStatus(@PathVariable("claimId") String claimId, @PathVariable("policyId") String policyId,
			@PathVariable("memberId") String memberId, @RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException
	{
		
		log.info("Inside the getClaimStatus EndPoint : Begin");
		
		// Checking the validity of user
		try {
			if (!authClient.getsValidity(token).isValidStatus())  {
				
				throw new InvalidTokenException("Token is either expired or invalid...");
			}
			
			}
			catch(FeignException e) {
				throw new InvalidTokenException("Token is either expired or invalid...");
				
			}
		
		try {
		return new ResponseEntity<>( ClaimStatusAndDetails.fetchClaimStatus(claimId, policyId, memberId,token), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>( ClaimStatusAndDetails.fetchClaimStatus(claimId, policyId, memberId,token), HttpStatus.FORBIDDEN);			
		}
	}
	
	@PostMapping(value="/submitClaim")
	public ResponseEntity<ClaimStatusDTO> returnClaimStatusOnUpdate(@RequestBody ClaimDetails claimDetails,  @RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException
	{
		log.info("Inside the submitClaim EndPoint : Begin");
		
		// Checking the validity of user
		try {
			if (!authClient.getsValidity(token).isValidStatus())  {
				
				throw new InvalidTokenException("Token is either expired or invalid...");
			}
			
			}
			catch(FeignException e) {
				throw new InvalidTokenException("Token is either expired or invalid...");
				
			}
		
		try {
			return new ResponseEntity<>( ClaimStatusAndDetails.fetchClaimDetails(claimDetails,token), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>( ClaimStatusAndDetails.fetchClaimDetails(claimDetails,token), HttpStatus.FORBIDDEN);
		}
	}
	
	 
}
