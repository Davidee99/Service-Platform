import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { FormControl, NgForm, Validators } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Store } from '@ngrx/store';
import { Ticket } from 'src/model/ticket.model';
import * as AppActions from 'src/app/store/app.actions';





@Component({
  selector: 'app-operator-ticket',
  templateUrl: './operator-ticket.component.html',
  styleUrls: ['./operator-ticket.component.css'],
  
  
})
export class OperatorTicketComponent {

changeStatusErrorDTO : ChangeStatusErrorDTO = {
  ticketId : -1,
  status : ""
}

  //expansion panel
  isCollapsed = true;

  //menù mark as...
  showMarkMenu = false;

  constructor(
		config: NgbModalConfig,
		private modalService: NgbModal,
    private store: Store
	) {
		// customize default values of modals used by this component tree
		config.backdrop = 'static';
		config.keyboard = false;
    

    
	}
  check: boolean = true

  code:string = "";

  submitForm(form: NgForm) {
    if (form.valid) {
      // Esegui l'invio del form
      console.log(this.code);
      
    }
  }

  @Input()
  ticket!: Ticket;

  toggleMarkMenu() {
    this.showMarkMenu = !this.showMarkMenu;
  }

  markAs(option: string) {
    // Qui puoi fare qualcosa con l'opzione selezionata, ad esempio inviare una richiesta al backend
    console.log("Marked as:", option);
    // Puoi anche chiudere il menu dopo aver selezionato un'opzione
    this.showMarkMenu = false;
  }

  setStatusError(){
    this.changeStatusErrorDTO.ticketId = this.ticket.id;
    this.store.dispatch(AppActions.changeStatusError({ changeStatusErrorDTO : this.changeStatusErrorDTO})); //Da fare: Aggiornare ticket child
   
  }

}

export interface ChangeStatusErrorDTO {
  ticketId : number,
  status : string
}