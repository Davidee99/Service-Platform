export interface NewTicket {
  firstName: string;
  lastName:string;
    email: string;
    type: 'REFUND' | 'PRODUCT';
    message: string;
    orderId? :String;
  }
  