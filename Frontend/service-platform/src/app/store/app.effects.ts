import { Injectable, inject } from '@angular/core';
import { createEffect, Actions, ofType } from '@ngrx/effects';
import { EMPTY, catchError, exhaustMap, map, of } from 'rxjs';

import * as AppActions from './app.actions';
import { TicketService } from '../services/ticket.service';
import { Ticket } from 'src/model/ticket.model';

@Injectable()
export class AppEffects {
  constructor(private actions$: Actions, private ticketService: TicketService) {}

  loadTickets$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.loadTickets),
      exhaustMap(() =>
        this.ticketService.getTicket().pipe(
          map((list: Ticket[]) => AppActions.ticketsLoaded({ tickets: list }))
        )
      )
    )
  );

  chatACPost$ = createEffect(() =>
  this.actions$.pipe(
    ofType(AppActions.chatACPost),
    exhaustMap((action) =>
      this.ticketService.postChatAC({ email: action.email, chatAC: action.chatAC }).pipe(
        map((response) => {
          console.log(response);
          // Potresti voler emettere un'azione per gestire la risposta
          return AppActions.chatACSuccess();
        }),
        catchError(error => {
          console.error(error);
          // Emetti un'azione chatACFailed in caso di errore
          return of(AppActions.chatACFailed());
        })
      )
    )
  ),
);

chatACSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.chatACSuccess),
      exhaustMap(() => {
        // Call the successLog() method of TicketService
        this.ticketService.successLog();
        
        // Return an empty observable to prevent further action dispatch
        return EMPTY;
      })
    ),
    { dispatch: false } // Set dispatch to false to prevent further action dispatch
  );

    userPost$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.userLogin),
      exhaustMap((action) => {
        this.ticketService.successLog2(action);
        
        return EMPTY;
      })
    ),
    { dispatch: false }
  );


}


