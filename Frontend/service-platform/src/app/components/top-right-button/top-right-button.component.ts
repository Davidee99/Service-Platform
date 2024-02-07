import { Component } from '@angular/core';
import { NgbActiveModal, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-top-right-button',
  templateUrl: './top-right-button.component.html',
  styleUrls: ['./top-right-button.component.css'],
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
    this.modalService.open(content);
}
}