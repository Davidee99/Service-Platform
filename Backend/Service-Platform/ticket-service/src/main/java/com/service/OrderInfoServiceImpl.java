package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.repository.OrderInfoRepository;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired
	private OrderInfoRepository orderInfoRepository;

	@Override
	public Boolean isOrderPresent(Long orderId) {
		// TODO Auto-generated method stub
		return orderInfoRepository.findById(orderId).orElse(null) != null;
	}

}
