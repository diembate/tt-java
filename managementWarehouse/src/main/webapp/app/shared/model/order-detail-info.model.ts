import { Moment } from 'moment';
import { IOrderInfo } from 'app/shared/model/order-info.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IOrderDetailInfo {
  id?: number;
  productName?: string;
  priceProduct?: number;
  quantityOrder?: number;
  amount?: number;
  orderDate?: Moment;
  orderInfo?: IOrderInfo;
  product?: IProduct;
}

export class OrderDetailInfo implements IOrderDetailInfo {
  constructor(
    public id?: number,
    public productName?: string,
    public priceProduct?: number,
    public quantityOrder?: number,
    public amount?: number,
    public orderDate?: Moment,
    public orderInfo?: IOrderInfo,
    public product?: IProduct
  ) {}
}
