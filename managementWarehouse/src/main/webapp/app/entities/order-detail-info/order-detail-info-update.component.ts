import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IOrderDetailInfo, OrderDetailInfo } from 'app/shared/model/order-detail-info.model';
import { OrderDetailInfoService } from './order-detail-info.service';
import { IOrderInfo } from 'app/shared/model/order-info.model';
import { OrderInfoService } from 'app/entities/order-info/order-info.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IOrderInfo | IProduct;

@Component({
  selector: 'jhi-order-detail-info-update',
  templateUrl: './order-detail-info-update.component.html'
})
export class OrderDetailInfoUpdateComponent implements OnInit {
  isSaving = false;

  orderinfos: IOrderInfo[] = [];

  products: IProduct[] = [];
  orderDateDp: any;

  editForm = this.fb.group({
    id: [],
    productName: [],
    priceProduct: [],
    quantityOrder: [],
    amount: [],
    orderDate: [],
    orderInfo: [],
    product: []
  });

  constructor(
    protected orderDetailInfoService: OrderDetailInfoService,
    protected orderInfoService: OrderInfoService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderDetailInfo }) => {
      this.updateForm(orderDetailInfo);

      this.orderInfoService
        .query()
        .pipe(
          map((res: HttpResponse<IOrderInfo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOrderInfo[]) => (this.orderinfos = resBody));

      this.productService
        .query()
        .pipe(
          map((res: HttpResponse<IProduct[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProduct[]) => (this.products = resBody));
    });
  }

  updateForm(orderDetailInfo: IOrderDetailInfo): void {
    this.editForm.patchValue({
      id: orderDetailInfo.id,
      productName: orderDetailInfo.productName,
      priceProduct: orderDetailInfo.priceProduct,
      quantityOrder: orderDetailInfo.quantityOrder,
      amount: orderDetailInfo.amount,
      orderDate: orderDetailInfo.orderDate,
      orderInfo: orderDetailInfo.orderInfo,
      product: orderDetailInfo.product
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderDetailInfo = this.createFromForm();
    if (orderDetailInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.orderDetailInfoService.update(orderDetailInfo));
    } else {
      this.subscribeToSaveResponse(this.orderDetailInfoService.create(orderDetailInfo));
    }
  }

  private createFromForm(): IOrderDetailInfo {
    return {
      ...new OrderDetailInfo(),
      id: this.editForm.get(['id'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      priceProduct: this.editForm.get(['priceProduct'])!.value,
      quantityOrder: this.editForm.get(['quantityOrder'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value,
      orderInfo: this.editForm.get(['orderInfo'])!.value,
      product: this.editForm.get(['product'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderDetailInfo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
