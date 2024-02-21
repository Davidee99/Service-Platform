import { createAction, props } from "@ngrx/store";
import { Ticket } from "src/model/ticket.model";




//azione 1
export const loadTickets= createAction('[ticket] loading');


//azione 2
export const ticketLoaded= createAction(
    '[ticket] loaded',
    //props--> mi ritorna un ...
    props<{tickets: Ticket[]}>()
);

