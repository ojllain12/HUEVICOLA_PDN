import { Component, OnInit } from '@angular/core';
import { ProductsService } from '../../../services/products.service';
import { RouterOutlet } from "@angular/router";
import { Category, Product, ProductMapped, Type } from '../../../objects/product.interface';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NotificationService } from '../../common/notification.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {

  list_products: ProductMapped[] = [];
  list_types: Type[] = [];
  list_category: Category[] = [];
  product_temp: ProductMapped = {
    date: '',
    id: undefined,
    name: '',
    price: 0,
    stock: 0,
    type: {
      category: {
        id: -1,
        name: ''
      },
      id: -1,
      name: '',
      user: '',
    },
    editMode: false,
  };

  constructor(private product: ProductsService,
    private notification: NotificationService
  ) {
  }
  async ngOnInit() {
    this.product.getAllProducts().subscribe(data => {
      this.list_products = data;
    });
    this.product.getAllCategories().subscribe(data => {
      this.list_category = data;
    });
    this.product.getAllTypes().subscribe(data => {
      this.list_types = data;
    });
  }

  async editField(index: number) {
    this.cancelEditAllRows();
    this.list_products[index].editMode = true;
    this.product_temp = { ...this.list_products[index] };
  }

  async deleteField(index: number) {
    this.notification.showConfirmDelete(`¿Seguro que deseas eliminar el ${this.list_products[index].name}?`, async () => {
      await this.deleteProduct(this.list_products[index].id ? this.list_products[index].id : -1);
    });

  }

  async saveField(index: number) {
    this.notification.showLoading("Creando producto...");
    if (this.list_products[index].id === undefined) {
      try {
        const mapped: ProductMapped = this.product_temp;
        const temp: Product = Object.assign({}, mapped);
        this.product.createProduct(temp)?.subscribe(success => {
          this.resetTemp();
          if (success) {
            this.notification.hideLoading();
            this.notification.showSuccess('Producto creado con exito!');
            this.ngOnInit();
          } else {
            this.notification.hideLoading();
            this.notification.showError('Error al crear producto');
          }
        })
      } catch (error: any) {
        await this.resetTemp();
        this.notification.hideLoading();
        console.error('Error al crear producto' + error);
      }
    } else {
      try {
        const mapped: ProductMapped = this.product_temp;
        const temp: Product = Object.assign({}, mapped);
        this.product.updateProduct(temp)?.subscribe(success => {
          this.resetTemp();
          if (success) {
            this.notification.hideLoading();
            this.notification.showSuccess('Producto actualizado con exito!');
            this.ngOnInit();
          } else {
            this.notification.hideLoading();
            this.notification.showError('Error al actualizar producto');
          }
        })
      } catch (error: any) {
        await this.resetTemp();
        this.notification.hideLoading();
        console.error('Error al actualizar producto' + error);
      }
    }

  }

  async cancelEdit(index: number) {
    if (this.list_products[index].id === undefined) {
      await this.resetTemp();
      this.list_products.pop();
      return;
    }
    this.cancelEditAllRows();
    this.list_products[index].editMode = false;

  }

  async resetTemp() {
    this.product_temp = {
      date: '',
      id: undefined,
      name: '',
      price: 0,
      stock: 0,
      type: {
        category: {
          id: -1,
          name: ''
        },
        id: -1,
        name: '',
        user: '',
      },
      editMode: false,
    };
  }

  async addNewRow() {
    const len = this.list_products.length;
    if (len > 0 && this.list_products[len - 1].id === undefined) {
      return;
    }
    this.cancelEditAllRows();
    const empty: ProductMapped = {
      date: '',
      id: undefined,
      name: '',
      price: 0,
      stock: 0,
      type: {
        category: {
          id: -1,
          name: ''
        },
        id: -1,
        name: '',
        user: '',
      },
      editMode: true,
    };
    this.list_products.push(empty);
  }

  deleteLastRow() {
    console.log(this.list_products);
    if (this.list_products[this.list_products.length - 1].id === undefined) {
      console.log('Eliminando la ultima fila');
      this.list_products.pop();
      return;
    }
  }

  cancelEditAllRows() {
    for (let i = this.list_products.length - 1; i >= 0; i--) {
      if (this.list_products[i].editMode) {
        this.list_products[i].editMode = false;
      }
    }
  }
  async deleteProduct(id: number) {
    this.notification.showLoading("Eliminando producto...");
    try {
      this.product.deleteProductById(id).subscribe(success => {
        if (success) {
          this.notification.hideLoading();
          this.notification.showSuccess('Producto eliminado con exito!');
          this.ngOnInit();
        } else {
          throw new Error();
        }
      })
    } catch (error: any) {
      this.notification.hideLoading();
      console.error('Error al eliminar producto' + error);
    }
  }
}
