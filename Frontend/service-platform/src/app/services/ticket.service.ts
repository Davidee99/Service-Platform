import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { Chat } from 'src/model/chat.model';
import { NewTicket } from 'src/model/create-ticket.model';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  successLog2(...props: any) {
    console.log(props);
  }

  constructor(private http: HttpClient, private router: Router) { }

  createNewTicket(newTicket: NewTicket): Observable<any> {
    const url = 'http://localhost:8080/ciao/test2'
    return this.http.post<NewTicket>(url, newTicket).pipe(
      tap((x) => {
        console.table(x);
      })
    );
  }

  getWipTicket(): Observable<Ticket[]> {
    const userId = sessionStorage.getItem('id');
    const url = "http://localhost:8082/api/dispatcher/ticket-service/ticket/getWIPTickets/?userId="+userId; //prova end point chiamata dei ticket
    return this.http.get<Ticket[]>(url);
  }

  getNonWipTicket() : Observable<Ticket[]> {
    const userId = sessionStorage.getItem('id');
    const url = "http://localhost:8082/api/dispatcher/ticket-service/ticket/getNonWIPTickets/?userId="+userId; //prova end point chiamata dei ticket
    return this.http.get<Ticket[]>(url);
  }

  postChatAC(data: any): Observable<any> {
    const url = 'http://localhost:8080/ciao/test2';
    return this.http.post<any>(url, data);
  }

  successLog() {
    console.log('la chiamata è andata');
  }

  userLogin(data: any): Observable<any> {
    const url = 'http://localhost:8082/api/authentication/login/user';
    return this.http.post<any>(url, data);
  }

  employeeLogin(data: any): Observable<any> {
    const url = 'http://localhost:8080/ciao/test2';
    return this.http.post<any>(url, data);
  }

  // Trasformo l'oggetto userCredential in un array di array con all'interno le info dell'utente che assegno al session Storage
  addUserCredentialToSession(userCredential: UserCredential) {
    Object.entries(userCredential).forEach(x => {
      sessionStorage.setItem(x[0], x[1]);
    })
    console.log(sessionStorage);
  }

  getUserCredentialFromSessionStorage(): UserCredential | null {
    const userIdStr = sessionStorage.getItem('id');
    const firstname = sessionStorage.getItem('firstName');
    const lastname = sessionStorage.getItem('lastName');
    const role = sessionStorage.getItem('role');
    const token = sessionStorage.getItem('token');

    if (userIdStr === null && firstname === null && lastname === null && role === null && token === null) {
      return null;
    }

    return {
      user_id: userIdStr ? parseInt(userIdStr, 10) : 0,
      firstname: firstname || '',
      lastname: lastname || '',
      role: role || 'USER',
      token: token || ''
    };
  }

  verifyACAndGetChatUser(accessCode: string) {
    const url = 'http://localhost:3000/chat'
    // const headers = new HttpHeaders().set('AccessCode', accessCode);
    return this.http.get<Chat>(url).pipe(
      tap((x) => {
        console.table(x);
      })
    );
  }



  updatedMessages(chat: Chat) {
    const url = 'http://localhost:8080/ciao/testMessage'
    let message = chat.messages[chat.messages.length - 1]
    return this.http.post(url, message)
  }


}