package com.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO implements Serializable {

	private static final long serialVersionUID = 9105374245320365006L;
	/*
	 * Form
	 * 
	 * nome, cognome, email, message, type
	 */
	private String firstName;
	private String lastName;
	private String email;
	private String message;
	private String type;
	private Long orderId;

}
