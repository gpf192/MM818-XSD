package com.xsdzq.mm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.EmpEntity;

public interface EmpRepository extends JpaRepository<EmpEntity, Long> {

	EmpEntity findByEmpId(Integer empId);

	List<EmpEntity> findByEmpNameLike(String name);

}
