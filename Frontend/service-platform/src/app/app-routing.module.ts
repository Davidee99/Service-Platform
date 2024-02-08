import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './components/LandingPage/landing-page/landing-page.component';
import { UserPageComponent } from './components/UserPage/user-page/user-page.component';

const routes: Routes = [ 
  
  {path: "", component: LandingPageComponent},
  {path: "user/:id", component: UserPageComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
