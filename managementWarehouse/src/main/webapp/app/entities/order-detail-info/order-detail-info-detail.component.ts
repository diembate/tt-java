import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderDetailInfo } from 'app/shared/model/order-detail-info.model';

@Component({
  selector: 'jhi-order-detail-info-detail',
  templateUrl: './order-detail-info-detail.component.html'
})
export class OrderDetailInfoDetailComponent implements OnInit {
  orderDetailInfo: IOrderDetailInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderDetailInfo }) => {
      this.orderDetailInfo = orderDetailInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
