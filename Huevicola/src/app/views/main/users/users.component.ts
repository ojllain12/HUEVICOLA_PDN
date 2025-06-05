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

  constructor(private user: UserService,
    private notification: NotificationService
  ) {

  }

  ngOnInit(): void {
    this.user.getAllUsers().subscribe(data => {
      this.list_users = data;
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
