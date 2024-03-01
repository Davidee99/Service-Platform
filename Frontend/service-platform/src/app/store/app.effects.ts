import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { EMPTY, concat, forkJoin, merge, of } from 'rxjs';
import { catchError, exhaustMap, map, switchMap, tap } from 'rxjs/operators';
import * as AppActions from './app.actions';
import { TicketService } from '../services/ticket.service';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class AppEffects {
  constructor(
    private actions$: Actions,
    private ticketService: TicketService
  ) { }

  loadTickets$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.loadTickets),
      exhaustMap(() =>
        this.ticketService
          .getUserTickets()
          .pipe(
            map((list: Ticket[]) => AppActions.ticketsLoaded({ tickets: list }))
          )

      )
    )
  );

  loadOperatorWIPTickets$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.loadOperatorWIPTickets),
      exhaustMap(() =>
        this.ticketService
          .getTicketsWIPByOperatorId()
          .pipe(
            map((list: Ticket[]) => AppActions.OperatorWIPTicketsLoaded({ ticketsWIP: list }))
          )
      )
    )
  );
  loadOperatorNONWIPTickets$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.loadOperatorNONWIPTickets),
      exhaustMap(() =>
        this.ticketService
          .getTicketsNONWIPByOperatorId()
          .pipe(
            map((list: Ticket[]) => AppActions.OperatorNONWIPTicketsLoaded({ ticketsNONWIP: list }))
          )
      )
    )
  );

changeStatusError$ = createEffect(() =>
this.actions$.pipe(
  ofType(AppActions.changeStatusError),
  exhaustMap((action) =>
    this.ticketService
      .putChangeStatusErrorTicket(action.changeStatusErrorDTO)
      .pipe(
        map((resultTicket) => AppActions.afterChangestatusError({updatedTicket : resultTicket})),
      )
  )
)
);


  chatACPost$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.requestAC),
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
            map((userCredential: UserCredential) => ({
              userCredential,
              type: AppActions.loginSuccess.type, // Add the action type manually
            })),
            tap((action) => {
              this.ticketService.addUserCredentialToSession(
                action.userCredential
              );
            }),
            catchError((error) => {
              console.error('Error during login:', error);
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
            map((userCredential: UserCredential) => ({
              userCredential,
              type: AppActions.loginSuccess.type, // Add the action type manually
            })),
            tap((action) => {
              this.ticketService.addUserCredentialToSession(
                action.userCredential
              );
            }),
            catchError((error) => {
              console.error('Error during login:', error);
              return of(AppActions.loginFailed());
            })
          )
      )
    )
  );


  checkSessionStorage$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.checkSessionStorage),
      switchMap(() => {
        const userCredential = this.ticketService.getUserCredentialFromSessionStorage(); // Assume che questo metodo esista in UserService
        if (userCredential) {
          return of(AppActions.sessionChecked({ userCredential }));
        } else {
          // Puoi gestire altri casi come la sessione non trovata
          return of(AppActions.sessionChecked({ userCredential: null }));
        }
      })
    )
  );


  newTicketPost$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.createTicket),
      exhaustMap((action) =>
        this.ticketService
          .createNewTicket(action.newTicket)
          .pipe(
            map((userCredential: UserCredential) => ({
              userCredential,
              type: AppActions.loginSuccess.type, // Add the action type manually
            })),
            tap((action) => {
              this.ticketService.successLog;
            }),
            catchError((error) => {
              console.error('Error during login:', error);
              return of(AppActions.loginFailed());
            })
          )
      )
    )
  );



  verifyAccessCode$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.verifyAccessCode),
      switchMap((action) =>
        this.ticketService.verifyACAndGetChatUser(action.accessCode).pipe(
          map((chat) => {
            console.log('Operazione completata con successo');
            console.log(chat);
            return AppActions.loadChat({ chat: chat }); // Restituisci un'azione di successo
          }),
          catchError((error) => {
            console.log('Errore durante l\'operazione:', error);
            return of(AppActions.loginFailed()); // Restituisci un'azione di errore
          })
        )
      )
    )
  );


  updateChat$ = createEffect(() =>
    this.actions$.pipe(
      ofType(AppActions.sendUpdatedChat),
      switchMap((action) =>
        this.ticketService.updatedMessages(action.chat).pipe(
          map(() => {
            console.log('Operazione completata con successo aggiungi mex');
            return AppActions.loadChat({ chat: action.chat }); // Restituisci un'azione di successo
          }),
          catchError((error) => {
            console.log('Errore durante l\'operazione:', error);
            return of(AppActions.loginFailed()); // Restituisci un'azione di errore
          })
        )
      )
    )

  )


}
