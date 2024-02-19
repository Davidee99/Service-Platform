import { Component, OnChanges, OnInit, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbActiveModal, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Store } from '@ngrx/store';
import { Observable, config } from 'rxjs';

import * as AppActions from 'src/app/store/app.actions';
import { selectLoginError } from 'src/app/store/app.selector';

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
export class TopRightButtonComponent implements OnInit, OnChanges {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
    private store: Store,
  
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
    this.isThereLoginError$= store.select(selectLoginError)
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.isThereLoginError$ = this.store.select(selectLoginError);
  }
  ngOnInit(): void {
    this.amIUser = 'user';
    this.isThereLoginError$ = this.store.select(selectLoginError);
  }

  isThereLoginError$: Observable<boolean>;
  amIUser: 'user' | 'employee' = 'user';

  iAmUser() {
    this.amIUser = 'user';
  }
  iAmEmployee() {
    this.amIUser = 'employee';
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
    this.loginData.email = '';
    this.loginData.password = '';

    console.log(this.loginData);
  }

  close(){
  this.store.dispatch(AppActions.resetLoginErrorState())
  this.modalService.dismissAll()
  }
}
