package com.mfpe.memberService.model;



import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ClaimDetails {
	
	/*private String memberId;
	private String policyId;
	private String hospitalId;
	private String remarks;
	private String benefitId;
	private int totalAmount;
	private int claimedAmount;*/
	

	private String claimId;
	
	private String status;

	private String description;
    

	private String remarks;
	
	private double claimAmount;


    private String hospitalId;
    

    private String benefitId;
    

    private String policyId;
    

    private String memberId;
	
}
