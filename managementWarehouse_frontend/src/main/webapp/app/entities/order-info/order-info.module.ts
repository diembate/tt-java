import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagementWarehouseSharedModule } from 'app/shared/shared.module';
import { OrderInfoComponent } from './order-info.component';
import { OrderInfoDetailComponent } from './order-info-detail.component';
import { OrderInfoUpdateComponent } from './order-info-update.component';
import { OrderInfoDeleteDialogComponent } from './order-info-delete-dialog.component';
import { orderInfoRoute } from './order-info.route';

@NgModule({
  imports: [ManagementWarehouseSharedModule, RouterModule.forChild(orderInfoRoute)],
  declarations: [OrderInfoComponent, OrderInfoDetailComponent, OrderInfoUpdateComponent, OrderInfoDeleteDialogComponent],
  entryComponents: [OrderInfoDeleteDialogComponent]
})
export class ManagementWarehouseOrderInfoModule {}
