import { IImportDetailInfo } from 'app/shared/model/import-detail-info.model';

export interface IImportInfo {
  id?: number;
  deliverPerson?: string;
  supplier?: string;
  cost?: number;
  importDetailInfos?: IImportDetailInfo[];
}

export class ImportInfo implements IImportInfo {
  constructor(
    public id?: number,
    public deliverPerson?: string,
    public supplier?: string,
    public cost?: number,
    public importDetailInfos?: IImportDetailInfo[]
  ) {}
}
