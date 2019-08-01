package com.xsdzq.mm.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	Page<ProductEntity> findBy(Pageable pageable);
	ProductEntity findByCode(String code);
	List<ProductEntity> findByBeginDateLessThanEqualAndEndDateGreaterThanEqual(Date preDay, Date preDay1);
}
