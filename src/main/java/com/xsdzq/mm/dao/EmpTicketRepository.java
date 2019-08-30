package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.EmpEntity;
import com.xsdzq.mm.entity.EmpTicketEntity;

public interface EmpTicketRepository extends JpaRepository<EmpTicketEntity, Long>, EmpTicketWrapper {

	long countByEmpEntityDivisionAndEmpEntityEnable(String division, int enable);

	EmpTicketEntity findByEmpEntity(EmpEntity empEntity);

	Page<EmpTicketEntity> findByEmpEntityEnableOrderByNumberDesc(int enable, Pageable pageable);

	Page<EmpTicketEntity> findByEmpEntityDivisionAndEmpEntityEnableOrderByNumberDesc(String division, int enable,
			Pageable pageable);

	List<EmpTicketEntity> findByEmpEntityEmpNameLikeAndEmpEntityEnable(String name, int enable);

}
