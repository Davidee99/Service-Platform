import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { UserCredential } from 'src/model/user-credentials.model';

import * as AppActions from 'src/app/store/app.actions';
import { selectUserCredential } from 'src/app/store/app.selector';

@Component({
  selector: 'app-operator-sidebar',
  templateUrl: './operator-sidebar.component.html',
  styleUrls: ['./operator-sidebar.component.css']
})
export class OperatorSidebarComponent {
  constructor(private store: Store){
    this.credentials$=store.select(selectUserCredential)
  }
  ngOnInit(): void {
    console.log(sessionStorage);
    this.store.dispatch(AppActions.checkSessionStorage())
   this.credentials$=this.store.select(selectUserCredential)
  }

  credentials$:Observable<UserCredential|null>;
  
}
