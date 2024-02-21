import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {Component} from '@angular/core';
import {FormControl, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './components/LandingPage/landing-page/landing-page.component';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './components/navbar/navbar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TopRightButtonComponent } from './components/top-right-button/top-right-button.component';
import { UserPageComponent } from './components/UserPage/user-page/user-page.component';
import { TicketContainerComponent } from './components/UserPage/ticket-container/ticket-container.component';
import { TicketComponent } from './components/UserPage/ticket/ticket.component';
import { UserSidebarComponent } from './components/UserPage/user-sidebar/user-sidebar.component';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { OperatorTicketComponent } from './components/OperatorPage/operator-ticket/operator-ticket.component';
import { OperatorTicketContainerComponent } from './components/OperatorPage/operator-ticket-container/operator-ticket-container.component';
import { OperatorSidebarComponent } from './components/OperatorPage/operator-sidebar/operator-sidebar.component';
import { OperatorPageComponent } from './components/OperatorPage/operator-page/operator-page.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminPageComponent } from './components/AdminPage/admin-page/admin-page.component';
import { AdminTaskComponent } from './components/AdminPage/admin-task/admin-task.component';
import {MatChipsModule} from '@angular/material/chips';
import {MatListModule} from '@angular/material/list';
import { AdminContainerComponent } from './components/AdminPage/admin-container/admin-container.component';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { appReducers } from './store/app.reducer';
import { EffectsModule } from '@ngrx/effects';









@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    NavbarComponent,
    TopRightButtonComponent,
    UserPageComponent,
    UserPageComponent,
    TicketContainerComponent,
    TicketComponent,
    UserSidebarComponent,
    OperatorTicketComponent,
    OperatorTicketContainerComponent,
    OperatorSidebarComponent,
    OperatorPageComponent,
    AdminPageComponent,
    AdminTaskComponent,
    AdminContainerComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatFormFieldModule, 
    MatInputModule, 
    MatSelectModule,
    FormsModule, 
    ReactiveFormsModule, 
    NgIf,
    MatButtonModule,
    MatToolbarModule,
     MatIconModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    NgbModule,
    NgbTooltipModule,
    MatExpansionModule,
    NgbCollapseModule,
    MatChipsModule,
    MatListModule,
    

    NgbTooltipModule,
    HttpClientModule,
    StoreModule.forRoot({app:appReducers}),
    EffectsModule.forRoot([AppEffects]),
    

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
