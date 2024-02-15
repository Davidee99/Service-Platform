package com.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
public class Employee {
	/*
	 * l'unico modo idoneo affinche' l'id di login info sia la stessa di employee info:
	 * bisogna seguire la scrittura sottostante, ovviamente quando si parla ,in questo caso,
	 * di relazioni one to one
	 * 
	 * utilizzo lombok per non scivere i metodi "classici" di java
	 */
	@Id
	private Long id;
	
	@OneToOne(mappedBy = "employee")
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private LoginInfo loginInfo;

	private String firstname;
	private String lastname;

}