  import { Component, Input } from '@angular/core';
  import { NgForm } from '@angular/forms';
  import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
  import { Store } from '@ngrx/store';
  import { Ticket } from 'src/model/ticket.model';

  import * as AppActions from 'src/app/store/app.actions';
  import { selectChat } from 'src/app/store/app.selector';
  import { Router } from '@angular/router';
  import { EMPTY } from 'rxjs';
  import { switchMap } from 'rxjs/operators';

  @Component({
    selector: 'app-ticket',
    templateUrl: './ticket.component.html',
    styleUrls: ['./ticket.component.css'],
  })
  export class TicketComponent {
    constructor(
      config: NgbModalConfig,
      private modalService: NgbModal,
      private store: Store,
      private router: Router
    ) {
      // customize default values of modals used by this component tree
      config.backdrop = 'static';
      config.keyboard = false;
    }

    accessCode: string = '';

    submitFormz(form: NgForm) {
      console.log(this.accessCode);
      if (form.valid) {
        this.store.dispatch(AppActions.verifyAccessCode({ accessCode: this.accessCode }));
    
        this.store.select(selectChat).pipe(
          switchMap(chat => {
            if (chat === null) {
              return EMPTY;
            } else {
              this.modalService.dismissAll();
              console.log('qui arrivo', chat.id);
              
              this.router.navigate([`/chat/${chat.id}`]);
              return EMPTY; 
            }
          })
        ).subscribe();
      }
    }
    //da fixare porconeee

    @Input()
    ticket!: Ticket;

    open(content: any) {
      this.modalService.open(content, {
        centered: true,
        modalDialogClass: 'dark-modal',
      });
    }

    ngOnInit() {
      // Ottieni il valore corrente di chat dall'observable selectChat
    }
  }
