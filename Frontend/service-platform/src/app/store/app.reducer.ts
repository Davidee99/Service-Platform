import { createReducer, on } from "@ngrx/store";

import * as AppActions from './app.actions';
import { Ticket } from "src/model/ticket.model";

export interface AppState {
    tickets: Ticket[];
    response:any
  }
  
  const initialState: AppState = {
    tickets: [],
    response:null
  };

  const _appReducers = createReducer(
    initialState,
    on(AppActions.ticketsLoaded, (state, {tickets}) => ({ ...state, tickets})),
  );
  
  
  export function appReducers(state: any, action: any) {
      return _appReducers(state, action)
  }