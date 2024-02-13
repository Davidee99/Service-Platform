package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.entity.LoginInfo;

public interface LoginInfoRepository extends JpaRepository<LoginInfo, Integer> {

	LoginInfo findByEmail(String email);

}
