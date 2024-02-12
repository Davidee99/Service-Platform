package com.model.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TicketInfoDTO {
	private Long count;
	private String ticketStatus;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketInfoDTO other = (TicketInfoDTO) obj;
		return Objects.equals(count, other.count) && Objects.equals(ticketStatus, other.ticketStatus);
	}
	@Override
	public int hashCode() {
		return Objects.hash(count, ticketStatus);
	}
}
