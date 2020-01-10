import { IProduct } from 'app/shared/model/product.model';

export interface ICategory {
  id?: number;
  cateName?: string;
  products?: IProduct[];
}

export class Category implements ICategory {
  constructor(public id?: number, public cateName?: string, public products?: IProduct[]) {}
}
