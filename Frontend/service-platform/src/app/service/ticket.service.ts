import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ticket } from 'src/model/ticket.model';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

   adminEndpoint='http://localhost:3000/tickets'

  constructor(private http : HttpClient) { 
  }

   getTicket(){
    return this.http.get<Ticket[]>(this.adminEndpoint)

  }


  
}
