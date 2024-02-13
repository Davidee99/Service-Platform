import { Component, ViewEncapsulation } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Store } from '@ngrx/store';

import * as AppActions from 'src/app/store/app.actions';

@Component({
  selector: 'app-top-right-button',
  templateUrl: './top-right-button.component.html',
  styleUrls: ['./top-right-button.component.css'],
  encapsulation: ViewEncapsulation.None,
  styles: [
    `
      .dark-modal .modal-content {
        background-color: #292b2c;
        color: white;
      }
      .dark-modal .close {
        color: white;
      }
      .light-blue-backdrop {
        background-color: #5cb3fd;
      }
    `,
  ],
  providers: [NgbModalConfig, NgbModal],
})
export class TopRightButtonComponent {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
    private store: Store
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  amIUser: 'user' | 'employee' = 'user';

  iAmUser() {
    this.amIUser = 'user';
    console.log(this.amIUser);
  }
  iAmEmployee() {
    this.amIUser = 'employee';
    console.log(this.amIUser);
  }

  loginData = {
    email: '',
    password: '',
  };

  open(content: any) {
    this.modalService.open(content, {
      centered: true,
      modalDialogClass: 'dark-modal',
    });
  }

  submitLogin() {
    if (this.amIUser == 'user') {
      this.store.dispatch(AppActions.userLogin(this.loginData));
    } else {
      this.store.dispatch(AppActions.employeeLogin(this.loginData));
    }

    console.log(this.loginData);
  }
}
