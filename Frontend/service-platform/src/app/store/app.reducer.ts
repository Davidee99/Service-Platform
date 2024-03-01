import { createReducer, on } from '@ngrx/store';

import * as AppActions from './app.actions';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';
import { Chat } from 'src/model/chat.model';

export interface AppState {
  tickets: Ticket[];
  ticketsWIP: Ticket[];
  ticketsNONWIP: Ticket[];
  loginError: any;
  userCredential: UserCredential | null;
  chat: Chat | null;
}

const initialState: AppState = {
  tickets: [],
  ticketsWIP: [],
  ticketsNONWIP: [],
  loginError: null,
  userCredential: null,
  chat: null,
};

const _appReducers = createReducer(
  initialState,
  on(AppActions.ticketsLoaded, (state, { tickets }) => ({ ...state, tickets })),

  on(AppActions.OperatorWIPTicketsLoaded, (state, { ticketsWIP }) => ({ ...state, ticketsWIP })),

  on(AppActions.OperatorNONWIPTicketsLoaded, (state, { ticketsNONWIP }) => ({ ...state, ticketsNONWIP })),

  on(AppActions.clearTickets, (state) => ({ ...state, ticketsWIP: [], ticketsNONWIP: [], tickets: [] })),

  on(AppActions.afterChangestatusError, (state, { updatedTicket }) => {
    const updatedTickets = state.tickets.map(ticket =>
      ticket.id === updatedTicket.id ? updatedTicket : ticket
    );
    return { ...state, tickets: updatedTickets };
  }),

  on(AppActions.loginSuccess, (state, { userCredential }) => ({
    ...state,
    userCredential,
  })),

  on(AppActions.sessionChecked, (state, { userCredential }) => ({
    ...state,
    userCredential,
  })),

  on(AppActions.loginFailed, (state) => ({ ...state, loginError: true })),

  on(AppActions.resetLoginErrorState, (state) => ({
    ...state,
    loginError: false,
  })),

  on(AppActions.clearCredentials, (state) => ({
    ...state,
    userCredential: null,
  })),

  on(AppActions.loadChat, (state, { chat }) => ({
    ...state,
    chat,
  })),

  on(AppActions.clearChat, (state) => ({
    ...state,
    chat: null,
  }))

);

export function appReducers(state: any, action: any) {
  return _appReducers(state, action);
}
