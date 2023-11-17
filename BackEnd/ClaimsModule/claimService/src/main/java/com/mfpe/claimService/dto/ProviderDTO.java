package com.mfpe.claimService.dto;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mfpe.claimService.model.Hospital;
import com.mfpe.claimService.model.HospitalDetails;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProviderDTO {
	
	@JsonProperty
	private List<Hospital> providers;
		@JsonIgnore
		public ProviderDTO(List<Hospital> providers) {
			this.providers=providers;
		}
		@JsonIgnore
		public List<Hospital> getProviders() {
			return providers;
		}
		@JsonIgnore
		public void setProviders(List<Hospital> providers) {
			this.providers = providers;
		}
		
		
		

}