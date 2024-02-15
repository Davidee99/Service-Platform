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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
public class User {
	/*
	 * l'unico modo idoneo affinchÃ© l'id di longin info sia la stessa di user info: bisogna
	 * seguira la scrittura sottostante, ovviamente quando si parla, in questo caso, di
	 * relazioni one to one
	 */
	@Id
	private Long id;

	@OneToOne(mappedBy = "user")
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private LoginInfo loginInfo;

	private String firstname;
	private String lastname;

}