import { Injectable } from '@angular/core';
import { HttpClient, HttpParams  } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class CounterService {

  constructor(private http: HttpClient) { }

  private baseUrlCounter = 'http://localhost:8080/api/worker';

  getAllCounters(): Observable<any> {
    return this.http.get(`${this.baseUrlCounter}/get-all-counters`);
  }
  
  getCounters(): Observable<any> {
    return this.http.get(`${this.baseUrlCounter}/get-counters`);
  }

  resetCounters(): Observable<any> {
    return this.http.get(`${this.baseUrlCounter}/reset-counters`, { responseType: 'text' });
  }

  resetCounter(id: number): Observable<any> {
    return this.http.get(`${this.baseUrlCounter}/reset-counter/${id}`, { responseType: 'text' });
  }

}
