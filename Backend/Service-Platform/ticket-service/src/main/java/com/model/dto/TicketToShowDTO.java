package com.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketToShowDTO implements Serializable {

	private static final long serialVersionUID = 2826514292546168291L;

	private String type;
	private String message;
	private String status;
	private String status_error;
	private Integer priority;

}
