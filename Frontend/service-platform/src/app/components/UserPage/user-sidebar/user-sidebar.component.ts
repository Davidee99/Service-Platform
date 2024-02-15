import { Component } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'app-user-sidebar',
  templateUrl: './user-sidebar.component.html',
  styleUrls: ['./user-sidebar.component.css']
})
export class UserSidebarComponent {

  // constructor(private store: Store){
  //   this.credentials$=store.select(selectUserCredential)
  // }
  // ngOnInit(): void {
  //  this.credentials$=this.store.select(selectUserCredential)
  // }
  
  // credentials$:Observable<UserCredential>;

  // userData = {
  //   email: "",
  //   chatAC: "",
  // };

  // submitForm(form: NgForm) {
  //   if (form.valid) {
  //     this.store.dispatch(AppActions.chatACPost({ email: this.userData.email, chatAC: this.userData.chatAC }));
  //     console.log(this.userData);
  //     this.userData.email=''
  //     this.userData.chatAC=''
  //   }
    
  // }

}
