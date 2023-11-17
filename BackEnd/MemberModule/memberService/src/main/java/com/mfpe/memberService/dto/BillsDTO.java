package com.mfpe.memberService.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class BillsDTO {
	

	
	private int memberId;
	private int policyId;
	private Date  lastPaidDate;
	private int dueAmount;
	private Date  lateCharge;
	private Date  dueDate;
	

}
