import { createReducer, on } from "@ngrx/store";

import * as AppActions from './app.actions';
import { Ticket } from "src/model/ticket.model";
import { UserCredential } from "src/model/user-credentials.model";

export interface AppState {
    tickets: Ticket[];
    response:any;
    userCredential:UserCredential;
  }
  
  const initialState: AppState = {
    tickets: [],
    response:null,
    userCredential:{
      user_id:0,
    firstname:"",
    lastname:"",
    role:"USER",
    token:""
    }
  };

  const _appReducers = createReducer(
    initialState,
    on(AppActions.ticketsLoaded, (state, {tickets}) => ({ ...state, tickets})),
    on(AppActions.loginSuccess, (state, {userCredential}) => ({ ...state, userCredential})),
  );
  
  
  export function appReducers(state: any, action: any) {
      return _appReducers(state, action)
  }