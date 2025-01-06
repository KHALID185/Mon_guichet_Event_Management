import { Component, OnInit } from '@angular/core';
import { EventService } from './event.service';
import { Router } from '@angular/router';
import { SubcategoryService } from '../sub-category/sub-category.service';
import { formatDate } from '@angular/common';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {
  listOfData: any[] = [];
  allData: any[] = [];
  pageIndex = 1;
  pageSize = 3;
  totalItems = 0;

  constructor(
    private eventService: EventService,
    private router: Router,
    private subcategoryService: SubcategoryService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.fetchAllEvents();
  }

  fetchAllEvents(): void {
    this.eventService.getAllEvents().subscribe(
      (data: any[]) => {
        data.sort((a, b) => b.id - a.id);
        this.allData = data;
        this.totalItems = data.length;
        this.updateTableData();
      },
      (error: any) => {
        console.error('Error fetching events:', error);
      }
    );
  }

  updateTableData(): void {
    const startIndex = (this.pageIndex - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.listOfData = this.allData.slice(startIndex, endIndex);
    this.listOfData.forEach(event => {
      this.subcategoryService.getSubcategoryNameById(event.subCategoryId).subscribe(
        (name: string) => {
          event.subCategoryName = name;
        },
        (error: any) => {
          console.error('Error fetching subcategory name:', error);
        }
      );
    });
  }

  deleteEvent(id: string): void {
    this.eventService.deleteEvent(id).subscribe(
      () => {
        this.allData = this.allData.filter(item => item.id !== id);
        this.totalItems = this.allData.length;
        this.updateTableData();
      },
      (error: any) => {
        console.error('Error deleting event:', error);
      }
    );
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    const options = { day: 'numeric', month: 'long', year: 'numeric' };
    return formatDate(date, 'dd MMMM yyyy', 'en-US');
  }

  goToAddEvent(): void {
    this.router.navigateByUrl('/add-event');
  }

  goToEditEvent(eventId: string): void {
    this.router.navigateByUrl(`/add-event/${eventId}`);
  }

  onPageIndexChange(index: number): void {
    this.pageIndex = index;
    this.updateTableData();
  }
}
