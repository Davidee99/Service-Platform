package com.model.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "chat_id")
	@JsonBackReference
	private Chat chat;

	@Column(name = "sender_id")
	private Long senderId;

	@Enumerated(EnumType.STRING)
	private AttachmentType type;

	private String url;

	private Timestamp timestamp;

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", senderId=" + senderId + ", type=" + type + ", url=" + url
				+ ", timestamp=" + timestamp + "]";
	}

}
