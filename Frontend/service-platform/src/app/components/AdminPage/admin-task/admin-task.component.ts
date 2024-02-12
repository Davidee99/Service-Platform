import { Component, Input } from '@angular/core';
//chart.js
import Chart from 'chart.js/auto';


@Component({
  selector: 'app-admin-task',
  templateUrl: './admin-task.component.html',
  styleUrls: ['./admin-task.component.css'],
  
})
export class AdminTaskComponent {

  

  @Input() 
  ticket:any;

  tasks: string[] = ['Item 1','Item 1','Item 1','Item 1', 'Item 2','Item 2', 'Item 3', 'Item 4', 'Item 5'];
  filteredTasks: string[] = this.tasks.slice(); // Copia tutti gli elementi in filteredItems all'inizio

  filterTasks(filterValue: string) {
    if (filterValue === 'Item 1') {
      this.filteredTasks = this.tasks.filter(item => item === 'Item 1');
    } else if (filterValue === 'Item 2') {
      this.filteredTasks = this.tasks.filter(item => item === 'Item 2');
    } else if (filterValue === 'Item 3') {
      this.filteredTasks = this.tasks.filter(item => item === 'Item 3');
    } else if (filterValue === 'Item 4') {
      this.filteredTasks = this.tasks.filter(item => item === 'Item 4');
    } else {
      this.filteredTasks = this.tasks;
    }
  }


//graph
  title = 'ng-chart';
  chart: any = [];


  ngOnInit() {
    this.chart = new Chart('canvas', {
      type: 'bar',
      data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [
          {
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
            borderWidth: 1,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }

}
