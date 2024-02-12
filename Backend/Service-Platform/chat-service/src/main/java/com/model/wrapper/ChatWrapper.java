package com.model.wrapper;

import com.model.entity.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatWrapper {

	private Chat chat;

	private String exceptionError;

}
