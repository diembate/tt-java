import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { ProductDeleteDialogComponent } from './product-delete-dialog.component';

import { ImportDetailInfoUpdateComponent } from './import/import-detail-info-update/import-detail-info-update.component';
import { OrderDetailInfoUpdateComponent } from './order/order-detail-info-update/order-detail-info-update.component';



@Component({
  selector: 'jhi-product',
  templateUrl: './product.component.html'
})
export class ProductComponent implements OnInit, OnDestroy {
  products?: IProduct[];
  eventSubscriber?: Subscription;

  constructor(
    protected productService: ProductService,
    protected eventManager: JhiEventManager, 
    protected modalService: NgbModal, 
   
    ) {

    }

  loadAll(): void {
    this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => {
      this.products = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProducts();
 
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProduct): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }
  

  registerChangeInProducts(): void {
    this.eventSubscriber = this.eventManager.subscribe('productListModification', () => this.loadAll());
  }

  delete(product: IProduct): void {
    const modalRef = this.modalService.open(ProductDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.product = product;
  }
 
 
  import(product: IProduct):void {
    const modalRef =this.modalService.open(ImportDetailInfoUpdateComponent);
    modalRef.componentInstance.id = product.id;
    // modalRef.componentInstance.passEntry.subscribe((receivedEntry: any) => {
    //   console.log(receivedEntry);
    //   })
 
  }
  order(product: IProduct):void  {
    const modalRef = this.modalService.open(OrderDetailInfoUpdateComponent);
    modalRef.componentInstance.id = product.id;
  }
}
