import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth/login';

  constructor(private http: HttpClient) { }

  public  getHeaders(): HttpHeaders {
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

  login(email: string, password: string) {
    const credentials = { email: email, password: password };
    return this.http.post(this.apiUrl, credentials);
  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('currentUser');
  }
}
