import { createReducer, on } from '@ngrx/store';

import * as AppActions from './app.actions';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';

export interface AppState {
  tickets: Ticket[];
  loginError: any;
  userCredential: UserCredential | null;
}

const initialState: AppState = {
  tickets: [],
  loginError: null,
  userCredential: null,
};

const _appReducers = createReducer(
  initialState,
  on(AppActions.ticketsLoaded, (state, { tickets }) => ({ ...state, tickets })),
  on(AppActions.loginSuccess, (state, { userCredential }) => ({
    ...state,
    userCredential,
  })),
  on(AppActions.sessionChecked, (state, { userCredential }) => ({
    ...state,
    userCredential,
  })),
  on(AppActions.loginFailed, (state )=>({...state,loginError:true})),
  on(AppActions.resetLoginErrorState, (state )=>({...state,loginError:false}))
);

export function appReducers(state: any, action: any) {
  return _appReducers(state, action);
}
