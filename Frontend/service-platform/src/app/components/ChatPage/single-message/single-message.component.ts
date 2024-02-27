import { Component, Input, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectUserCredential } from 'src/app/store/app.selector';
import { UserCredential } from 'src/model/user-credentials.model';

import * as AppActions from 'src/app/store/app.actions';


@Component({
  selector: 'app-single-message',
  templateUrl: './single-message.component.html',
  styleUrls: ['./single-message.component.css']
})
export class SingleMessageComponent{
@Input() message:any
}
