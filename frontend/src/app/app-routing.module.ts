import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EventComponent } from './event/event/event.component'; // Importez le composant EventComponent
import { WelcomeComponent } from './pages/welcome/welcome.component'; // Importez le composant WelcomeComponent (s'il existe)
import { CategoryComponent } from './event/category/category.component';
import { SubCategoryComponent } from './event/sub-category/sub-category.component';
import { AddEventComponent } from './event/add-event/add-event.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './event/user/user.component';
import { OrderComponent } from './event/order/order.component';
 
  import { TestComponent } from './event/test/test.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full', },
  { path: 'login', component: LoginComponent },
 // { path: '', redirectTo: '/welcome', pathMatch: 'full' }, // Rediriger vers /welcome lorsque l'URL est vide
  { path: 'event', component: EventComponent }, // Configuration de la route pour le composant EventComponent
 
  { path: 'category', component: CategoryComponent },
  { path: 'sub-category', component: SubCategoryComponent },
  { path: 'add-event', component: AddEventComponent },
  { path: 'add-event/:id', component: AddEventComponent },
  { path: 'user', component: UserComponent },
  { path: 'order', component: OrderComponent },
  { path: 'test', component: TestComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
