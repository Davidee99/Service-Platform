import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

import * as AppActions from 'src/app/store/app.actions';
@Component({
  selector: 'app-user-sidebar',
  templateUrl: './user-sidebar.component.html',
  styleUrls: ['./user-sidebar.component.css']
})
export class UserSidebarComponent {
  
  constructor(private store: Store){}
  

  userData = {
    email: "",
    chatAC: "",
  };

  submitForm(form: NgForm) {
    if (form.valid) {
      this.store.dispatch(AppActions.chatACPost({ email: this.userData.email, chatAC: this.userData.chatAC }));
      console.log(this.userData);
    }
    
  }
}
