import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Task } from "../../_models/task";
import { TaskService } from "../../_services/task.service";

@Component({
  selector: 'app-update-task',
  templateUrl: './update-task.component.html',
  styleUrls: ['./update-task.component.css']
})
  
export class UpdateTaskComponent implements OnInit {

  id: number;
  task: Task;

  constructor(private service: TaskService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.task = new Task();
    this.id = this.route.snapshot.params['id'];
    this.service.getTask(this.id).subscribe(
      data => {
        console.log(data)
        this.task = data;
      },
      error => console.log(error)
    );
  }

  updateTask() {
    this.service.updateTask(this.id, this.task).subscribe(
      data => console.log(data),
      error => console.log(error)
    );
    this.task = new Task();
    this.gotoList();
  }

  onSubmit() {
    this.updateTask();    
  }

  gotoList() {
    this.router.navigate(['/tasks']);
  }

}
