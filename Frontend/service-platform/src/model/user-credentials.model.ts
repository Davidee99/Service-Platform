export interface UserCredential {
    user_id:number;
    firstname:string;
    lastname:string;
    role:"ADMIN"|"OPERATOR"|"USER";
    token:string;
    email?:string;
}