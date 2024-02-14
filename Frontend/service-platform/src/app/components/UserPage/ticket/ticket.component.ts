import { Component, Input } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css']
})
export class TicketComponent {
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
  
  
  open(content: any) {
    this.modalService.open(content, {centered : true , modalDialogClass:"dark-modal"});
  }

  accessChat() {
    console.log(this.code);
    
  throw new Error('Method not implemented.');
  }
}

