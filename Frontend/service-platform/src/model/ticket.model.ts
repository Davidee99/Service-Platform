export interface Ticket  {
    id:number,
    message:string,
    status:"NON_WIP"|"WIP"|"CLOSED",
    order_id?:number
}