import { createReducer,on } from "@ngrx/store";
import { Ticket } from "src/model/ticket.model";

import * as AppActions from './app.actions'




export interface AppState{
    tickets: Ticket[];
}

const initialState: AppState = {
    tickets: []
}

const _appReducers= createReducer(initialState, 
    //on --> su quale azione si deve mettere in ascolto
    on(AppActions.ticketLoaded,(state,{tickets})=>({...state,tickets})),
    
    )

    export function appReducers(state:any,actions:any){
        return _appReducers(state,actions);
    }


