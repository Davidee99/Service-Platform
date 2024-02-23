import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewChild,
  ViewEncapsulation,
} from '@angular/core';
import { Store } from '@ngrx/store';
import { EMPTY, Observable, filter, of, switchMap, take } from 'rxjs';
import { selectChat, selectUserCredential } from 'src/app/store/app.selector';
import { UserCredential } from 'src/model/user-credentials.model';

import * as AppActions from 'src/app/store/app.actions';
import {
  NgbModalConfig,
  NgbModal,
  NgbDropdownModule,
} from '@ng-bootstrap/ng-bootstrap';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Message } from 'src/model/chat.model';
import { format } from 'date-fns';

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
export class ChatPageComponent implements OnInit, AfterViewInit, OnDestroy {
  constructor(
    private store: Store,
    private modalService: NgbModal,
    config: NgbModalConfig,
    private router: Router
  ) {
    this.credentials$ = store.select(selectUserCredential);
    config.backdrop = 'static';
    config.keyboard = false;
  }

  @ViewChild('contentL') contentL!: TemplateRef<any>;

  credentials$: Observable<UserCredential | null>;


  accessCode: string = '';


  messageToSend: Message = {
    sender: 0,
    message: '',
    timestamp:format(new Date('2024-02-20T10:56:47.000+00:00'), "yyyy-MM-dd'T'HH:mm:ss.SSSxxx")
  };


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
        timestamp: '2024-02-20T10:56:47.000+00:00',
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: '2024-02-20T10:57:47.000+00:00',
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: '2024-02-20T10:58:47.000+00:00',
      },
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: '2024-02-20T10:59:47.000+00:00',
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: '2024-02-20T11:56:47.000+00:00',
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: '2024-02-20T12:56:47.000+00:00',
      },
      {
        id: 1,
        sender: 19,
        message: 'Ciao, come stai?',
        timestamp: '2024-02-20T13:56:47.000+00:00',
      },
      {
        id: 4,
        sender: 19,
        message: 'Ciao, come stai?2',
        timestamp: '2024-02-20T14:56:47.000+00:00',
      },
      {
        id: 2,
        sender: 3,
        message: 'Ciao! Sto bene, grazie!',
        timestamp: '2024-02-20T15:56:47.000+00:00',
      },
    ],
  };

  ngOnDestroy(): void {
    this.modalService.dismissAll();
    this.store.dispatch(AppActions.clearChat());
  }

  ngOnInit(): void {
    this.store.dispatch(AppActions.checkSessionStorage());
  }

  ngAfterViewInit(): void {
    this.credentials$
      .pipe(
        switchMap((credentials) => {
          if (credentials === null) {
            this.open();
            return EMPTY;
          } else {
            return EMPTY;
          }
        })
      )
      .subscribe();
  }

  onInputChange(event: any) {
    this.messageToSend.message = event;
  }

  sendMessage() {
    this.credentials$.pipe(
      switchMap((credentials) => {
        if (credentials === null) {
          this.messageToSend.sender = this.chat.userId;
        } else {
          this.messageToSend.sender = credentials!.user_id;
        }
        return of({ ...this.messageToSend });
      })
    ).subscribe((message) => {
      this.messageToSend.timestamp = format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
      this.chat.messages.push(message);
      
  
      this.messageToSend.message = '';
  
      // Azione da triggerare e pulizia del campo di input
      console.log('Messaggio inviato:', message);
    });
  }
  

  submitForm(form: NgForm) {
    console.log(this.accessCode);
    if (form.valid) {
      this.store.dispatch(
        AppActions.verifyAccessCode({ accessCode: this.accessCode })
      );
    }

    this.store
      .select(selectChat)
      .pipe(
        filter((chat) => chat !== null),
        take(1)
      )
      .subscribe((chat) => {
        this.modalService.dismissAll();
      });
  }

  open() {
    this.modalService.open(this.contentL, {
      centered: true,
      modalDialogClass: 'dark-modal',
      fullscreen: true,
    });
  }
}
