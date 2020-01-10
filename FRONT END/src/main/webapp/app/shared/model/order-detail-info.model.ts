import { Moment } from 'moment';

export interface IOrderDetailInfo {
  id?: number;
  productName?: string;
  priceProduct?: number;
  quantityOrder?: number;
  amount?: number;
  orderDate?: Moment;
  orderInfoId?: number;
  productId?: number;
  reportId?: number;
}

export class OrderDetailInfo implements IOrderDetailInfo {
  constructor(
    public id?: number,
    public productName?: string,
    public priceProduct?: number,
    public quantityOrder?: number,
    public amount?: number,
    public orderDate?: Moment,
    public orderInfoId?: number,
    public productId?: number,
    public reportId?: number
  ) {}
}
