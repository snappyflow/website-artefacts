import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { Task } from "../../_models/task";
import { TaskService } from "../../_services/task.service";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})

export class TaskListComponent implements OnInit {

  tasks: Observable<Task[]>;
  interval: any;
  allTasks: any;
  pageNo = '0';
  pages: Array<number>;
  deleteId: any;

  constructor(private service: TaskService, private router: Router, private http: HttpClient) {}

  ngOnInit() {

    this.reloadData();
    
    // this.interval = setInterval(() => {
    //   this.reloadData();
    // }, 30000);
  
  }

  reloadData() {
    this.pagination();
  }

  pagination() {
    this.allTasks = this.service.getTasksList(this.pageNo).subscribe(
      data => {
        this.allTasks = data;
        this.pages = new Array(data['totalPages']);
      },
      error => console.log(error)
    );
    console.log(this.allTasks);
  }
  
  setPage(i, event: any) {
    event.preventDefault();
    this.pageNo = i;
    this.pagination();
  }

  taskDetails(id: number){
    this.router.navigate(['task/details', id]);
  }

  updateTask(id: number){
    this.router.navigate(['task/update', id]);
  }
  
  delete(id: number) {
    this.deleteId = id;
  }
  
  deleteTask(id: number) {
    this.service.deleteTask(id).subscribe(
      data => {
        console.log(data);
        this.reloadData();
      },
      error => console.log(error)
    );
  }

  deleteAll() {
    this.service.deleteAll().subscribe(
      data => {
        console.log(data);
        this.reloadData();
      }
    );
    this.reloadData();
  }

  addTask() {
    this.router.navigate(['task/add']);
  }

}
