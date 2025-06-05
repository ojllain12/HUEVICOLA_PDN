import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { APP_CONFIG } from '../shared/constants';
import { catchError, map, Observable, of } from 'rxjs';
import { User } from '../objects/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private storage: StorageService) { }

  getAllUsers(): Observable<User[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    return this.http.get<User[]>(`${APP_CONFIG.BACKEND_URL}/api/firebase-users`, { headers }).pipe(
      catchError(error => {
        console.error('Error fetching products:', error);
        return of([]);
      })
    );
  }

  deleteUserByUID(uid:string):Observable<boolean>{
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    console.log('ID a eliminar:', uid);
    return this.http.delete(`${APP_CONFIG.BACKEND_URL}/api/firebase-users/${uid}`, { headers }).pipe(
      map(() => {
        console.log('Usuario eliminado con éxito');
        return true
      }),
      catchError(error => {
        console.error('Error al eliminar:', error);
        return of(false);
      })
    );
  }
}
