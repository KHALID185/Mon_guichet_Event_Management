import { Component, OnInit } from '@angular/core';
import { SubcategoryService } from './sub-category.service';
import { CategoryService } from '../category/category.service';
import { AuthService } from '../../auth/auth.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';  // Importez le service de notification

@Component({
  selector: 'app-sub-category',
  templateUrl: './sub-category.component.html',
  styleUrls: ['./sub-category.component.css']
})
export class SubCategoryComponent implements OnInit {
  newSubcategoryName: string = '';
  selectedCategoryId: string = '';
  editCache: { [key: string]: { edit: boolean; data: any } } = {};
  listOfData: any[] = [];
  categoryList: any[] = [];
  pageIndex = 1;
  pageSize = 3;
  totalItems = 0;

  constructor(
    private subcategoryService: SubcategoryService,
    private categoryService: CategoryService,
    private authService: AuthService,
    private notification: NzNotificationService  // Injectez le service de notification
  ) {}

  ngOnInit(): void {
    this.updateTableData();
    this.loadCategoryList();
  }

  updateTableData(): void {
    this.subcategoryService.getAllSubcategories().subscribe(
      (data: any[]) => {
        data.sort((a, b) => b.id - a.id);
        this.totalItems = data.length;
        const startIndex = (this.pageIndex - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        this.listOfData = data.slice(startIndex, endIndex);
        this.updateEditCache();
      },
      (error: any) => {
        this.notification.error('Error', 'Error fetching subcategories');
        console.error('Error fetching subcategories:', error);
      }
    );
  }

  loadCategoryList(): void {
    this.categoryService.getAllCategories().subscribe(
      (data: any[]) => {
        this.categoryList = data;
      },
      (error: any) => {
        this.notification.error('Error', 'Error fetching categories');
        console.error('Error fetching categories:', error);
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
    const updatedSubcategory = this.editCache[id].data;
    this.subcategoryService.updateSubcategory(id, updatedSubcategory).subscribe(
      () => {
        this.notification.success('Success', 'Subcategory updated successfully');
        this.editCache[id].edit = false;
        this.updateTableData();
      },
      (error: any) => {
        this.notification.error('Error', 'Error updating subcategory');
        console.error('Error updating subcategory:', error);
      }
    );
  }

  addRow(): void {
    if (!this.newSubcategoryName || !this.selectedCategoryId) {
      this.notification.warning('Warning', 'Subcategory name and category must be provided');
      console.error('Subcategory name and category must be provided.');
      return;
    }
    this.subcategoryService.createSubcategory(this.newSubcategoryName, this.selectedCategoryId).subscribe(
      () => {
        this.notification.success('Success', 'Subcategory added successfully');
        if ((this.totalItems % this.pageSize) === 0) {
          this.pageIndex++;
        }
        this.updateTableData();
        this.newSubcategoryName = '';
        this.selectedCategoryId = '';
      },
      (error: any) => {
        this.notification.error('Error', 'Error adding subcategory');
        console.error('Error adding subcategory:', error);
      }
    );
  }

  deleteRow(id: string): void {
    this.subcategoryService.deleteSubcategory(id).subscribe(
      () => {
        this.notification.success('Success', 'Subcategory deleted successfully');
        this.listOfData = this.listOfData.filter(item => item.id !== id);
        this.updateEditCache();
      },
      (error: any) => {
        this.notification.error('Error', 'Error deleting subcategory');
        console.error('Error deleting subcategory:', error);
      }
    );
  }

  onPageIndexChange(index: number): void {
    this.pageIndex = index;
    this.updateTableData();
  }

  getCategoryName(categoryId: string): string {
    const category = this.categoryList.find(category => category.id === categoryId);
    return category ? category.name : 'N/A';
  }

  private updateEditCache(): void {
    this.listOfData.forEach(item => {
      this.editCache[item.id] = {
        edit: false,
        data: { ...item }
      };
    });
  }
}
