import { Moment } from 'moment';
import { IImportInfo } from 'app/shared/model/import-info.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IImportDetailInfo {
  id?: number;
  productName?: string;
  quantityImport?: number;
  importDate?: Moment;
  priceImport?: number;
  importInfo?: IImportInfo;
  productId?: IProduct;
}

export class ImportDetailInfo implements IImportDetailInfo {
  constructor(
    public id?: number,
    public productName?: string,
    public quantityImport?: number,
    public importDate?: Moment,
    public priceImport?: number,
    public importInfo?: IImportInfo,
    public productId?: IProduct
  ) {}
}
