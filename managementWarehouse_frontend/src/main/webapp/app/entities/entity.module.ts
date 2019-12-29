import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'brand',
        loadChildren: () => import('./brand/brand.module').then(m => m.ManagementWarehouseBrandModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.ManagementWarehouseCategoryModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.ManagementWarehouseProductModule)
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.ManagementWarehouseCustomerModule)
      },
      {
        path: 'import-detail-info',
        loadChildren: () => import('./import-detail-info/import-detail-info.module').then(m => m.ManagementWarehouseImportDetailInfoModule)
      },
      {
        path: 'import-info',
        loadChildren: () => import('./import-info/import-info.module').then(m => m.ManagementWarehouseImportInfoModule)
      },
      {
        path: 'order-detail-info',
        loadChildren: () => import('./order-detail-info/order-detail-info.module').then(m => m.ManagementWarehouseOrderDetailInfoModule)
      },
      {
        path: 'order-info',
        loadChildren: () => import('./order-info/order-info.module').then(m => m.ManagementWarehouseOrderInfoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ManagementWarehouseEntityModule {}
