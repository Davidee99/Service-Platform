import { Component, OnChanges, OnInit, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal, NgbDropdownMenu, NgbDropdownModule, NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { Store } from '@ngrx/store';
import { Observable, config } from 'rxjs';

import * as AppActions from 'src/app/store/app.actions';
import { selectLoginError, selectUserCredential } from 'src/app/store/app.selector';
import { UserCredential } from 'src/model/user-credentials.model';
import { LandingPageComponent } from '../LandingPage/landing-page/landing-page.component';

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
  providers: [NgbModalConfig, NgbModal, NgbDropdownModule],
})
export class TopRightButtonComponent implements OnInit, OnChanges {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
    private store: Store,
    private router: Router
    
    ) {
      config.backdrop = 'static';
      config.keyboard = false;
      this.isThereLoginError$= store.select(selectLoginError)
      this.credentials$= this.store.select(selectUserCredential)
    }
    ngOnChanges(changes: SimpleChanges): void {
      this.isThereLoginError$ = this.store.select(selectLoginError);
    }
    ngOnInit(): void {
    this.amIUser = 'user';
    this.isThereLoginError$ = this.store.select(selectLoginError);
    this.credentials$= this.store.select(selectUserCredential)
  }
  credentials$:Observable<UserCredential|null>;
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
  
  logout() {
    this.router.navigate(['/'])
    sessionStorage.clear()
    this.store.dispatch(AppActions.clearCredentials())
    
  }
}
