package com.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangeTicketStatusErrorDTO implements Serializable{

	private static final long serialVersionUID = -6857557299324416678L;
	
	private Long ticketId;
	private String status;
}
