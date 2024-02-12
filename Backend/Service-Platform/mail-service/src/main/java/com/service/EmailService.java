package com.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	public String sendDefaultMessage(String to, String accessCode, String link);
	public String sendCustomMessage(String to, String subj, String text);
}
