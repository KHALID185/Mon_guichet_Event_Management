import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { AuthService } from '../../auth/auth.service';
import { NzNotificationService } from 'ng-zorro-antd/notification'; // Importez le service de notification

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {
  private apiUrl = 'http://localhost:8080/api/v1/category/subcategory/';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private notification: NzNotificationService // Injectez le service de notification
  ) {}

  getAllSubcategories(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.authService.getHeaders() }).pipe(
      catchError(error => {
        this.notification.error('Error', 'Error fetching subcategories');
        return this.handleError(error);
      })
    );
  }

  createSubcategory(subcategoryName: string, categoryId: string): Observable<any> {
    return this.http.post(this.apiUrl + 'create', { name: subcategoryName, categoryId: categoryId }, { headers: this.authService.getHeaders() }).pipe(
      catchError(error => {
        this.notification.error('Error', 'Error creating subcategory');
        return this.handleError(error);
      })
    );
  }

  updateSubcategory(id: string, updatedSubcategory: any): Observable<any> {
    return this.http.put(`${this.apiUrl}${id}`, updatedSubcategory, { headers: this.authService.getHeaders() }).pipe(
      catchError(error => {
        this.notification.error('Error', 'Error updating subcategory');
        return this.handleError(error);
      })
    );
  }

  deleteSubcategory(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}${id}`, { headers: this.authService.getHeaders() }).pipe(
      catchError(error => {
        this.notification.error('Error', 'Error deleting subcategory');
        return this.handleError(error);
      })
    );
  }

  getSubcategoryNameById(id: string): Observable<string> {
    return this.http.get<any>(`${this.apiUrl}${id}`, { headers: this.authService.getHeaders() }).pipe(
      map((response: any) => {
        if (!response || !response.name) {
          throw new Error('Invalid response received or missing name property.');
        }
        return response.name;
      }),
      catchError(error => {
        this.notification.error('Error', 'Error fetching subcategory name');
        return this.handleError(error);
      })
    );
  }

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError('Something went wrong; please try again later.');
  }
}
