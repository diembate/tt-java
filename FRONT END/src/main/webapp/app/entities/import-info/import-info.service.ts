import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IImportInfo } from 'app/shared/model/import-info.model';

type EntityResponseType = HttpResponse<IImportInfo>;
type EntityArrayResponseType = HttpResponse<IImportInfo[]>;

@Injectable({ providedIn: 'root' })
export class ImportInfoService {
  public resourceUrl = SERVER_API_URL + 'api/import-infos';

  constructor(protected http: HttpClient) {}

  create(importInfo: IImportInfo): Observable<EntityResponseType> {
    return this.http.post<IImportInfo>(this.resourceUrl, importInfo, { observe: 'response' });
  }

  update(importInfo: IImportInfo): Observable<EntityResponseType> {
    return this.http.put<IImportInfo>(this.resourceUrl, importInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImportInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImportInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
