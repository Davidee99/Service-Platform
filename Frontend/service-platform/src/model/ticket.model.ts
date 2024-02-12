export interface Ticket {
  id: number;
  message: string;
  user_id?: number;
  status: 'NON_WIP' | 'WIP' | 'CLOSED' | 'CANCELLED';
  status_error?: 'DUPLICATED' | 'FAKE' | 'WRONG_SECTOR';
  priority?: string;
  create_date?: string;
  closing_date?: string;
  type?: 'REFUND' | 'PRODUCT';
  order_id?: number;
}
