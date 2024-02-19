import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngrx/store';

import * as AppActions from 'src/app/store/app.actions';
import { NewTicket } from 'src/model/create-ticket.model';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
})
export class LandingPageComponent {

  constructor(private store:Store){}

  userData : NewTicket = {
    firstname: '',
    lastname: '',
    email: '',
    type: 'REFUND',
    message: '',
  };

  submitForm(form: NgForm) {
    if (form.valid) {
      // Esegui l'invio del form
      console.log(this.userData);
      this.store.dispatch(AppActions.createTicket({newTicket:this.userData}))
    }
  }
}
