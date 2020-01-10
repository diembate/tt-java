import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrderInfo } from 'app/shared/model/order-info.model';

type EntityResponseType = HttpResponse<IOrderInfo>;
type EntityArrayResponseType = HttpResponse<IOrderInfo[]>;

@Injectable({ providedIn: 'root' })
export class OrderInfoService {
  public resourceUrl = SERVER_API_URL + 'api/order-infos';

  constructor(protected http: HttpClient) {}

  create(orderInfo: IOrderInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderInfo);
    return this.http
      .post<IOrderInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(orderInfo: IOrderInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderInfo);
    return this.http
      .put<IOrderInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrderInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrderInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(orderInfo: IOrderInfo): IOrderInfo {
    const copy: IOrderInfo = Object.assign({}, orderInfo, {
      orderDate: orderInfo.orderDate && orderInfo.orderDate.isValid() ? orderInfo.orderDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderDate = res.body.orderDate ? moment(res.body.orderDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((orderInfo: IOrderInfo) => {
        orderInfo.orderDate = orderInfo.orderDate ? moment(orderInfo.orderDate) : undefined;
      });
    }
    return res;
  }
}
