package com.mfpe.claimService.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "claims")

@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Claim {

	@Id
    @Column(name="CID")
	private String claimId;
	
    @Column(name="Status")	
	private String status;
    
    @Column(name="Description")	
	private String description;
    
    @Column(name="Remarks")	
	private String remarks;
    
    @Column(name="Claim_Amount")	
	private double claimAmount;

    //@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    //@JoinColumn(name="hospitalId")
   // private  HospitalDetails hospitalDetails;
    @Column(name="hospitalId")
    private String hospitalId;
    
    //@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    //@JoinColumn(name="benefitId")
   // private Benefits benefits;
    @Column(name="benefitId")
    private String benefitId;
    
    @Column(name="PolicyId")
    private String policyId;
    
    @Column(name="MemberId")
    private String memberId;
    
    
}
