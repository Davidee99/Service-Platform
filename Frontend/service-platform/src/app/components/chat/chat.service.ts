import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, delay, finalize, tap } from 'rxjs';
import { ChatResponse } from 'src/model/chatDTO.model';

const chatResponse: ChatResponse = {
  chatId: "chat123",
  ticketId: "ticket456",
  messages: [
      { content: "Ciao!", timestamp: 1644787200, role: "User" },
      { content: "Salve, come posso aiutarla?", timestamp: 1644790800, role: "Operator" },
      { content: "Ho un problema con il mio ordine.", timestamp: 1644794400, role: "User" }
  ],
  attachments: [
      { link: "https://example.com/image1.jpg", timestamp: 1644790800, role: "Operator" },
      { link: "https://example.com/image2.jpg", timestamp: 1644794400, role: "User" }
  ]
};

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) {}


  // getChatByTicketId(ticketId: any) {
  //   // Esegui la richiesta HTTP al backend, ad esempio:
  //   return this.http.get(`http://localhost:8084/api/chat-service/getChatByTicket?ticketId=${ticketId}`);
  // }

  getChatByTicketId(ticketId: any): Observable<ChatResponse> {
    // Simulo una chiamata HTTP che ritorna i dati simulati

    return of(chatResponse).pipe(
      delay(1000), // Delay di 1 secondo per simulare una chiamata asincrona
      tap(response => console.log('Response from getChatByTicketId:', response)),
    );

  }
  
}



