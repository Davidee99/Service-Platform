import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ticket-container',
  templateUrl: './ticket-container.component.html',
  styleUrls: ['./ticket-container.component.css'],
  
})
export class TicketContainerComponent implements OnInit {

  ngOnInit(): void {

      console.log(this.tickets[0]);

    
  }

  tickets : any[] = [
    {
        "ID": 1,
        "MESSAGE": "Primo messaggio",
        "USER_ID": 123,
        "OPERATOR_ID": null,
        "STATUS": "NON_WIP",
        "STATUS_ERROR": null,
        "PRIORITY": 1,
        "ACCESS_CODE": "ABC123",
        "CREATE_DATE": "2024-02-08T00:00:00",
        "CLOSING_DATE": null,
        "TYPE": "REFOUND",
        "ORDER_ID": 456
    },
    {
        "ID": 2,
        "MESSAGE": "Secondo messaggio",
        "USER_ID": 456,
        "OPERATOR_ID": null,
        "STATUS": "NON_WIP",
        "STATUS_ERROR": null,
        "PRIORITY": 5,
        "ACCESS_CODE": "DEF456",
        "CREATE_DATE": "2024-02-08T00:00:00",
        "CLOSING_DATE": null,
        "TYPE": "PRODUCT",
        "ORDER_ID": 789
    },
    {
        "ID": 3,
        "MESSAGE": "Terzo messaggio",
        "USER_ID": 789,
        "OPERATOR_ID": null,
        "STATUS": "WIP",
        "STATUS_ERROR": null,
        "PRIORITY": 3,
        "ACCESS_CODE": "GHI789",
        "CREATE_DATE": "2024-02-08T00:00:00",
        "CLOSING_DATE": null,
        "TYPE": "REFOUND",
        "ORDER_ID": 101112
    },
    {
        "ID": 4,
        "MESSAGE": "Quarto messaggio",
        "USER_ID": 101112,
        "OPERATOR_ID": null,
        "STATUS": "WIP",
        "STATUS_ERROR": null,
        "PRIORITY": 7,
        "ACCESS_CODE": "JKL101",
        "CREATE_DATE": "2024-02-08T00:00:00",
        "CLOSING_DATE": null,
        "TYPE": "PRODUCT",
        "ORDER_ID": 131415
    },
    {
        "ID": 5,
        "MESSAGE": "Quinto messaggio",
        "USER_ID": 131415,
        "OPERATOR_ID": null,
        "STATUS": "CLOSED",
        "STATUS_ERROR": null,
        "PRIORITY": 2,
        "ACCESS_CODE": "MNO131",
        "CREATE_DATE": "2024-02-08T00:00:00",
        "CLOSING_DATE": "2024-02-08T12:00:00",
        "TYPE": "REFOUND",
        "ORDER_ID": 161718
    },
    {
        "ID": 6,
        "MESSAGE": "Sesto messaggio",
        "USER_ID": 161718,
        "OPERATOR_ID": null,
        "STATUS": "CLOSED",
        "STATUS_ERROR": null,
        "PRIORITY": 4,
        "ACCESS_CODE": "PQR161",
        "CREATE_DATE": "2024-02-08T00:00:00",
        "CLOSING_DATE": "2024-02-08T12:00:00",
        "TYPE": "PRODUCT",
        "ORDER_ID": 192021
    }
]


}
