import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import * as AppActions from 'src/app/store/app.actions';
import { selectUserCredential } from 'src/app/store/app.selector';
import { UserCredential } from 'src/model/user-credentials.model';
@Component({
  selector: 'app-user-sidebar',
  templateUrl: './user-sidebar.component.html',
  styleUrls: ['./user-sidebar.component.css']
})
export class UserSidebarComponent implements OnInit{
  
  constructor(private store: Store){
    this.credentials$=store.select(selectUserCredential)
  }
  ngOnInit(): void {
   this.credentials$=this.store.select(selectUserCredential)
  }
  
  credentials$:Observable<UserCredential>;

  userData = {
    email: "",
    chatAC: "",
  };

  submitForm(form: NgForm) {
    if (form.valid) {
      this.store.dispatch(AppActions.chatACPost({ email: this.userData.email, chatAC: this.userData.chatAC }));
      console.log(this.userData);
      this.userData.email=''
      this.userData.chatAC=''
    }
    
  }
}
