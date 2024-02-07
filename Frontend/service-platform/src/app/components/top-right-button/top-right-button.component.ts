import { Component, ViewEncapsulation } from '@angular/core';
import { NgbActiveModal, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-top-right-button',
  templateUrl: './top-right-button.component.html',
  styleUrls: ['./top-right-button.component.css'],
  encapsulation: ViewEncapsulation.None,
  styles: [`
  .dark-modal .modal-content {
      background-color: #292b2c;
      color: white;
  }
  .dark-modal .close {
      color: white;
  }
  .light-blue-backdrop {
      background-color: #5cb3fd;
  }`],
  providers: [NgbModalConfig, NgbModal]
})
export class TopRightButtonComponent {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
) {
    // customize default values of modals used by this component tree
    config.backdrop = 'static';
    config.keyboard = false;
}


open(content: any) {
    this.modalService.open(content, {centered : true , modalDialogClass:"dark-modal"});
}
}