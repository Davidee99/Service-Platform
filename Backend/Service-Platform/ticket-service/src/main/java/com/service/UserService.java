package com.service;

import com.model.User;
import com.model.dto.TicketDTO;

public interface UserService {

	User getUserFromTicketDTO(TicketDTO ticketDTO);

	Boolean isEmailMatching(User user, String email);

}
