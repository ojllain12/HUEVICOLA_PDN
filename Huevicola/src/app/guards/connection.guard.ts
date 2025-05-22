import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { catchError, map, of, timeout, firstValueFrom } from 'rxjs';
import { APP_CONFIG } from '../shared/constants';

@Injectable({
  providedIn: 'root'
})
export class ConnectionGuard implements CanActivate {

  constructor(private http: HttpClient, private router: Router) { }

  async canActivate(): Promise<boolean> {
    const isConnected = await this.checkConnection();
    if (isConnected) {
      console.log('Connection OK');
      return true;
    } else {
      console.log('Connection failed');
      return this.router.navigate(['/offline']);
    }
  }

  public async checkConnection(): Promise<boolean> {
    try {
      const result = await firstValueFrom(
        this.http.get(APP_CONFIG.BACKEND_URL + '/ping', { responseType: 'text' }).pipe(
          timeout(10),
          map(() => true),
          catchError((error) => {
            //console.error('Error completo:', error);
            return of(false);
          })
        )
      );
      return result;
    } catch (err) {
      //console.error('Error inesperado en checkConnection:', err);
      return false;
    }
  }
}
