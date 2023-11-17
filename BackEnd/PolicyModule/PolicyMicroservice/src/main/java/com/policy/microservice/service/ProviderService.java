package com.policy.microservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.microservice.dto.ProviderDTO;
import com.policy.microservice.exception.InvalidPolicyId;
import com.policy.microservice.model.Hospital;
import com.policy.microservice.model.Policy;
import com.policy.microservice.repository.HospitalRepo;
import com.policy.microservice.repository.PolicyRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProviderService {
	
	@Autowired
	private PolicyRepo policyRepo;
	
	public ProviderDTO getProviders(String policyId) throws InvalidPolicyId {
		
		log.info("Inside get providers method in provider service...");
					
		Optional<Policy> policies = policyRepo.findById(policyId);
		
		if(!policies.isPresent())
			throw new InvalidPolicyId("Invalid Policy Id");

		log.info("Exiting get providers method in provider service...");
		
		return new ProviderDTO(policies.get().getHospitals());
	}
}
