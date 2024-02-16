package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.LoginInfo;

public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {

}
