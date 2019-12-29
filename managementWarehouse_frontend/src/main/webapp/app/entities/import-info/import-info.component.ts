import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IImportInfo } from 'app/shared/model/import-info.model';
import { ImportInfoService } from './import-info.service';
import { ImportInfoDeleteDialogComponent } from './import-info-delete-dialog.component';

@Component({
  selector: 'jhi-import-info',
  templateUrl: './import-info.component.html'
})
export class ImportInfoComponent implements OnInit, OnDestroy {
  importInfos?: IImportInfo[];
  eventSubscriber?: Subscription;

  constructor(protected importInfoService: ImportInfoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.importInfoService.query().subscribe((res: HttpResponse<IImportInfo[]>) => {
      this.importInfos = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInImportInfos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IImportInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInImportInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('importInfoListModification', () => this.loadAll());
  }

  delete(importInfo: IImportInfo): void {
    const modalRef = this.modalService.open(ImportInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.importInfo = importInfo;
  }
}
