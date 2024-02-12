import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './components/LandingPage/landing-page/landing-page.component';
import { UserPageComponent } from './components/UserPage/user-page/user-page.component';
import { OperatorPageComponent } from './components/OperatorPage/operator-page/operator-page.component';
import { AdminPageComponent } from './components/AdminPage/admin-page/admin-page.component';

const routes: Routes = [ 
  
  {path: "", component: LandingPageComponent},
  {path: "user/:id", component: UserPageComponent},
   { path : "operator" , component: OperatorPageComponent},
   { path : "admin" , component: AdminPageComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
