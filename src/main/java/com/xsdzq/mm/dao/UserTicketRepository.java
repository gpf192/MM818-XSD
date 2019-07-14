package com.xsdzq.mm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserTicketEntity;

public interface UserTicketRepository extends JpaRepository<UserTicketEntity, Long>, UserTicketWrapper {

	UserTicketEntity findByUserEntity(UserEntity userEntity);

}
