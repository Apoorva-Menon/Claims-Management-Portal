package com.mfpe.claimService.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "benefitsDetails")

@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Benefits {
	@Id
	private String benefitId;
	private String benefitName;
	
	//@OneToOne(fetch = FetchType.LAZY,mappedBy="benefits",cascade = CascadeType.ALL)
	//private Claim claim;
	
	
}
