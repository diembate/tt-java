import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderDetailInfo } from 'app/shared/model/order-detail-info.model';
import { OrderDetailInfoService } from './order-detail-info.service';

@Component({
  templateUrl: './order-detail-info-delete-dialog.component.html'
})
export class OrderDetailInfoDeleteDialogComponent {
  orderDetailInfo?: IOrderDetailInfo;

  constructor(
    protected orderDetailInfoService: OrderDetailInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderDetailInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderDetailInfoListModification');
      this.activeModal.close();
    });
  }
}
