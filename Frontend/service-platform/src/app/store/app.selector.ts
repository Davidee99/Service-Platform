import { createFeatureSelector, createSelector } from "@ngrx/store";
import { AppState } from "./app.reducer";

export const selectAppState = createFeatureSelector<AppState>('app');

export const selectAllTickets = createSelector(
  selectAppState,
  state => state.tickets
);
export const selectWIPTickets = createSelector(
  selectAppState,
  state => state.ticketsWIP
);
export const selectNONWIPTickets = createSelector(
  selectAppState,
  state => state.ticketsNONWIP
);

export const selectUserCredential = createSelector(
  selectAppState,
  state => state.userCredential
);


export const selectLoginError = createSelector(
  selectAppState,
  state => state.loginError
);


export const selectChat = createSelector(
  selectAppState,
  state => state.chat
);


