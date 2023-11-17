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
@Table(name = "hospitalDetails")

@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class HospitalDetails {

	@Id
	private String hospitalId;
	private String hospitalName;
	private String address;
	//@OneToOne(fetch = FetchType.LAZY, mappedBy="hospitalDetails",cascade = CascadeType.ALL)
	//private Claim claim;
	
	
}
