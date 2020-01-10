import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBrand } from 'app/shared/model/brand.model';
import { BrandService } from './brand.service';
import { BrandDeleteDialogComponent } from './brand-delete-dialog.component';

@Component({
  selector: 'jhi-brand',
  templateUrl: './brand.component.html'
})
export class BrandComponent implements OnInit, OnDestroy {
  brands?: IBrand[];
  eventSubscriber?: Subscription;

  constructor(protected brandService: BrandService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.brandService.query().subscribe((res: HttpResponse<IBrand[]>) => {
      this.brands = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBrands();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBrand): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBrands(): void {
    this.eventSubscriber = this.eventManager.subscribe('brandListModification', () => this.loadAll());
  }

  delete(brand: IBrand): void {
    const modalRef = this.modalService.open(BrandDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.brand = brand;
  }
}
