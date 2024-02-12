import { inject } from "@angular/core";
import { createEffect, Actions, ofType } from "@ngrx/effects";
import { exhaustMap, map } from "rxjs";


import * as AppActions from './app.actions';
import { TicketService } from "../services/ticket.service";
import { Ticket } from "src/model/ticket.model";

export const loadTodos = createEffect(
    (actions$ = inject(Actions), service = inject(TicketService)) => {
      return actions$.pipe(
        ofType(AppActions.loadTickets),
        exhaustMap(() =>
          service
            .getTicket()
            .pipe(
              map((list: Ticket[]) =>
                AppActions.ticketsLoaded({ tickets: list })
              )
            )
        )
      );
    },
    { functional: true }
  ); 