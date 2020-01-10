import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WarehouseSharedModule } from 'app/shared/shared.module';
import { ReportComponent } from './report.component';
import { ReportDetailComponent } from './report-detail.component';

import { reportRoute } from './report.route';

@NgModule({
  imports: [WarehouseSharedModule, RouterModule.forChild(reportRoute)],
  declarations: [ReportComponent, ReportDetailComponent,],
  entryComponents: []
})
export class WarehouseReportModule {}
