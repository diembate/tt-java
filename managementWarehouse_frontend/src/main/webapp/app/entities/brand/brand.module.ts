import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagementWarehouseSharedModule } from 'app/shared/shared.module';
import { BrandComponent } from './brand.component';
import { BrandDetailComponent } from './brand-detail.component';
import { BrandUpdateComponent } from './brand-update.component';
import { BrandDeleteDialogComponent } from './brand-delete-dialog.component';
import { brandRoute } from './brand.route';

@NgModule({
  imports: [ManagementWarehouseSharedModule, RouterModule.forChild(brandRoute)],
  declarations: [BrandComponent, BrandDetailComponent, BrandUpdateComponent, BrandDeleteDialogComponent],
  entryComponents: [BrandDeleteDialogComponent]
})
export class ManagementWarehouseBrandModule {}
