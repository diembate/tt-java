import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'brand',
        loadChildren: () => import('./brand/brand.module').then(m => m.WarehouseBrandModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.WarehouseCategoryModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.WarehouseProductModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.WarehouseCustomerModule)
      },
      {
        path: 'import-detail-info',
        loadChildren: () => import('./import-detail-info/import-detail-info.module').then(m => m.WarehouseImportDetailInfoModule)
      },
      {
        path: 'import-info',
        loadChildren: () => import('./import-info/import-info.module').then(m => m.WarehouseImportInfoModule)
      },
      {
        path: 'report',
        loadChildren: () => import('./report/report.module').then(m => m.WarehouseReportModule)
      },
      {
        path: 'order-detail-info',
        loadChildren: () => import('./order-detail-info/order-detail-info.module').then(m => m.WarehouseOrderDetailInfoModule)
      },
      {
        path: 'order-info',
        loadChildren: () => import('./order-info/order-info.module').then(m => m.WarehouseOrderInfoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class WarehouseEntityModule {}
