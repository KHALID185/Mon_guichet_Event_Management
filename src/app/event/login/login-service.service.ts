import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  constructor(private http : HttpClient) { }
  private apiUrl = 'http://localhost:8090/api/v1/category/';
  login(email:string,password:string){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http
      .post<any>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap((res: any) => {
          if (res && res.token) {
            localStorage.setItem('token', res.token);
          }
        }),
        catchError(this.handleError)
      );
  }

  
  handleError(handleError: any): import("rxjs").OperatorFunction<any, any> {
    throw new Error('Method not implemented.');
  }
}
