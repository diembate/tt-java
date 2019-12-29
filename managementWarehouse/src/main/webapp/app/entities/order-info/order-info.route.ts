import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrderInfo, OrderInfo } from 'app/shared/model/order-info.model';
import { OrderInfoService } from './order-info.service';
import { OrderInfoComponent } from './order-info.component';
import { OrderInfoDetailComponent } from './order-info-detail.component';
import { OrderInfoUpdateComponent } from './order-info-update.component';

@Injectable({ providedIn: 'root' })
export class OrderInfoResolve implements Resolve<IOrderInfo> {
  constructor(private service: OrderInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((orderInfo: HttpResponse<OrderInfo>) => {
          if (orderInfo.body) {
            return of(orderInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderInfo());
  }
}

export const orderInfoRoute: Routes = [
  {
    path: '',
    component: OrderInfoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderInfoDetailComponent,
    resolve: {
      orderInfo: OrderInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderInfoUpdateComponent,
    resolve: {
      orderInfo: OrderInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderInfos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderInfoUpdateComponent,
    resolve: {
      orderInfo: OrderInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrderInfos'
    },
    canActivate: [UserRouteAccessService]
  }
];
