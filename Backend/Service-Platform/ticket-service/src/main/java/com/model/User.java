package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "FIRSTNAME", length = 255)
	private String firstName;

	@Column(name = "LASTNAME", length = 255)
	private String lastName;

}