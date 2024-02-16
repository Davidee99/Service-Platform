package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

}
