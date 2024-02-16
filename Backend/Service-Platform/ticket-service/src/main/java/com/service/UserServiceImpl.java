package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.LoginInfo;
import com.model.User;
import com.model.dto.TicketDTO;
import com.repository.LoginInfoRepository;
import com.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginInfoRepository loginInfoRepository;

	@Override
	public User getUserFromTicketDTO(TicketDTO ticketDTO) {

		return userRepository.findUserByFirstNameAndLastName(ticketDTO.getFirstName(), ticketDTO.getLastName());
	}

	@Override
	public Boolean isEmailMatching(User user, String email) {

		LoginInfo loginInfo = loginInfoRepository.findById(user.getId()).orElse(null);

		return loginInfo.getEmail().equals(email);
	}

}
