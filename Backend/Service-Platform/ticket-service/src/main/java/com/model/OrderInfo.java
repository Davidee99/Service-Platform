package com.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "ORDER_INFO")
public class OrderInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CODE", length = 255, unique = true)
	private String code;

	@Column(name = "CREATE_DATE")
	private Timestamp createDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_METHOD")
	private PaymentMethod paymentMethod;

	@Column(name = "CC_NUMBER", length = 16)
	private String ccNumber;

}