import { AfterViewInit, Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectUserCredential } from 'src/app/store/app.selector';
import { UserCredential } from 'src/model/user-credentials.model';

import * as AppActions from 'src/app/store/app.actions';
import { NgbModalConfig, NgbModal, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.css'],
  encapsulation: ViewEncapsulation.None,
  styles: [
    `
      .dark-modal .modal-content {
        background-color: #292b2c;
        color: white;
      }
      .dark-modal .close {
        color: white;
      }
      .light-blue-backdrop {
        background-color: #5cb3fd;
      }
    `,
  ],
  providers: [NgbModalConfig, NgbModal, NgbDropdownModule],
})
export class ChatPageComponent implements OnInit{

  constructor(private store: Store,
    private modalService:NgbModal){
    this.credentials$=store.select(selectUserCredential )
  }
  @ViewChild('content') modalContent: any;
  ngOnInit(): void {
    console.log(sessionStorage);
    this.store.dispatch(AppActions.checkSessionStorage())
    this.credentials$=this.store.select(selectUserCredential)
    this.modalService.open(this.modalContent)
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

