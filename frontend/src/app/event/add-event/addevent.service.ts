import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AddeventService {
  private apiUrl = 'http://localhost:8080/api/v1/category/subcategory/event/';

  constructor(private http: HttpClient, private authService: AuthService) { }

  private getHeaders(): HttpHeaders {
    return this.authService.getHeaders();
  }

  getEvent(eventId: string): Observable<any> { // Ajout de la méthode getEvent
    const headers = this.getHeaders();
    return this.http.get(`${this.apiUrl}${eventId}`, { headers });
  }

  createEvent(eventData: any): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(this.apiUrl + 'create', eventData, { headers });
  }

  updateEvent(eventId: string, eventData: any): Observable<any> {
    const headers = this.getHeaders();
    return this.http.put(`${this.apiUrl}${eventId}`, eventData, { headers });
  }

  
}
