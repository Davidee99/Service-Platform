import { createAction, props } from '@ngrx/store';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';

export const loadTickets = createAction('[tickets] loading');

export const ticketsLoaded = createAction(
  '[tickets] loaded',
  props<{ tickets: Ticket[] }>()
);

export const chatACPost = createAction(
  '[chatAC] send',
  props<{ email: string; chatAC: string }>()
);

export const chatACSuccess = createAction('[chatAC] success');

export const chatACFailed = createAction('[chatAC] failed');

export const userLogin = createAction('[login] userLoginSend',
props<{ email: string; password: string }>());

export const employeeLogin = createAction('[login] employeeLoginSend',
props<{ email: string; password: string }>());

export const loginSuccess = createAction('[login] success');

export const loginFailed = createAction('[login] failed');
