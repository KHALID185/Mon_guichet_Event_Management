import { Component, OnInit } from '@angular/core';
import { CategoryService } from './category.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  newCategoryName: string = '';
  editCache: { [key: string]: { edit: boolean; data: any } } = {};
  listOfData: any[] = [];
  pageIndex = 1;
  pageSize = 3;
  totalItems = 0;

  constructor(
    private categoryService: CategoryService,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {
    this.updateTableData();
  }

  updateTableData(): void {
    this.categoryService.getAllCategories().subscribe(
      (data: any[]) => {
        data.sort((a, b) => b.id - a.id);
        this.totalItems = data.length;
        const startIndex = (this.pageIndex - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        this.listOfData = data.slice(startIndex, endIndex);
        this.updateEditCache();
      },
      (error: any) => {
        this.notification.error('Error', 'Error fetching categories');
        console.error('Error fetching categories:', error);
      }
    );
  }

  addCategory(): void {
    if (this.newCategoryName.trim() === '') {
      return;
    }
    this.categoryService.createCategory(this.newCategoryName).subscribe(
      () => {
        this.newCategoryName = '';
        this.pageIndex = 1;
        this.updateTableData();
        this.notification.success('Success', 'Category added successfully');
      },
      (error: any) => {
        this.notification.error('Error', 'Error adding category');
        console.error('Error adding category:', error);
      }
    );
  }

  startEdit(id: string): void {
    this.editCache[id].edit = true;
  }

  cancelEdit(id: string): void {
    const index = this.listOfData.findIndex(item => item.id === id);
    this.editCache[id] = {
      data: { ...this.listOfData[index] },
      edit: false
    };
  }

  saveEdit(id: string): void {
    const index = this.listOfData.findIndex(item => item.id === id);
    const updatedCategory = this.editCache[id].data;
    this.categoryService.updateCategory(id, updatedCategory).subscribe(
      () => {
        this.editCache[id].edit = false;
        this.updateTableData();
        this.notification.success('Success', 'Category updated successfully');
      },
      (error: any) => {
        this.notification.error('Error', 'Error updating category');
        console.error('Error updating category:', error);
      }
    );
  }

  deleteRow(id: string): void {
    this.categoryService.deleteCategory(id).subscribe(
      () => {
        this.listOfData = this.listOfData.filter(item => item.id !== id);
        this.updateEditCache();
        this.notification.success('Success', 'Category deleted successfully');
      },
      (error: any) => {
        this.notification.error('Error', 'Error deleting category');
        console.error('Error deleting category:', error);
      }
    );
  }

  onPageIndexChange(index: number): void {
    this.pageIndex = index;
    this.updateTableData();
  }

  updateEditCache(): void {
    this.listOfData.forEach(item => {
      this.editCache[item.id] = {
        edit: false,
        data: { ...item }
      };
    });
  }
}
