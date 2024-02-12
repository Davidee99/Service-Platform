import { Component, OnInit } from '@angular/core';
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
    console.log('OpenButton' + this.open);
  }
  ClosedButton() {
    this.closed = true;
    this.open = false;
    console.log('ClosedButton' + this.closed);
  }

  tickets: any[] = [
    {
      id: 2,
      message: 'akjshdkjahdkjaskdh jadkjahdkjahskda',
      status: 'wip',
      order_id: 7,
    },
    {
        id: 7,
        message: 'asd12 eq',
        status: 'no-wip',
        order_id: 7,
      },
      {
        id: 4,
        message: 'asd12 jadkjahdkjahskda',
        status: 'closed',
        order_id: 7,
      },
      {
        id: 3,
        message: 'asd12 as',
        status: 'closed',
        order_id: 7,
      },
      {
        id: 1,
        message: 'asd12 jadkjahdkjahskda',
        status: 'wip',
        order_id: 7,
      },
  ];
}
