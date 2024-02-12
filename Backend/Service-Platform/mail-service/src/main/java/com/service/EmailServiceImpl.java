package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    
    private String getMailContent(SimpleMailMessage msg) 
    {
    	return "Email sent"
    	+ "\n To: "+ msg.getTo() 
    	+ "\n Subject: "+ msg.getSubject() 
    	+ "\n Text: "+ msg.getText();
    }

    public String sendDefaultMessage(String to, String accessCode, String link) {
    	// creo l'oggetto del messaggio, in pratica l'email che verrà mandata
        SimpleMailMessage message = new SimpleMailMessage(); 
        
        // lo compongo con titolo, oggetto, ecc ecc
        
        message.setTo(to); // destinatario
        
        String subject = "Ticket request"; 
        message.setSubject(subject); // imposta il titolo dell'email
        
        String text = "We received a ticket request from your account. \n"
        		+ "Open the link and use this access code to see your ticket: "+accessCode+" \n"
        		+ link;
        message.setText(text);
        
        // chiamo il mail sender e faccio inviare il messaggio
        try {
        	emailSender.send(message);
        	return getMailContent(message);
		}
		catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			return null;
		}
    }
    
    public String sendCustomMessage(String to, String subj, String text) {
    	// creo l'oggetto del messaggio, in pratica l'email che verrà mandata
        SimpleMailMessage message = new SimpleMailMessage(); 
        
        // lo compongo con titolo, oggetto, ecc ecc
        message.setTo(to);
        message.setSubject(subj);
        message.setText(text);
        
        // chiamo il mail sender e provo a far inviare il messaggio
        try {
        	emailSender.send(message);
        	return getMailContent(message);
		}
		catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			return null;
		}
    }
}