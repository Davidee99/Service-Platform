import { createAction, props } from "@ngrx/store";
import { Ticket } from "src/model/ticket.model";

export const loadTickets = createAction('[tickets] loading');

export const ticketsLoaded = createAction('[tickets] loaded', props<{tickets:Ticket[]}>());