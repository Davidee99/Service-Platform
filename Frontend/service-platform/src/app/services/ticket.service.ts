import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  successLog2(...props: any) {
    console.log(props);
  }

  constructor(private http: HttpClient) {}

  getTicket(): Observable<Ticket[]> {
    const url = 'http://localhost:3000/tickets'; //prova end point chiamata dei ticket
    return this.http.get<Ticket[]>(url).pipe(
      tap((x) => {
        console.table(x);
      })
    );
  }
  
  postChatAC(data: any): Observable<any> {
    const url = 'http://localhost:8080/ciao/test2';
    return this.http.post<any>(url, data);
  }

  successLog() {
    console.log('la chiamata è andata');
  }

  userLogin(data: any): Observable<any> {
    const url = 'http://localhost:8080/ciao/test3';
    return this.http.post<any>(url, data);
  }

  employeeLogin(data: any): Observable<any> {
    const url = 'http://localhost:8080/ciao/test2';
    return this.http.post<any>(url, data);
  }
  
  addUserCredentialToSession(userCredential:UserCredential){
    Object.entries(userCredential).forEach(x=>{
      sessionStorage.setItem(x[0],x[1])
      console.log(sessionStorage.getItem(x[0]));
      
    })
    console.log(sessionStorage);
     
  }

  getUserCredentialFromSessionStorage(): UserCredential {
    // Crea un oggetto userCredential con valori predefiniti
    let userCredential: UserCredential = {
      user_id: 0,
      firstname: '',
      lastname: '',
      role: 'USER',
      token: ''
    };

  
    // Recupera i valori dalla sessionStorage e assegnali all'oggetto userCredential
    const userIdStr = sessionStorage.getItem('user_id');
    if (userIdStr !== null) {
      userCredential.user_id = parseInt(userIdStr, 10);
    }
  
    const firstname = sessionStorage.getItem('firstname');
    if (firstname !== null) {
      userCredential.firstname = firstname;
    }
  
    const lastname = sessionStorage.getItem('lastname');
    if (lastname !== null) {
      userCredential.lastname = lastname;
    }
  
    const role = sessionStorage.getItem('role');
    if (role !== null ) {
      userCredential.role = role;
    }
  
    const token = sessionStorage.getItem('token');
    if (token !== null) {
      userCredential.token = token;
    }
  
    return userCredential;
  }
  
}