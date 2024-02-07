import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent{
 

  userData = {
    
    firstname : "",
    lastname : "",
    email: "",
    type: "",
    message : ""

  };
  

  



}
