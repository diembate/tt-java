export interface IProduct {
  id?: number;
  productName?: string;
  priceProduct?: number;
  quantityProduct?: number;
  categoryId?: number;
  brandId?: number;
  reportId?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productName?: string,
    public priceProduct?: number,
    public quantityProduct?: number,
    public categoryId?: number,
    public brandId?: number,
    public reportId?: number
  ) {}
}
