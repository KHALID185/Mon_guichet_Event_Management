import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from './order.service';
import { Order } from './order.model';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  listOfData: Order[] = [];

  // Properties for pagination
  pageIndex: number = 1;
  pageSize: number = 10;
  totalItems: number = 0;

  constructor(private router: Router, private orderService: OrderService) { }

  ngOnInit(): void {
    this.fetchOrders();
  }

  fetchOrders(): void {
    this.orderService.getOrders().subscribe(
      (orders: Order[]) => {
        this.listOfData = orders;
        this.totalItems = orders.length;
      },
      (error: any) => {
        console.error('Error fetching orders:', error);
      }
    );
  }

  editOrder(orderId: number): void {
    // Logic to edit order
  }

  deleteOrder(orderId: number): void {
    // Logic to delete order
  }

  onPageIndexChange(event: number): void {
    // Logic to handle page change
  }

  openEditDrawer(orderId: number): void {
    // Logic to open edit drawer
  }
}
