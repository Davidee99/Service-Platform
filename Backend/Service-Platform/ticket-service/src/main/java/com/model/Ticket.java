package com.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TICKET")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MESSAGE", columnDefinition = "TEXT", nullable = false)
	private String message;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "OPERATOR_ID")
	private Long operatorId;

	@Column(name = "ACCESS_CODE", length = 8, nullable = false)
	private String accessCode;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "STATUS_ERROR")
	private String statusError;

	@Column(name = "PRIORITY")
	private Integer priority;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Column(name = "CLOSING_DATE")
	private Timestamp closingDate;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId;
}
