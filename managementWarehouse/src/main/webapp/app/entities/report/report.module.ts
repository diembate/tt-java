import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManagementWarehouseSharedModule } from 'app/shared/shared.module';
import { ReportComponent } from './report.component';
import { ReportDetailComponent } from './report-detail.component';
import { reportRoute } from './report.route';

@NgModule({
  imports: [ManagementWarehouseSharedModule, RouterModule.forChild(reportRoute)],
  declarations: [ReportComponent, ReportDetailComponent],
  entryComponents: []
})
export class ManagementWarehouseReportModule {}
