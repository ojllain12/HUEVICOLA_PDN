import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { StorageService } from '../../../services/storage.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  isSidebarOpen: boolean = true;

  constructor(
    private router: Router,
    private storage: StorageService) {
  }

  goToProducts() {
    this.router.navigate(['/main/products']);
  }
  goToUsers() {
    this.router.navigate(['/main/users']);
  }

  logOut() {
    this.storage.deleteToken();
    this.router.navigate(['/login']);
  }
}
