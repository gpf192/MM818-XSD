package com.xsdzq.mm.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.UserTicketTotalViewEntity;

public interface UserTicketTotalViewRepository extends JpaRepository<UserTicketTotalViewEntity, Long> {

	Page<UserTicketTotalViewEntity> findBy(Pageable pageable);

}
