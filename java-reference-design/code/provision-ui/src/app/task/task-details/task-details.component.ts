import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Task } from "../../_models/task";
import { TaskService } from "../../_services/task.service";

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
  
export class TaskDetailsComponent implements OnInit {

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
      }, error => console.log(error)
    );
    
  }

  list(){
    this.router.navigate(['tasks']);
  }

}
