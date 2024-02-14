package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendAttachmentDTO {

	private Long chatId;
	private Long senderId;
	private String link;
	private String role;

}
