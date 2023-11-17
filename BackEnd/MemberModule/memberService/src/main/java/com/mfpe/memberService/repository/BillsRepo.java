package com.mfpe.memberService.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mfpe.memberService.model.Bills;



@Repository
@Transactional
public interface BillsRepo extends JpaRepository<Bills, Integer> {
	@Query(value = "select * from Bills b where b.mid=?1", nativeQuery = true)
	Bills getBills(String id);

}