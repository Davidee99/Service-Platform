package com.utility;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

	public static final String TICKET_STATUS_NON_WIP = "NON_WIP";
	public static final String TICKET_STATUS_WIP = "WIP";
	public static final String TICKET_STATUS_CLOSED = "CLOSED";
	public static final String TICKET_STATUS_CANCELLED = "CANCELLED";

	public static final List<String> TICKET_STATUS_VALUES_LIST = Arrays.asList(TICKET_STATUS_NON_WIP, TICKET_STATUS_WIP,
			TICKET_STATUS_CLOSED, TICKET_STATUS_CANCELLED);

	public static final String ROLE_USER = "USER";
	public static final String ROLE_OPERATOR = "OPERATOR";
	public static final String ROLE_ADMIN = "ADMINISTRATOR";

	public static final List<String> ROLES_LIST = Arrays.asList(ROLE_USER, ROLE_OPERATOR, ROLE_ADMIN);

}
