import { Component, Input } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModalConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
//chart.js
import Chart from 'chart.js/auto';


@Component({
  selector: 'app-admin-task',
  templateUrl: './admin-task.component.html',
  styleUrls: ['./admin-task.component.css'],
  
})
export class AdminTaskComponent {

  




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
  
  
  




  selected = 'Refund';
  change(ticket:any) {
    // Logica per l'invio
    console.log('Type changed');
    
    //cambio type del ticket
    if (this.selected == 'Refund') {
      ticket.type='Product';
    }else{
      ticket.type='Refund';
    }
    
    
    
    
}}




  


