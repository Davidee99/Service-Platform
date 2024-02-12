package com.model.entity;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ticket_id")
	private Long ticketId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "operator_id")
	private Long operatorId;

	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Message> messages;

	@OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Attachment> attachments;

	private Timestamp timestamp;

}
