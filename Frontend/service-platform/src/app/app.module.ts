import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { isDevMode } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingPageComponent } from './components/LandingPage/landing-page/landing-page.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
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







import { StoreModule } from '@ngrx/store';
import { EffectsModule, EffectsRootModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';

import { appReducers } from './store/app.reducer';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppEffects } from './store/app.effects';
import { TokenInterceptorService } from './services/token-interceptor.service';
import { ChatPageComponent } from './components/ChatPage/chat-page/chat-page.component';
import { SingleMessageComponent } from './components/ChatPage/single-message/single-message.component';
import { AccessCodeFormComponent } from './components/access-code-form/access-code-form.component';

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
    OperatorPageComponent,
    UserSidebarComponent,
    SingleMessageComponent,
    ChatPageComponent,
    AccessCodeFormComponent,
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
    NgbTooltipModule,
    HttpClientModule,
    StoreModule.forRoot({ app: appReducers }),
    EffectsModule.forRoot([AppEffects]),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: !isDevMode() }),
  ],
  providers: [  { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true },],
  bootstrap: [AppComponent],
})
export class AppModule {}
