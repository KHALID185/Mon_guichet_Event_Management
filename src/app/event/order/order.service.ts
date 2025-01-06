import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../../auth/auth.service';
import { Order } from './order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = 'http://localhost:8080/api/order2/';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return this.authService.getHeaders();
  }

  getOrders(): Observable<Order[]> {
    const headers = this.getHeaders();
    return this.http.get<Order[]>(this.apiUrl, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  createOrder(orderData: Order): Observable<any> {
    const headers = this.getHeaders();
    return this.http.post(this.apiUrl + 'create', orderData, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  updateOrder(orderId: number, updatedOrderData: Order): Observable<any> {
    const headers = this.getHeaders();
    return this.http.put(`${this.apiUrl}${orderId}`, updatedOrderData, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  deleteOrder(orderId: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.apiUrl}${orderId}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  getOrderById(orderId: number): Observable<Order> {
    const headers = this.getHeaders();
    return this.http.get<Order>(`${this.apiUrl}${orderId}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }
  

  private handleError(error: any) {
    console.error('An error occurred:', error);
    return throwError('Something went wrong; please try again later.');
  }
}
