import { createFeatureSelector, createSelector } from "@ngrx/store";
import { AppState } from "./app.reducer";


//creazione selettore 
export const selectAppstate = createFeatureSelector<AppState>('app');

export const selectorAllTickets= createSelector(selectAppstate,state=>state.tickets)
