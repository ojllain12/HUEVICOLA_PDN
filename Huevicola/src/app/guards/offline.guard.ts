import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { ConnectionGuard } from './connection.guard';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OfflineGuard implements CanActivate  {

  constructor(
    private connectionGuard: ConnectionGuard,
    private router: Router) { }

  async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    const isConnected = await this.connectionGuard.checkConnection();
    if(isConnected){
      return this.router.navigate(['/main']);
    }else{
      return true;
    }
  }

}
