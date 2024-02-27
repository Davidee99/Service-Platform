export interface UserCredential {
    user_id:number;
    firstname:string;
    lastname:string;
    role:"ADMIN"|"OPERATOR"|"USER"|string;
    token:string;
    email?:string;
}