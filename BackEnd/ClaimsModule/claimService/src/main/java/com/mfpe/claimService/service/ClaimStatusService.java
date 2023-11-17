package com.mfpe.claimService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mfpe.claimService.dto.ClaimStatusDTO;
import com.mfpe.claimService.repository.ClaimRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClaimStatusService {

	@Autowired
	ClaimRepo claimRepo;
	
	public ResponseEntity<ClaimStatusDTO> getClaimStatus(String id) {
		try {
			log.info("inside getClaimStatus in service ");
			ClaimStatusDTO claimStatusDTO = new ClaimStatusDTO();
			String status = claimRepo.getStatus(id);
			String desc = claimRepo.getDescription(id);
			if (status == null) {
				log.info("inside the get Claim Status : ENDED");
				return new ResponseEntity<ClaimStatusDTO>(claimStatusDTO, HttpStatus.FORBIDDEN);
			}
			claimStatusDTO.setClaimStatus(status);
			claimStatusDTO.setClaimDescription(desc);
			claimStatusDTO.setClaimId(id);
			log.info("inside the get Claim Status : ENDED");
			return new ResponseEntity<ClaimStatusDTO>(claimStatusDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ClaimStatusDTO>(new ClaimStatusDTO(), HttpStatus.FORBIDDEN);
		}
	}

}
