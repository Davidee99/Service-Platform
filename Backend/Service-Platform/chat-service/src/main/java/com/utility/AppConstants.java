package com.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

public class AppConstants {
	private static final List<String> TICKET_STATUS_VALUES_LIST = Arrays.asList("NON_WIP", "WIP", "CLOSED", "CANCELLED");

	public static List<String> getTicketStatusValuesList() {
		return TICKET_STATUS_VALUES_LIST;
	}
	
}
