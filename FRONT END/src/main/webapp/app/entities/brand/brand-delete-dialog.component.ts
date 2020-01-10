import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBrand } from 'app/shared/model/brand.model';
import { BrandService } from './brand.service';

@Component({
  templateUrl: './brand-delete-dialog.component.html'
})
export class BrandDeleteDialogComponent {
  brand?: IBrand;

  constructor(protected brandService: BrandService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.brandService.delete(id).subscribe(() => {
      this.eventManager.broadcast('brandListModification');
      this.activeModal.close();
    });
  }
}
