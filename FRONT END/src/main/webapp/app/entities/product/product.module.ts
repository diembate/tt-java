import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WarehouseSharedModule } from 'app/shared/shared.module';
import { ProductComponent } from './product.component';
import { ProductDetailComponent } from './product-detail.component';
import { ProductUpdateComponent } from './product-update.component';
import { ProductDeleteDialogComponent } from './product-delete-dialog.component';
import { productRoute } from './product.route';
import { ImportDetailInfoUpdateComponent } from './import-detail-info-update/import-detail-info-update.component';
import { OrderDetailInfoUpdateComponent } from './order-detail-info-update/order-detail-info-update.component';


@NgModule({
  imports: [WarehouseSharedModule, RouterModule.forChild(productRoute)],
  declarations: [ProductComponent, ProductDetailComponent, ProductUpdateComponent, ProductDeleteDialogComponent, ImportDetailInfoUpdateComponent, OrderDetailInfoUpdateComponent],
  entryComponents: [ProductDeleteDialogComponent,ImportDetailInfoUpdateComponent,OrderDetailInfoUpdateComponent]
})
export class WarehouseProductModule {}
