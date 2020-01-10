import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IReport } from 'app/shared/model/report.model';
import { ReportService } from './report.service';

@Component({
  selector: 'jhi-report',
  templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit, OnDestroy {
  reports?: IReport[];
  eventSubscriber?: Subscription;

  constructor(protected reportService: ReportService, protected eventManager: JhiEventManager) {}

  loadAll(): void {
    this.reportService.query().subscribe((res: HttpResponse<IReport[]>) => {
      this.reports = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('reportListModification', () => this.loadAll());
  }
}
