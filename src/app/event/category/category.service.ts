import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8080/api/v1/category/';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return this.authService.getHeaders();
  }

  getAllCategories(): Observable<any[]> {
    const headers = this.getHeaders();
    return this.http.get<any[]>(this.apiUrl, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  createCategory(categoryName: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(this.apiUrl + 'create', { name: categoryName }, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  updateCategory(id: string, updatedCategory: any): Observable<any> {
    const headers = this.getHeaders();
    return this.http.put(`${this.apiUrl}${id}`, updatedCategory, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  deleteCategory(id: string): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.apiUrl}${id}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError('Something went wrong; please try again later.');
  }
}
