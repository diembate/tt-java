import { Moment } from 'moment';

export interface IImportDetailInfo {
  id?: number;
  productName?: string;
  quantityImport?: number;
  importDate?: Moment;
  priceImport?: number;
  importInfoId?: number;
  productId?: number;
}

export class ImportDetailInfo implements IImportDetailInfo {
  constructor(
    public id?: number,
    public productName?: string,
    public quantityImport?: number,
    public importDate?: Moment,
    public priceImport?: number,
    public importInfoId?: number,
    public productId?: number
  ) {}
}
