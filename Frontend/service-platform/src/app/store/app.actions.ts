import { ChangeStatusErrorDTO } from './../components/OperatorPage/operator-ticket/operator-ticket.component';
import { createAction, props } from '@ngrx/store';
import { Chat, Message } from 'src/model/chat.model';
import { NewTicket } from 'src/model/create-ticket.model';
import { Ticket } from 'src/model/ticket.model';
import { UserCredential } from 'src/model/user-credentials.model';

//CREATE USER TICKET
export const createTicket = createAction(
  '[newTicket] createTicket',
  props<{ newTicket: NewTicket }>()
);

//USER TICKETS 
export const loadTickets = createAction('[tickets] loading');

export const ticketsLoaded = createAction(
  '[tickets] loaded',
  props<{ tickets: Ticket[] }>()
);

//OPERATOR TICKETS
export const loadOperatorWIPTickets = createAction('[operatorWIPTickets] loading');

export const OperatorWIPTicketsLoaded = createAction(
  '[operatorWIPTickets] loaded',
  props<{ ticketsWIP: Ticket[] }>()
);

export const loadOperatorNONWIPTickets = createAction('[operatorNONWIPTickets] loading');

export const OperatorNONWIPTicketsLoaded = createAction(
  '[operatorNONWIPTickets] loaded',
  props<{ ticketsNONWIP: Ticket[] }>()
);

export const clearTickets = createAction('[tickets] clearCredentials');

export const changeStatusError = createAction('[operatorTicket] changestatusError', props<{ changeStatusErrorDTO : ChangeStatusErrorDTO }>());

export const afterChangestatusError = createAction('[operatorTicket] afterChangestatusError', props<{ updatedTicket : Ticket }>());



//LOGIN
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

export const clearCredentials = createAction('[session] clearCredentials');


//CHAT
export const requestAC = createAction(
  '[chatAC] send',
  props<{ email: string; chatAC: string }>()
);

export const chatACSuccess = createAction('[chatAC] success');

export const chatACFailed = createAction('[chatAC] failed');

export const verifyAccessCode = createAction(
  '[chatAC] verifyAC',
  props<{ accessCode: string }>()
);

export const loadChat = createAction('[chat] loadChat', props<{ chat: Chat }>())

export const clearChat = createAction('[chat] clearChat')

export const sendUpdatedChat = createAction('[chat] sendUpdateChat', props<{ chat: Chat }>())

export const loadUpdatedChat = createAction('[chat] loadUpdateChat', props<{ chat: Chat }>())
