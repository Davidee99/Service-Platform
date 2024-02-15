import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-container',
  templateUrl: './admin-container.component.html',
  styleUrls: ['./admin-container.component.css']
})
export class AdminContainerComponent {

  ngOnInit(): void {
    console.log(this.tickets);
  }

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

  tickets: any[] = [
    {
      id: 2,
      message: 'akjshdkjahdkjaskdh jadkjahdkjahskda',
      status: 'wip',
      type:'Refund',
      order_id: 7,
    },
    {
        id: 7,
        message: 'asd12 eq',
        status: 'no-wip',
        type:'Refund',
        order_id: 7,
      },
      {
        id: 4,
        message: 'asd12 jadkjahdkjahskda',
        status: 'closed',
        type:'Refund',
        order_id: 7,
      },
      {
        id: 3,
        message: 'asd12 as',
        status: 'closed',
        type:'Product',
        order_id: 7,
      },
      {
        id: 1,
        message: 'asd12 jadkjahdkjahskda',
        status: 'wip',
        type:'Product',
        order_id: 7,
      },
  ];

}
