export interface ProductMapped {
  date: string | null;
  id: number | undefined;
  name: string;
  price: number;
  stock: number;
  type: Type;
  editMode: boolean;
}

export interface Product {
  date: string | null;
  id: number | undefined;
  name: string;
  price: number;
  stock: number;
  type: Type;
}

export interface Type {
  category: Category;
  id: number;
  name: string;
  user: any;
}

export interface Category {
  id: number;
  name: string;
}
