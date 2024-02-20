package com.utils;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class AppConstants {
	public final List<String> ticketStatuses = Arrays.asList("NON_WIP", "WIP", "CLOSED", "CANCELLED");
				
}
