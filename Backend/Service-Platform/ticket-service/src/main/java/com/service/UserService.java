package com.service;

import com.model.User;
import com.model.dto.TicketToInsertDTO;

public interface UserService {

	User getUserFromTicketDTO(TicketToInsertDTO ticketDTO);

	Boolean isEmailMatching(User user, String email);

}
