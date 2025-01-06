import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { User } from './user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  listOfData: User[] = [];
  pageIndex: number = 1;  
  pageSize: number = 10;
  totalItems: number = 0;
  editCache: { [key: number]: { edit: boolean; data: User } } = {};
   
  statutest: string = ''; // Nouvelle propriété pour stocker le statut
  switchValues: { [key: number]: boolean } = {}; 
  
  id_user: number[] = [];
contenu_isactive: boolean[] = [];


  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  
 

fetchUsers(): void {
  this.userService.getUsers().subscribe(
    (users: User[]) => {
      this.listOfData = users;
      this.totalItems = users.length;
      this.updateEditCache();

      // Extraction des valeurs des utilisateurs
      this.extractUserValues(users);

      // Autres traitements...
    },
    (error: any) => {
      console.error('Error fetching users:', error);
    }
  );
}


  startEdit(user: User): void {
    this.editCache[user.userId].edit = true;
  }

  cancelEdit(user: User): void {
    const index = this.listOfData.findIndex(item => item.userId === user.userId);
    this.editCache[user.userId] = {
      data: { ...this.listOfData[index] },
      edit: false
    };
  }

  saveEdit(userId: number): void {
    const index = this.listOfData.findIndex(item => item.userId === userId);
    const updatedUser = this.editCache[userId].data;
    this.userService.updateUser(userId, updatedUser).subscribe(
      () => {
        this.editCache[userId].edit = false;
        this.fetchUsers();
      },
      (error: any) => {
        console.error('Error updating user:', error);
      }
    );
  }

  onPageIndexChange(event: number): void {
    this.pageIndex = event;
    this.fetchUsers();
  }

  updateEditCache(): void {
    this.listOfData.forEach(user => {
      this.editCache[user.userId] = {
        edit: false,
        data: { ...user }
      };
    });
  }

  isEditing(user: User, field?: string): boolean {
    if (field) {
      return this.editCache[user.userId].edit && this.editCache[user.userId].data.hasOwnProperty(field);
    }
    return this.editCache[user.userId].edit;
  }


  onSwitchChange(value: boolean, user: User): void {
    const userIdToUpdate = user.userId;
  
    if (value) {
      // Update user
      const updatedUser = { ...user, isActive: 'ACTIVE' };
      this.userService.updateUser(userIdToUpdate, updatedUser).subscribe(
        () => {
          this.fetchUsers();
           
        },
        (error: any) => {
          console.error('Error updating user activation:', error);
        }
      );  location.reload(); // recharge la page après la requête
    } else {
      // Deactivate user
      this.userService.deleteUser(userIdToUpdate).subscribe(
        () => {
          this.fetchUsers();
           
        },
        (error: any) => {
          console.error('Error deleting user:', error);
        }
      );  location.reload(); // recharge la page après la requête
    }
  }

  
  extractUserValues(users: User[]): void {
    this.id_user = [];
    this.contenu_isactive = [];
  
    users.forEach(user => {
      this.id_user.push(user.userId);
      this.contenu_isactive.push(user.isActive === 'ACTIVE');
    });
  }
  
  
  
}
