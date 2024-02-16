package com.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeTicketStatusDTO implements Serializable{

	private static final long serialVersionUID = -8681961070272841324L;
	
	private Long ticketId;
	
	private Long operatorId;

}
