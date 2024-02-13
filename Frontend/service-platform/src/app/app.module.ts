import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import {Component} from '@angular/core';
import {FormControl, Validators,  ReactiveFormsModule, FormsModule} from '@angular/forms';
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
import { ChatComponent } from './components/chat/chat.component';



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
    ChatComponent
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
    NgbTooltipModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
