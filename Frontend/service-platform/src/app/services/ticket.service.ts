import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Ticket } from 'src/model/ticket.model';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http:HttpClient) {   }
  url="http://localhost:3000/tickets" //prova end point chiamata dei ticket

  getTicket():Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.url).pipe(tap(x=>{
      console.table(x)
    }))
}
}
