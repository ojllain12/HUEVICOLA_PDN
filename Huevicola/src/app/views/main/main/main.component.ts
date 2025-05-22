import { Component } from '@angular/core';
import { SidebarComponent } from '../../common/sidebar/sidebar.component';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    SidebarComponent, RouterOutlet],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {
  constructor(
    private router: Router) {
  }
}
