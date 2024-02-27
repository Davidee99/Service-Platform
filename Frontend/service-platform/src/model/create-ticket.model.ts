export interface NewTicket {
    firstname: string;
    lastname:string;
    email: string;
    type: 'REFUND' | 'PRODUCT';
    message: string;
  }
  