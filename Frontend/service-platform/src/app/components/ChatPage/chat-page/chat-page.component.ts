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
    operatorId: 1,
    messages: [
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: new Date().getFullYear(),
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: new Date().getFullYear(),
      },
    ],
  };
}
//devo controllare se ho un userid in sessione  se è di un operator i messaggi di operator vanno a destra
//else vanno a sinistra
