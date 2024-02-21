import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import * as AppActions from 'src/app/store/app.actions';
import { selectUserCredential } from 'src/app/store/app.selector';
import { UserCredential } from 'src/model/user-credentials.model';

@Component({
  selector: 'app-access-code-form',
  templateUrl: './access-code-form.component.html',
  styleUrls: ['./access-code-form.component.css']
})
export class AccessCodeFormComponent {

  constructor(private store: Store){
  }

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
