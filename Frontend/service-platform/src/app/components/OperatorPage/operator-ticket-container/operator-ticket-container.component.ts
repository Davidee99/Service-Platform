import { map } from 'rxjs/operators';
import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectWIPTickets , selectNONWIPTickets } from 'src/app/store/app.selector';
import { Ticket } from 'src/model/ticket.model';

import * as AppActions from 'src/app/store/app.actions';

@Component({
  selector: 'app-operator-ticket-container',
  templateUrl: './operator-ticket-container.component.html',
  styleUrls: ['./operator-ticket-container.component.css']
})
export class OperatorTicketContainerComponent  implements OnInit , OnDestroy , OnChanges{

  constructor(private store: Store) {
    this.ticketsWIP$ = store.select(selectWIPTickets);
    this.ticketsNONWIP$ = store.select(selectNONWIPTickets);
  }
  
  ngOnInit(): void {
    this.store.dispatch(AppActions.loadOperatorWIPTickets())
    this.ticketsWIP$ = this.store.select(selectWIPTickets);
  }

  ngOnDestroy(): void {
    this.store.dispatch(AppActions.clearTickets());
    this.ticketsWIP$.pipe(map( x => console.log("Ciao " , x)
    ))
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log("Ci siamo");
}

  ticketsWIP$: Observable<Ticket[]>;
  ticketsNONWIP$: Observable<Ticket[]>;

  //Botton
  open: boolean = false;
  closed: boolean = false;

  WIPButton() {
    this.store.dispatch(AppActions.loadOperatorWIPTickets())
    this.ticketsWIP$ = this.store.select(selectWIPTickets);
    this.open = true;
    this.closed = false;
  }
  NONWIPButton() {
    this.store.dispatch(AppActions.loadOperatorNONWIPTickets())
    this.ticketsNONWIP$ = this.store.select(selectNONWIPTickets);
    this.closed = true;
    this.open = false;
  }

 
}
