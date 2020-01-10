import { Moment } from 'moment';
import { IOrderDetailInfo } from 'app/shared/model/order-detail-info.model';

export interface IOrderInfo {
  id?: number;
  amount?: number;
  orderDate?: Moment;
  customerId?: number;
  orderDetailInfos?: IOrderDetailInfo[];
}

export class OrderInfo implements IOrderInfo {
  constructor(
    public id?: number,
    public amount?: number,
    public orderDate?: Moment,
    public customerId?: number,
    public orderDetailInfos?: IOrderDetailInfo[]
  ) {}
}
