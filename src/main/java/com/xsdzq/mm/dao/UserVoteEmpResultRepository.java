package com.xsdzq.mm.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xsdzq.mm.entity.UserEntity;
import com.xsdzq.mm.entity.UserVoteEmpResultEntity;

public interface UserVoteEmpResultRepository extends JpaRepository<UserVoteEmpResultEntity, Long> {

	UserVoteEmpResultEntity findByUserEntityAndRecordTime(UserEntity userEntity, Date recordTime);

}
