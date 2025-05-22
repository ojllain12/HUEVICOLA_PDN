import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { APP_CONFIG } from '../shared/constants';
import { catchError, map, Observable, of, throwError } from 'rxjs';
import { Category, Product, ProductMapped, Type } from '../objects/product.interface';
import { NotificationService } from '../views/common/notification.service';

@Injectable({
  providedIn: 'root'
})
export class UserServices {

  constructor(
    private http: HttpClient,
    private storage: StorageService,
    private notification: NotificationService) { }

    loginWithGithub(code:string){
        return this.http.post<any>(`${APP_CONFIG.BACKEND_URL}/api/github/auth`, { code });
    }
}
