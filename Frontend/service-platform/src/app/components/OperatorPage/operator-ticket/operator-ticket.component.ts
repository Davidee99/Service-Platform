import { Component, Input } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';



@Component({
  selector: 'app-operator-ticket',
  templateUrl: './operator-ticket.component.html',
  styleUrls: ['./operator-ticket.component.css'],
  
  
})
export class OperatorTicketComponent {

  //expansion panel
  isCollapsed = true;


  constructor(
		config: NgbModalConfig,
		private modalService: NgbModal,
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
  ticket:any;
  
  
  // ticket={
  //   id: 2,
  //   message: 'akjshdkjahdkjaskdh jadkjahdkjahskda',
  //   status: 'wip',
  //   order_id: 7,
  // }
   

    

   



}
