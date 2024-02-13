import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ticket-container',
  templateUrl: './ticket-container.component.html',
  styleUrls: ['./ticket-container.component.css'],
})
export class TicketContainerComponent implements OnInit {
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
      order_id: 7,
    },
    {
        id: 7,
        message: 'asd12 eq',
        status: 'no-wip',
        order_id: 7,
      },
      {
        id: 4,
        message: 'asd12 jadkjahdkjahskda',
        status: 'closed',
        order_id: 7,
      },
      {
        id: 3,
        message: 'asd12 as',
        status: 'closed',
        order_id: 7,
      },
      {
        id: 1,
        message: 'asd12 jadkjahdkjahskda',
        status: 'wip',
        order_id: 7,
      },
  ];
}
