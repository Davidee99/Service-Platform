export interface Chat {
  id: number;
  ticketId: number;
  userId: number;
  operatorId: number;
  messages: Message[]|[];
}

export interface Message {
  id: number;
  sender: number;
  message: string;
  timestamp: string;
}
