import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WarehouseSharedModule } from 'app/shared/shared.module';
import { OrderInfoComponent } from './order-info.component';
import { OrderInfoDetailComponent } from './order-info-detail.component';
import { OrderInfoUpdateComponent } from './order-info-update.component';
import { OrderInfoDeleteDialogComponent } from './order-info-delete-dialog.component';
import { orderInfoRoute } from './order-info.route';

@NgModule({
  imports: [WarehouseSharedModule, RouterModule.forChild(orderInfoRoute)],
  declarations: [OrderInfoComponent, OrderInfoDetailComponent, OrderInfoUpdateComponent, OrderInfoDeleteDialogComponent],
  entryComponents: [OrderInfoDeleteDialogComponent]
})
export class WarehouseOrderInfoModule {}
