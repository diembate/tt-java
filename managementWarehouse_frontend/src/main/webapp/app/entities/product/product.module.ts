import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagementWarehouseSharedModule } from 'app/shared/shared.module';
import { ProductComponent } from './product.component';
import { ProductDetailComponent } from './product-detail.component';
import { ProductUpdateComponent } from './product-update.component';
import { ProductDeleteDialogComponent } from './product-delete-dialog.component';
import { productRoute } from './product.route';
import { OrderDetailInfoUpdateComponent } from './order/order-detail-info-update/order-detail-info-update.component';
import { ImportDetailInfoUpdateComponent } from './import/import-detail-info-update/import-detail-info-update.component';


@NgModule({
  imports: [ManagementWarehouseSharedModule, RouterModule.forChild(productRoute)],
  declarations: [ProductComponent, ProductDetailComponent, ProductUpdateComponent, ProductDeleteDialogComponent, OrderDetailInfoUpdateComponent,ImportDetailInfoUpdateComponent],
  entryComponents: [ProductDeleteDialogComponent,OrderDetailInfoUpdateComponent,ImportDetailInfoUpdateComponent]
})
export class ManagementWarehouseProductModule {}
