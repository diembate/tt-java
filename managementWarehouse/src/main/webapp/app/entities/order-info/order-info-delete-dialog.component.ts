import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderInfo } from 'app/shared/model/order-info.model';
import { OrderInfoService } from './order-info.service';

@Component({
  templateUrl: './order-info-delete-dialog.component.html'
})
export class OrderInfoDeleteDialogComponent {
  orderInfo?: IOrderInfo;

  constructor(protected orderInfoService: OrderInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('orderInfoListModification');
      this.activeModal.close();
    });
  }
}
