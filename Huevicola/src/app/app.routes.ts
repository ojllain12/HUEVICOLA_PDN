import { Routes } from '@angular/router';
import { ConnectionGuard } from './guards/connection.guard';
import { MainComponent } from './views/main/main/main.component';
import { ProductsComponent } from './views/main/products/products.component';
import { TokenGuard } from './guards/token.guard';
import { OfflineGuard } from './guards/offline.guard';
import { loginGuard } from './guards/login.guard';
import { UsersComponent } from './views/main/users/users.component';

export const routes: Routes = [
  {
    path: 'offline',
    canActivate: [OfflineGuard],
    loadComponent: () => import('./views/offline/offline.component').then(m => m.OfflineComponent)
  },
  {
    path: 'main',
    canActivate: [ConnectionGuard, TokenGuard],
    component: MainComponent,
    children:[
      {
        path: 'products', component: ProductsComponent
      },
      {
        path: 'users', component: UsersComponent
      },
      {
        path:'**', redirectTo: 'products', pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    canActivate: [ConnectionGuard, loginGuard],
    loadComponent: () => import('./views/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];
