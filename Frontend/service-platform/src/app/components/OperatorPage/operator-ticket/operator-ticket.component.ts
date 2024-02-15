import { Component, Input, ViewChild } from '@angular/core';
import { FormControl, NgForm, Validators } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';





@Component({
  selector: 'app-operator-ticket',
  templateUrl: './operator-ticket.component.html',
  styleUrls: ['./operator-ticket.component.css'],
  
  
})
export class OperatorTicketComponent {

  //expansion panel
  isCollapsed = true;

  //menù mark as...
  showMarkMenu = false;




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


  toggleMarkMenu() {
    this.showMarkMenu = !this.showMarkMenu;
  }

  markAs(option: string) {
    // Qui puoi fare qualcosa con l'opzione selezionata, ad esempio inviare una richiesta al backend
    console.log("Marked as:", option);
    // Puoi anche chiudere il menu dopo aver selezionato un'opzione
    this.showMarkMenu = false;
  }
  
  
  // tickett={
  //   id: 9,
  //   user_id:4,
  //   message: 'messaggio custom del tickett',
  //   status: 'wip',
  //   priority: 'hight',
  //   order_id: 7,
  // }




  selected = 'option2';
  send() {
    // Logica per l'invio
    console.log('Sending...');
  }

   

    

   



}
