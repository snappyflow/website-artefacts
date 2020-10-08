import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from "rxjs";

import { Task } from "../../_models/task";
import { TaskService } from "../../_services/task.service";

@Component({
  selector: 'app-create-task',
  templateUrl: './create-task.component.html',
  styleUrls: ['./create-task.component.css']
})

export class CreateTaskComponent implements OnInit {

  task: Task = new Task();
  submitted = false;

  // os = ["Select OS", "Windows 7", "Windows 10", "Ubuntu 19", "Ubuntu 20"]
  // cloud = ["Select Cloud Type", "AWS", "Azure", "Google Cloud", "Openstack"]

  constructor(private service: TaskService, private router: Router) { }

  ngOnInit() {
    // this.task.name = "Select OS"
    // this.task.cloud_type = "Select Cloud Type"
  }

  newTask(): void {
    this.submitted = false;
    this.task = new Task();
  }

  save() {
    this.service.create_task(this.task).subscribe(
      data => console.log(data),
      error => console.log(error)
    );
    this.task = new Task();
    this.gotoList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
    this.reloadData();
  }

  gotoList() {
    this.router.navigate(['tasks']);
  }

  // Update list
  tasks: Observable<Task[]>;
  reloadData() {
    this.tasks = this.service.getTasksList("0");
    // this.tasks = this.service.get_all_tasks();
    console.log(this.tasks);
  }

}
