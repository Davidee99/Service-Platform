import { createAction, props } from '@ngrx/store';
import { NewTicket } from 'src/model/create-ticket.model';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';

export const createTicket = createAction(
  '[newTicket] createTicket',
  props<{ newTicket: NewTicket }>()
);

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

export const userLogin = createAction(
  '[login] userLoginSend',
  props<{ email: string; password: string }>()
);

export const employeeLogin = createAction(
  '[login] employeeLoginSend',
  props<{ email: string; password: string }>()
);

export const loginSuccess = createAction(
  '[login] success',
  props<{ userCredential: UserCredential }>()
);

export const loginFailed = createAction('[login] failed');

export const resetLoginErrorState = createAction(
  '[login] loginErrorStateReset'
);

export const checkSessionStorage = createAction('[session] checkStorage');

export const sessionChecked = createAction(
  '[session] checked',
  props<{ userCredential: UserCredential | null }>()
);
