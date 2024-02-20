package com.utils;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class AppConstants {

	public static final String TICKET_STATUS_CLOSED = "CLOSED";
	public static final String TICKET_STATUS_NON_WIP = "NON_WIP";
	public static final String TICKET_STATUS_WIP = "WIP";
	public static final String TICKET_STATUS_CANCELLED = "CANCELLED";

	public static final List<String> ticketStatuses = Arrays.asList(TICKET_STATUS_NON_WIP, TICKET_STATUS_WIP,
			TICKET_STATUS_CLOSED, TICKET_STATUS_CANCELLED);

	public static final String TICKET_TYPE_PRODUCT = "PRODUCT";
	public static final String TICKET_TYPE_REFUND = "REFUND";
	public static final List<String> TICKET_TYPE_LIST = Arrays.asList(TICKET_TYPE_PRODUCT, TICKET_TYPE_REFUND);
}
