package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.EmpEntity;
import com.xsdzq.mm.entity.EmpTicketEntity;

public interface EmpTicketRepository extends JpaRepository<EmpTicketEntity, Long>, EmpTicketWrapper {

	EmpTicketEntity findByEmpEntity(EmpEntity empEntity);

	Page<EmpTicketEntity> findByOrderByNumberDesc(Pageable pageable);

	Page<EmpTicketEntity> findByEmpEntityDivisionOrderByNumberDesc(String division, Pageable pageable);

	List<EmpTicketEntity> findByEmpEntityEmpNameLike(String name);
	EmpTicketEntity findByEmpEntity_empId(String empId);


}
