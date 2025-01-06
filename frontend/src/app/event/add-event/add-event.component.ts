import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { finalize } from 'rxjs/operators';
import { NzUploadFile, NzUploadChangeParam } from 'ng-zorro-antd/upload';
import { AddeventService } from './addevent.service';
import { AuthService } from '../../auth/auth.service';
import { SubcategoryService } from '../sub-category/sub-category.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {
  fileList1: NzUploadFile[] = [];
  eventName: string | null = null;
  eventDescription: string | null = null;
  eventDate: Date | null = null;
  ticketPrice: number | null = null;
  ticketStock: number | null = null;
  selectedSubcategory: any = null;
  subcategories: any[] = [];
  eventId: string | null = null;
  imageUrl: string | null = null;

  constructor(
    private fireStorage: AngularFireStorage,
    private addEventService: AddeventService,
    private authService: AuthService,
    private subcategoryService: SubcategoryService,
    private notification: NzNotificationService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.eventId = params.get('id');
      if (this.eventId) {
        this.loadEventData();
      }
    });

    this.loadSubcategories();
  }

  uploadedFiles: Set<string> = new Set<string>();

  onFileSelected(event: NzUploadChangeParam): void {
    const file = event?.file?.originFileObj as File;
    if (file) {
      const fileName = file.name;
      if (!this.uploadedFiles.has(fileName)) {
        this.uploadedFiles.add(fileName);
        const uniqueFileName = this.generateUniqueFileName(fileName);
        const filePath = `events/${uniqueFileName}`;
        const fileRef = this.fireStorage.ref(filePath);
        const task = this.fireStorage.upload(filePath, file);

        task.snapshotChanges().pipe(
          finalize(() => {
            fileRef.getDownloadURL().subscribe(url => {
              console.log('Uploaded Image URL:', url);
              this.imageUrl = url;
              this.notification.success('Success', 'Image uploaded successfully');
            });
          })
        ).subscribe();
      } else {
        console.log('File already uploaded:', fileName);
      }
    }
  }

  generateUniqueFileName(originalFileName: string): string {
    const date = new Date();
    const timestamp = date.getTime();
    const randomString = Math.random().toString(36).substring(2, 8);
    const extension = originalFileName.split('.').pop();
    return `${timestamp}_${randomString}.${extension}`;
  }

  loadEventData(): void {
    this.addEventService.getEvent(this.eventId!).subscribe(
      eventData => {
        this.eventName = eventData.name;
        this.eventDescription = eventData.description;
        this.eventDate = new Date(eventData.dueDate);
        this.ticketPrice = eventData.price_Ticket;
        this.ticketStock = eventData.stock_Ticket;
        this.selectedSubcategory = eventData.subCategoryId;
        this.imageUrl = eventData.url;
      },
      error => {
        console.error('Error fetching event data:', error);
      }
    );
  }

  loadSubcategories(): void {
    this.subcategoryService.getAllSubcategories().subscribe(
      subcategories => {
        this.subcategories = subcategories;
      },
      error => {
        console.error('Error fetching subcategories:', error);
      }
    );
  }

  addEvent(): void {
    if (!this.eventName || !this.eventDate || !this.selectedSubcategory || !this.ticketPrice || !this.ticketStock || !this.eventDescription || !this.imageUrl) {
      this.notification.error('Error', 'Please fill in all required fields');
      return;
    }

    const eventData = {
      name: this.eventName,
      description: this.eventDescription,
      dueDate: this.eventDate,
      price_Ticket: this.ticketPrice,
      stock_Ticket: this.ticketStock,
      subCategoryId: this.selectedSubcategory,
      url: this.imageUrl
    };

    if (this.eventId) {
      this.updateEvent(eventData);
    } else {
      this.createEvent(eventData);
    }
  }

  createEvent(eventData: any): void {
    this.addEventService.createEvent(eventData).subscribe(
      response => {
        this.notification.success('Success', 'Event created successfully');
        this.router.navigateByUrl(`/add-event/${response.id}`);
      },
      error => {
        this.notification.error('Error', 'Failed to create event');
        console.error('Error creating event:', error);
      }
    );
  }

  updateEvent(eventData: any): void {
    this.addEventService.updateEvent(this.eventId!, eventData).subscribe(
      response => {
        this.notification.success('Success', 'Event updated successfully');
        console.log('Event updated successfully:', response);
      },
      error => {
        this.notification.error('Error', 'Failed to update event');
        console.error('Error updating event:', error);
      }
    );
  }
}
