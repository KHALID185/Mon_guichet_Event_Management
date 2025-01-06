import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../../auth/auth.service';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/users/';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return this.authService.getHeaders();
  }

  getUsers(): Observable<User[]> {
    const headers = this.getHeaders();
    return this.http.get<User[]>(this.apiUrl, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  createUser(userData: User): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(this.apiUrl + 'register', userData, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  updateUser(userId: number, updatedUserData: User): Observable<any> {
    const headers = this.getHeaders();
    return this.http.put(`${this.apiUrl}${userId}`, updatedUserData, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(userId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.apiUrl}${userId}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }
 
  updateUserActivation(userId: number, isActive: boolean): Observable<any> {
    const headers = this.getHeaders();
    const userData = { isActive };
    return this.http.put(`${this.apiUrl}${userId}/activation`, userData, { headers }).pipe(
      catchError(this.handleError)
    );
  }
  
  

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError('Something went wrong; please try again later.');
  }
}
