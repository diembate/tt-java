import { ICategory } from 'app/shared/model/category.model';
import { IBrand } from 'app/shared/model/brand.model';

export interface IProduct {
  id?: number;
  productName?: string;
  priceProduct?: number;
  quantityProduct?: number;
  category?: ICategory;
  brand?: IBrand;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productName?: string,
    public priceProduct?: number,
    public quantityProduct?: number,
    public category?: ICategory,
    public brand?: IBrand
  ) {}
}
