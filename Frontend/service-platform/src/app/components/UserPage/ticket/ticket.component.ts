import { Router, RouterModule } from '@angular/router';
import { ChatService } from './../../chat/chat.service';
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
    private chatService : ChatService,
    private router : Router
	) {
		// customize default values of modals used by this component tree
		config.backdrop = 'static';
		config.keyboard = false;
	}

  check: boolean = true

  code:string = "";

  ticketId : number = 1;

  submitForm(form: NgForm) {
    if (form.valid) {
      // Esegui l'invio del form
      console.log(this.code);
      this.chatService.getChatByTicketId( this.ticketId ).subscribe(response => {
        // Controlla la risposta del backend
        this.router.navigate(['/chat', this.ticketId]);

      });
    }
  }
  
  @Input()
  ticket:any;
   

    open(content: any) {
      this.modalService.open(content, {centered : true , modalDialogClass:"dark-modal"});
    }
}

