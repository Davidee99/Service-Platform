package com.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoDTO {

	private String firstName;
	private String lastName;
	private String role;
	private int userId;
	private String tokenValue;

}
