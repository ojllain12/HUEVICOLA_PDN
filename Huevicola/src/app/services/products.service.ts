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
export class ProductsService {

  constructor(
    private http: HttpClient,
    private storage: StorageService,
    private notification: NotificationService) { }


  getAllProducts(): Observable<ProductMapped[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    return this.http.get<ProductMapped[]>(`${APP_CONFIG.BACKEND_URL}/api/item`, { headers }).pipe(
      map((response: ProductMapped[]) => {
        return response.map(item => ({
          ...item,
          editMode: false
        }));
      }),
      catchError(error => {
        console.error('Error fetching products:', error);
        return of([]);
      })
    );
  }
  getAllTypes(): Observable<Type[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    return this.http.get<Type[]>(`${APP_CONFIG.BACKEND_URL}/api/type`, { headers }).pipe(
      map((response: Type[]) => {
        return response.map(item => ({
          ...item
        }));
      }),
      catchError(error => {
        console.error('Error fetching types:', error);
        return of([]);
      })
    );
  }
  getAllCategories(): Observable<Category[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    return this.http.get<Category[]>(`${APP_CONFIG.BACKEND_URL}/api/category`, { headers }).pipe(
      map((response: Category[]) => {
        return response.map(item => ({
          ...item
        }));
      }),
      catchError(error => {
        console.error('Error fetching categories:', error);
        return of([]);
      })
    );
  }
  deleteProductById(id: number): Observable<boolean> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    console.log('ID a eliminar:', id);
    return this.http.delete(`${APP_CONFIG.BACKEND_URL}/api/item/${id}`, { headers }).pipe(
      map(() => {
        console.log('Producto eliminado con éxito');
        return true
      }),
      catchError(error => {
        console.error('Error al eliminar:', error);
        return of(false);
      })
    );
  }
  createProduct(product: Product): Observable<Product> | null {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    console.log('Producto a crear:', product);
    return this.http.post<Product>(`${APP_CONFIG.BACKEND_URL}/api/item`, product, { headers }).pipe(
      map((response: Product) => {
        //console.log('Producto creado con éxito:', response);
        return response;
      }),
      catchError((error: any) => {
        this.notification.showError(error.error.message ? error.error.message : 'Error al crear producto');
        return throwError(() => error);
      })
    );
  }
  updateProduct(product: Product): Observable<Product> | null {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.storage.getToken()}`
    });
    console.log('Producto a actualizar:', product);
    return this.http.put<Product>(`${APP_CONFIG.BACKEND_URL}/api/item/${product.id}`, product, { headers }).pipe(
      map((response: Product) => {
        //console.log('Producto actualizado con éxito:', response);
        return response;
      }),
      catchError((error: any) => {
        this.notification.showError(error.error.message ? error.error.message : 'Error al actualizar producto');
        return throwError(() => error);
      })
    );
  }

}
