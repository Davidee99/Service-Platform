package com.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTicketRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993329168952775517L;
	Long ticketId;
	String type;
}
