import { Injectable } from '@angular/core';
import { CanActivate, CanActivateFn, Router } from '@angular/router';
import { StorageService } from '../services/storage.service';

@Injectable({
  providedIn: 'root'
})
export class loginGuard implements CanActivate {

  constructor(
    private router: Router,
    private storage: StorageService) { }

  canActivate() {
    const token = this.storage.getToken();
    if (!token) {
      return true;
    } else {
      return this.router.navigate(['/main']);
    }
  }
}
