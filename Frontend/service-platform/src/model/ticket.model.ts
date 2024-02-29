export interface Ticket {
  id: number;
  message: string;
  userId?: number;
  operatorId?:string;
  status: 'NON_WIP' | 'WIP' | 'CLOSED' | 'CANCELLED';
  statusError?: 'DUPLICATED' | 'FAKE' | 'WRONG_SECTOR';
  priority?: string;
  createDate?: string;
  closingDate?: string;
  type?: 'REFUND' | 'PRODUCT';
  orderId?: number;
}
