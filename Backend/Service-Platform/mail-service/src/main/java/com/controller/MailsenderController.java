package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.EmailService;
import com.model.CustomRequestObject;
import com.model.RequestObject;

@RestController
@RequestMapping("/mailsender")
public class MailsenderController {
	@Autowired
	EmailService svc;
	
	@PostMapping("/sendDefault")
	ResponseEntity<String> sendDefault(@RequestBody RequestObject reqObj)
	{
		String success = svc.sendDefaultMessage(
				reqObj.getTo(), 
				reqObj.getAccessCode(),
				reqObj.getLink()
				);
		return ResponseEntity.status(HttpStatus.OK).body(success);
		
	}
	@PostMapping("/sendCustom")
	ResponseEntity<String> sendCustom(@RequestBody CustomRequestObject reqObj)
	{
		String success = svc.sendCustomMessage(
				reqObj.getTo(), 
				reqObj.getSubj(),
				reqObj.getText()
				);
		return ResponseEntity.status(HttpStatus.OK).body(success);
	}
}
