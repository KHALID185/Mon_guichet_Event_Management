import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../../auth/auth.service';

@Injectable({
    providedIn: 'root'
})
export class EventService {
    private apiUrl = 'http://localhost:8080/api/v1/category/subcategory/event/';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        const currentUserString = localStorage.getItem('currentUser');
        if (!currentUserString) {
            throw new Error('No user information available.');
        }
        const currentUser = JSON.parse(currentUserString);
        const accessToken = currentUser.accessToken;
        if (!accessToken) {
            throw new Error('No access token available.');
        }
        return new HttpHeaders({
            'Content-type': 'application/json',
            'Authorization': `Bearer ${accessToken}`
        });
    }

    getAllEvents(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() }).pipe(
            catchError(this.handleError)
        );
    }

    deleteEvent(id: string): Observable<any> {
        return this.http.delete(`${this.apiUrl}${id}`, { headers: this.getHeaders() }).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: any) {
        console.error('An error occurred:', error);
        return throwError('Something went wrong; please try again later.');
    }
}
