import { IProduct } from 'app/shared/model/product.model';
import { IOrderDetailInfo } from 'app/shared/model/order-detail-info.model';

export interface IReport {
  id?: number;
  productName?: string;
  quantityRemaining?: number;
  totalOrderQuantity?: number;
  products?: IProduct[];
  orderDetailInfos?: IOrderDetailInfo[];
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public productName?: string,
    public quantityRemaining?: number,
    public totalOrderQuantity?: number,
    public products?: IProduct[],
    public orderDetailInfos?: IOrderDetailInfo[]
  ) {}
}
