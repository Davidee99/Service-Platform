import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectAllTickets } from 'src/app/store/app.selector';
import { Ticket } from 'src/model/ticket.model';

import * as AppActions from 'src/app/store/app.actions';

@Component({
  selector: 'app-operator-ticket-container',
  templateUrl: './operator-ticket-container.component.html',
  styleUrls: ['./operator-ticket-container.component.css']
})
export class OperatorTicketContainerComponent  implements OnInit{

  constructor(private store: Store) {
    this.tickets$ = store.select(selectAllTickets);
  }
  
  ngOnInit(): void {
    this.store.dispatch(AppActions.loadTickets())
    this.tickets$ = this.store.select(selectAllTickets);
    console.log(this.tickets$);
  }


  tickets$: Observable<Ticket[]>;

  //Botton
  open: boolean = false;
  closed: boolean = false;

  OpenButton() {
    this.open = true;
    this.closed = false;
    console.log('OpenButton' + this.open);
  }
  ClosedButton() {
    this.closed = true;
    this.open = false;
    console.log('ClosedButton' + this.closed);
  }

 
}
