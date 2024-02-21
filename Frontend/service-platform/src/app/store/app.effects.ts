import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { EMPTY, of } from 'rxjs';
import { catchError, exhaustMap, map, switchMap, tap } from 'rxjs/operators';
import * as AppActions from './app.actions';
import { Ticket } from 'src/model/ticket.model';
import { TicketService } from '../service/ticket.service';

@Injectable()
export class AppEffects {
  constructor(
    private actions$: Actions,
    private ticketService: TicketService
  ) {}

  loadTickets$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.loadTickets),
      exhaustMap(() =>
        this.ticketService
          .getTicket()
          .pipe(
            map((list: Ticket[]) => AppActions.ticketLoaded({ tickets: list }))
          )
      )
    )
  );


}
