import { Routes } from '@angular/router';
import { ConnectionGuard } from './guards/connection.guard';
import { MainComponent } from './views/main/main/main.component';
import { ProductsComponent } from './views/main/products/products.component';
import { TokenGuard } from './guards/token.guard';
import { OfflineGuard } from './guards/offline.guard';
import { loginGuard } from './guards/login.guard';
import { GoogleComponent } from './callbacks/google/google.component';
import { GithubComponent } from './callbacks/github/github.component';
import { FacebookComponent } from './callbacks/facebook/facebook.component';

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
        path:'**', redirectTo: 'products', pathMatch: 'full'
      }
    ]
  },
  {
    path: 'callback',
    canActivate: [ConnectionGuard, loginGuard],
    children:[
      {
        path: 'google', component: GoogleComponent
      },
      {
        path: 'github', component: GithubComponent
      },
      {
        path: 'facebook', component: FacebookComponent
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
    loadComponent: () => import('./views/common/page-not-found/page-not-found.component').then(m => m.PageNotFoundComponent)
  }
];
