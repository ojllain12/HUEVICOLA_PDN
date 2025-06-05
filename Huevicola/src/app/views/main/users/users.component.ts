import { Component, OnInit } from '@angular/core';
import { User } from '../../../objects/user.interface';
import { NotificationService } from '../../common/notification.service';
import { UserService } from '../../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {
  list_users: User[] = [];
  filteredUsers: User[] = [];
  searchText: string = '';
  searchDate: string = '';

  constructor(private user: UserService,
    private notification: NotificationService
  ) {

  }

  ngOnInit(): void {
    this.user.getAllUsers().subscribe(data => {
      this.list_users = data;
      this.filteredUsers = [...this.list_users];
    });
  }
  clearDate() {
    this.searchDate = '';
    this.filterUsers();
  }

  filterUsers() {
    const text = this.searchText.toLowerCase().trim();
    const date = this.searchDate;
    if (!date && !text) {
      this.filteredUsers = [...this.list_users];
      return;
    }

    this.filteredUsers = this.list_users.filter(user => {
      const name = user.name ? user.name.toLowerCase() : '';
      const email = user.email ? user.email.toLowerCase() : '';
      const lastAccessDate = user.lastAccess ? user.lastAccess.split('T')[0] : '';

      const matchesText = name.includes(text) || email.includes(text);
      const matchesDate = !date || lastAccessDate === date;

      return matchesText && matchesDate;
    });
  }

  async deleteUserByUID(idx: number) {
    let name = this.list_users[idx].name ? this.list_users[idx].name : this.list_users[idx].email;
    this.notification.showConfirmDelete(`¿Seguro que deseas eliminar a ${name}?`, async () => {
      this.notification.showLoading("Eliminando usuario...");
      try {
        this.user.deleteUserByUID(this.list_users[idx].uid).subscribe(success => {
          if (success) {
            this.notification.hideLoading();
            this.notification.showSuccess('Usuario eliminado con exito!');
            this.ngOnInit();
          } else {
            throw new Error();
          }
        })
      } catch (error: any) {
        this.notification.hideLoading();
        console.error('Error al eliminar usuario' + error);
      }
    });
  }
}
