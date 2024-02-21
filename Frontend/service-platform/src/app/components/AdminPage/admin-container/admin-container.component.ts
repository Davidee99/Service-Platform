import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { loadTickets } from 'src/app/store/app.actions';
import * as AppActions from 'src/app/store/app.actions';
import { selectorAllTickets } from 'src/app/store/app.selector';
import { Ticket } from 'src/model/ticket.model';


@Component({
  selector: 'app-admin-container',
  templateUrl: './admin-container.component.html',
  styleUrls: ['./admin-container.component.css']
})
export class AdminContainerComponent {


  //Bottoni
  open: boolean = false;
  closed: boolean = false;

  OpenButton() {
    this.open = true;
    this.closed = false;
    console.log('OpenButton' + this.open);
  }
  ClosedButton() {
    this.closed = true;
    this.open = false;
    console.log('ClosedButton' + this.closed);
  }

  myObservable:Observable<Ticket[]> |null=null

  constructor(private store: Store){

    this.store.dispatch(AppActions.loadTickets())

    //chiamo il selector dello store
    this.myObservable=this.store.select(selectorAllTickets)
    

  }

  


  // tickets: any[] = [
  //   {
  //     id: 2,
  //     message: 'akjshdkjahdkjaskdh jadkjahdkjahskda',
  //     status: 'wip',
  //     type:'Refund',
  //     order_id: 7,
  //   },
  //   {
  //       id: 7,
  //       message: 'asd12 eq',
  //       status: 'no-wip',
  //       type:'Refund',
  //       order_id: 7,
  //     },
  //     {
  //       id: 4,
  //       message: 'asd12 jadkjahdkjahskda',
  //       status: 'closed',
  //       type:'Refund',
  //       order_id: 7,
  //     },
  //     {
  //       id: 3,
  //       message: 'asd12 as',
  //       status: 'closed',
  //       type:'Product',
  //       order_id: 7,
  //     },
  //     {
  //       id: 1,
  //       message: 'asd12 jadkjahdkjahskda',
  //       status: 'wip',
  //       type:'Product',
  //       order_id: 7,
  //     },
  // ];

}
