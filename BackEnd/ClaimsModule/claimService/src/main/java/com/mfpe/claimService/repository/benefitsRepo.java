package com.mfpe.claimService.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfpe.claimService.model.Benefits;


@Repository
@Transactional
public interface benefitsRepo extends JpaRepository<Benefits, String> {

}
