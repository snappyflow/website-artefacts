import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { TaskListComponent } from './task/task-list/task-list.component';
import { CreateTaskComponent } from './task/create-task/create-task.component';
import { TaskDetailsComponent } from './task/task-details/task-details.component';
import { UpdateTaskComponent } from './task/update-task/update-task.component';
import { CountersComponent } from './counters/counters.component';

const routes: Routes = [

  { path: '', component: HomeComponent },
  
  { path: 'tasks', component: TaskListComponent },
  { path: 'task/add', component: CreateTaskComponent },
  { path: 'task/details/:id', component: TaskDetailsComponent },
  { path: 'task/update/:id', component: UpdateTaskComponent },
  
  { path: 'counters', component: CountersComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
  
export class AppRoutingModule { }
