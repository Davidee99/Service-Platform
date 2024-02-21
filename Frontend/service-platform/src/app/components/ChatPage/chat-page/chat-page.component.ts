import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectUserCredential } from 'src/app/store/app.selector';
import { UserCredential } from 'src/model/user-credentials.model';

import * as AppActions from 'src/app/store/app.actions';

@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.css'],
})
export class ChatPageComponent {

  constructor(private store: Store){
    this.credentials$=store.select(selectUserCredential )
  }

  ngOnInit(): void {
    console.log(sessionStorage);
    this.store.dispatch(AppActions.checkSessionStorage())
   this.credentials$=this.store.select(selectUserCredential)
  }

  credentials$:Observable<UserCredential|null>;

  chat: any = {
    id: 1,
    ticketId: 1,
    userId: 3,
    operatorId: 19,
    messages: [
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: "2024-02-20T10:56:47.000+00:00",
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: "2024-02-20T10:57:47.000+00:00",
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: "2024-02-20T10:58:47.000+00:00",
      },
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: "2024-02-20T10:59:47.000+00:00",
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: "2024-02-20T11:56:47.000+00:00",
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: "2024-02-20T12:56:47.000+00:00",
      },
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: "2024-02-20T13:56:47.000+00:00",
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: "2024-02-20T14:56:47.000+00:00",
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: "2024-02-20T15:56:47.000+00:00",
      },
    ],
  };
}
//devo controllare se ho un userid in sessione  se è di un operator i messaggi di operator vanno a destra
//else vanno a sinistra
