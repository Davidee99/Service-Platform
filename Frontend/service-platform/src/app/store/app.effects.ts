import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { EMPTY, of } from 'rxjs';
import { catchError, exhaustMap, map } from 'rxjs/operators';
import * as AppActions from './app.actions';
import { TicketService } from '../services/ticket.service';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';

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
            map((list: Ticket[]) => AppActions.ticketsLoaded({ tickets: list }))
          )
      )
    )
  );

  chatACPost$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.chatACPost),
      exhaustMap((action) =>
        this.ticketService
          .postChatAC({ email: action.email, chatAC: action.chatAC })
          .pipe(
            map(() => AppActions.chatACSuccess()),
            catchError((error) => {
              console.error(error);
              return of(AppActions.chatACFailed());
            })
          )
      )
    )
  );

  chatACSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(AppActions.chatACSuccess),
        exhaustMap(() => {
          this.ticketService.successLog();
          return EMPTY;
        })
      ),
    { dispatch: false }
  );

  userLogin$ = createEffect(() =>
  this.actions$.pipe(
    ofType(AppActions.userLogin),
    exhaustMap((action) =>
      this.ticketService
        .userLogin({
          email: action.email,
          password: action.password,
        })
        .pipe(
          map((userCredential:UserCredential) => AppActions.loginSuccess({ userCredential })),
          catchError((error) => {
            console.error(error);
            return of(AppActions.loginFailed());
          })
        )
    )
  )
);

employeeLogin$ = createEffect(() =>
  this.actions$.pipe(
    ofType(AppActions.employeeLogin),
    exhaustMap((action) =>
      this.ticketService
        .employeeLogin({
          email: action.email,
          password: action.password,
        })
        .pipe(
          map((userCredential:UserCredential) => AppActions.loginSuccess({ userCredential })),
          catchError((error) => {
            console.error(error);
            return of(AppActions.loginFailed());
          })
        )
    )
  )
);

}
