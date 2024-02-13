import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Ticket } from 'src/model/ticket.model';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  successLog2(...props:any) {
    console.log(props);
  }

  constructor(private http:HttpClient) {   }
  
  getTicket():Observable<Ticket[]> {
    const url="http://localhost:3000/tickets" //prova end point chiamata dei ticket
    return this.http.get<Ticket[]>(url).pipe(tap(x=>{
      console.table(x)
    }))
}

postChatAC(data: any): Observable<any> {
  const url = 'http://localhost:8080/ciao/test2'
  return this.http.post<any>(url, data);
}

successLog(){
  console.log('la chiamata è andata');
}







}
