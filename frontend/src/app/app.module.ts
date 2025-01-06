import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EventComponent } from './event/event/event.component'; // Utilisez le chemin correct vers EventComponent
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';

import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { CategoryComponent } from './event/category/category.component';
import { SubCategoryComponent } from './event/sub-category/sub-category.component';
import { AddEventComponent } from './event/add-event/add-event.component';
import { LoginComponent } from './login/login.component';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { ReactiveFormsModule } from '@angular/forms';
import { NzInputNumberModule } from 'ng-zorro-antd/input-number';
import { NzNotificationModule } from 'ng-zorro-antd/notification';
import { UserComponent } from './event/user/user.component';
import { OrderComponent } from './event/order/order.component';
import { TestComponent } from './event/test/test.component';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireStorageModule } from '@angular/fire/compat/storage';
import { environment } from './event/firebase/environment';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { NzSwitchModule } from 'ng-zorro-antd/switch';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { HttpClientJsonpModule } from '@angular/common/http';


registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    EventComponent , 
    CategoryComponent,
    SubCategoryComponent,
    AddEventComponent,
    LoginComponent,
    UserComponent,
    OrderComponent,
    TestComponent
  ],
  imports: [
 
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    CommonModule ,
    NzTableModule,
    NzPopconfirmModule,
    NzPaginationModule,
    NzSelectModule,
    NzFormModule,
    NzInputModule,
    ReactiveFormsModule,
    NzButtonModule, 
    NzCheckboxModule,
    NzDatePickerModule,
    NzInputNumberModule,
    NzNotificationModule,
    NzDrawerModule,
    AngularFireModule.initializeApp(environment.firebase)   ,
    AngularFireStorageModule,
    NzUploadModule,
    NzSwitchModule,
    NzIconModule,
   
   
    HttpClientJsonpModule, 
    ScrollingModule,
    DragDropModule

  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
