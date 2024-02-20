
package com.controller;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.dto.ChangeTicketRequest;
import com.service.AdminService;

@RestController
@RequestMapping("/api/admin") // forse da togliere?
public class AdminController {

    @Autowired
    AdminService adminSvc;

    /*
     * Visualizza tutti i ticket aperti dagli utenti e marchiati dagli operatori
     */
    @GetMapping("/getOpenTickets")
    public ResponseEntity<?> getOpenAndApprovedTickets() {
    	System.out.println("entrato nel controller");
    	return adminSvc.getOpenAndApprovedTickets();
    }
    
    @GetMapping("/closeTicket")
    public ResponseEntity<?> closeTicket(@RequestParam(name = "ticketId") Long ticketId)
    {
    	ResponseEntity<?> result;
    	try 
    	{
			result = adminSvc.closeTickets(ticketId);
		} 
    	catch (IllegalArgumentException e) 
    	{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    	return result;
    }
    
    @PostMapping("/changeTicketType")
    public ResponseEntity<?> changeTicketType(@RequestBody ChangeTicketRequest info)
    {
    	ResponseEntity<?> result;
    	try 
    	{
			result = adminSvc.changeTicketType(info);
		} 
    	catch (IllegalArgumentException e) 
    	{
			e.printStackTrace();
			System.err.println("Illegal argument");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    	return result;
    }
    
    
    @GetMapping("/getTicketsExceptNonWip")
    public ResponseEntity<?> getTicketsExceptNonWip(@RequestParam(name = "operatorId") Long operatorId)
    {
    	ResponseEntity<?> result;
    	try {
			result = adminSvc.getTicketsExceptNonWip(operatorId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println("Illegal argument");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
    	return result;
    }
    
}