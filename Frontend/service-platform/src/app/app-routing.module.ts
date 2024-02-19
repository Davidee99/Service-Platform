import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './components/LandingPage/landing-page/landing-page.component';
import { UserPageComponent } from './components/UserPage/user-page/user-page.component';
import { OperatorPageComponent } from './components/OperatorPage/operator-page/operator-page.component';
import { roleAuthGuard } from './guards/role-auth.guard';

const routes: Routes = [ 
  
  {path: "", component: LandingPageComponent},
  {path: "user/:id", component: UserPageComponent, canActivate:[roleAuthGuard]},
  { path : "operator/:id" , component: OperatorPageComponent, canActivate:[roleAuthGuard]}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
