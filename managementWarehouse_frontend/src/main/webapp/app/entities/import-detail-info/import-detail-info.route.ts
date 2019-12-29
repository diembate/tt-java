import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IImportDetailInfo, ImportDetailInfo } from 'app/shared/model/import-detail-info.model';
import { ImportDetailInfoService } from './import-detail-info.service';
import { ImportDetailInfoComponent } from './import-detail-info.component';
import { ImportDetailInfoDetailComponent } from './import-detail-info-detail.component';
import { ImportDetailInfoUpdateComponent } from './import-detail-info-update.component';

@Injectable({ providedIn: 'root' })
export class ImportDetailInfoResolve implements Resolve<IImportDetailInfo> {
  constructor(private service: ImportDetailInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImportDetailInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((importDetailInfo: HttpResponse<ImportDetailInfo>) => {
          if (importDetailInfo.body) {
            return of(importDetailInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ImportDetailInfo());
  }
}

export const importDetailInfoRoute: Routes = [
  {
    path: '',
    component: ImportDetailInfoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.importDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImportDetailInfoDetailComponent,
    resolve: {
      importDetailInfo: ImportDetailInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.importDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImportDetailInfoUpdateComponent,
    resolve: {
      importDetailInfo: ImportDetailInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.importDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImportDetailInfoUpdateComponent,
    resolve: {
      importDetailInfo: ImportDetailInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.importDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
