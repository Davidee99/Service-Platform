package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
