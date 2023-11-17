package com.mfpe.claimService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mfpe.claimService.client.PolicyClient;
import com.mfpe.claimService.dto.BenefitsDTO;
import com.mfpe.claimService.dto.ClaimAmountDTO;
import com.mfpe.claimService.dto.ClaimStatusDTO;
import com.mfpe.claimService.dto.ProviderDTO;
import com.mfpe.claimService.model.Benefits;
import com.mfpe.claimService.model.Claim;
import com.mfpe.claimService.model.Hospital;
import com.mfpe.claimService.model.HospitalDetails;

import com.mfpe.claimService.repository.ClaimRepo;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubmitClaimService {
	@Autowired
	private ClaimRepo claimRepo;
	@Autowired
	private PolicyClient policyClient;
	
	public ResponseEntity<ClaimStatusDTO> submitClaim(Claim claim,String token) {
		try {
			boolean hospitalFlag = false;
			boolean benefitFlag = false;
			boolean amountFlag = false;

			log.info("inside the submit claim service method : BEGIN");
			log.info("checking the provider : BEGIN");
			ProviderDTO providerDTO = policyClient.getChainOfProviders(claim.getPolicyId(),token).getBody();
			log.info("came here");
			for (Hospital hospitalDetails : providerDTO.getProviders()) {
				//System.err.println(hospitalDetails.getName());
				if (claim.getHospitalId().equalsIgnoreCase(hospitalDetails.getHospitalId())) {
					//System.err.println(hospitalDetails.getHospitalId()+" --- "+hospitalDetails.getName());
					hospitalFlag = true;
					break;
				}
			}
			log.info("checking of provider : ENDED");

			log.info("checking the Benefit : BEGIN");
			BenefitsDTO benefitsDTO = policyClient.getEligibleBenefits(claim.getPolicyId(), claim.getMemberId(),token)
					.getBody();
			for (Benefits benefits : benefitsDTO.getBenefits()) {
				if (claim.getBenefitId().equalsIgnoreCase(benefits.getBenefitId())) {
					System.err.println(benefits.getBenefitName());
					benefitFlag = true;
					break;
				}
			}
			log.info("checking of Benefit : ENDED");

			log.info("checking the Amount : BEGIN");
			ClaimAmountDTO claimAmountDTO = policyClient
					.getEligibleAmount(claim.getPolicyId(), claim.getMemberId(),token)
					.getBody();
			if (claim.getClaimAmount() <= claimAmountDTO.getEligibleAmount()) {
				System.err.println(claim.getClaimAmount());
				amountFlag = true;
			}
			log.info("checking of provider : ENDED");

			log.info("setting the status : BEGIN");
			if (hospitalFlag && benefitFlag && amountFlag) {
				claim.setStatus("Pending Action");
				claim.setDescription("All the fields are successfully verified! Please wait for furthur action");
			} else {
				claim.setStatus("Claim Rejected");
				if (!hospitalFlag) {
					claim.setDescription("Providers Details are not valid");
				} else if (!benefitFlag) {
					claim.setDescription("Benefits Details are not valid");
				} else {
					claim.setDescription("Claim amount is not valid");
				}
			}
			log.info("setting the status : ENDED");
			claimRepo.save(claim);
			ClaimStatusDTO claimStatusDTO = new ClaimStatusDTO();
			claimStatusDTO.setClaimStatus(claim.getStatus());
			claimStatusDTO.setClaimDescription(claim.getDescription());
			claimStatusDTO.setClaimId(claim.getClaimId());
			return new ResponseEntity<ClaimStatusDTO>(claimStatusDTO, HttpStatus.OK);
		} catch (Exception e) {
			
			ClaimStatusDTO claimStatusDTO = new ClaimStatusDTO();
			claim.setDescription("you have provided invalid Details");
			claim.setStatus("Invalid Claim Details");
			claimStatusDTO.setClaimStatus(claim.getStatus());
			claimStatusDTO.setClaimDescription(claim.getDescription());
			claimStatusDTO.setClaimId(claim.getClaimId());
//			claimRepo.save(claim);
			return new ResponseEntity<ClaimStatusDTO>(claimStatusDTO, HttpStatus.FORBIDDEN);
		}
	}

}
