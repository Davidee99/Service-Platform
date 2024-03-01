import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectAllTickets } from 'src/app/store/app.selector';
import { Ticket } from 'src/model/ticket.model';

import * as AppActions from 'src/app/store/app.actions';

@Component({
  selector: 'app-ticket-container',
  templateUrl: './ticket-container.component.html',
  styleUrls: ['./ticket-container.component.css'],
})
export class TicketContainerComponent implements OnInit {

  constructor(private store: Store) {
    this.tickets$ = store.select(selectAllTickets);
  }

  tickets$: Observable<Ticket[]>;

  ngOnInit(): void {
    this.store.dispatch(AppActions.loadTickets())
    this.tickets$ = this.store.select(selectAllTickets);
    console.log(this.tickets$);
  }


  open: boolean = false;
  closed: boolean = false;

  OpenButton() {
    this.open = true;
    this.closed = false;
  }
  ClosedButton() {
    this.closed = true;
    this.open = false;
  }


}
