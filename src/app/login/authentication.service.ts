import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private apiUrl = 'http://localhost:8080/auth/login';

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<any> {
    const credentials = { email: email, password: password };
    return this.http.post(this.apiUrl, credentials).pipe(
      tap((res: any) => {
        if (res && res.email) {
          localStorage.setItem('currentUser', JSON.stringify(res));
        }
      })
    );
  }

  logout() {
    localStorage.removeItem('currentUser');
  }
}
