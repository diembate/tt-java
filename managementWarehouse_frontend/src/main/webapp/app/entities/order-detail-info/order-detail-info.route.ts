import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderDetailInfo, OrderDetailInfo } from 'app/shared/model/order-detail-info.model';
import { OrderDetailInfoService } from './order-detail-info.service';
import { OrderDetailInfoComponent } from './order-detail-info.component';
import { OrderDetailInfoDetailComponent } from './order-detail-info-detail.component';
import { OrderDetailInfoUpdateComponent } from './order-detail-info-update.component';

@Injectable({ providedIn: 'root' })
export class OrderDetailInfoResolve implements Resolve<IOrderDetailInfo> {
  constructor(private service: OrderDetailInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderDetailInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderDetailInfo: HttpResponse<OrderDetailInfo>) => {
          if (orderDetailInfo.body) {
            return of(orderDetailInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderDetailInfo());
  }
}

export const orderDetailInfoRoute: Routes = [
  {
    path: '',
    component: OrderDetailInfoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.orderDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderDetailInfoDetailComponent,
    resolve: {
      orderDetailInfo: OrderDetailInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.orderDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderDetailInfoUpdateComponent,
    resolve: {
      orderDetailInfo: OrderDetailInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.orderDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderDetailInfoUpdateComponent,
    resolve: {
      orderDetailInfo: OrderDetailInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'managementWarehouseApp.orderDetailInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
