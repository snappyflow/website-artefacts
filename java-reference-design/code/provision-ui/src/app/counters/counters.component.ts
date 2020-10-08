import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { Counter } from "../_models/counter";
import { CounterService } from "../_services/counter.service";

@Component({
  selector: 'app-counters',
  templateUrl: './counters.component.html',
  styleUrls: ['./counters.component.css']
})
  
export class CountersComponent implements OnInit {

  constructor(private service: CounterService) { }

  counters: Observable<Counter[]>;
  interval: any;
  counter: Counter;
  resetId: any;
  summary: any;

  ngOnInit() {

    this.reloadData();
    
    // this.interval = setInterval(() => {
    //   this.reloadData();
    // }, 3000);

  }

  reloadData() {
    this.counters = this.service.getCounters();
    console.log(this.counters);
    this.service.getAllCounters().subscribe(
      data => {
        this.summary = data;
        console.log(this.summary);
      }
    );
  }

  reset(id: number) {
    this.resetId = id;
  }

  resetCounter(id: number) {
    console.log("Reset the Counter for ID : " + id);
    this.service.resetCounter(id).subscribe(
      data => {
        console.log(data);
        this.reloadData();
      },
      error => console.log(error)
    );
  }

  resetAll() {
    this.service.resetCounters().subscribe(
      data => {
        console.log(data);
        this.reloadData();
      },
      error => console.log(error)
    );
  }

}
