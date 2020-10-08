import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class TaskService {

  constructor(private http: HttpClient) { }

  private url = 'http://localhost:8080/api/provision';

  getTask(id: number): Observable<any> {
    return this.http.get(`${this.url}/${id}`);
  }

  getTasksList(PageNo: string): Observable<any> {
    let params = new HttpParams().set('page', PageNo);
    return this.http.get(`${this.url}/all`, {params});
  }

  // get_all_tasks(): Observable<any> {
  //   return this.http.get(`${this.url}`);
  // }

  create_task(task: Object): Observable<Object> {
    return this.http.post(`${this.url}`, task);
  }

  updateTask(id: number, value: any): Observable<Object> {
    return this.http.put(`${this.url}/${id}`, value);
  }

  deleteTask(id: number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`, { responseType: 'text' });
  }

  deleteAll(): Observable<any> {
    return this.http.delete(`${this.url}/delete-all`);
  }

}
