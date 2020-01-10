import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImportDetailInfo } from 'app/shared/model/import-detail-info.model';

type EntityResponseType = HttpResponse<IImportDetailInfo>;
type EntityArrayResponseType = HttpResponse<IImportDetailInfo[]>;

@Injectable({ providedIn: 'root' })
export class ImportDetailInfoService {
  public resourceUrl = SERVER_API_URL + 'api/import-detail-infos';

  constructor(protected http: HttpClient) {}

  create(importDetailInfo: IImportDetailInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(importDetailInfo);
    return this.http
      .post<IImportDetailInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(importDetailInfo: IImportDetailInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(importDetailInfo);
    return this.http
      .put<IImportDetailInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IImportDetailInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IImportDetailInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(importDetailInfo: IImportDetailInfo): IImportDetailInfo {
    const copy: IImportDetailInfo = Object.assign({}, importDetailInfo, {
      importDate:
        importDetailInfo.importDate && importDetailInfo.importDate.isValid() ? importDetailInfo.importDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.importDate = res.body.importDate ? moment(res.body.importDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((importDetailInfo: IImportDetailInfo) => {
        importDetailInfo.importDate = importDetailInfo.importDate ? moment(importDetailInfo.importDate) : undefined;
      });
    }
    return res;
  }
}
