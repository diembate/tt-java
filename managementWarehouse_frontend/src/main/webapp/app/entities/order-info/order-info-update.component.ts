import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IOrderInfo, OrderInfo } from 'app/shared/model/order-info.model';
import { OrderInfoService } from './order-info.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-order-info-update',
  templateUrl: './order-info-update.component.html'
})
export class OrderInfoUpdateComponent implements OnInit {
  isSaving = false;

  customers: ICustomer[] = [];
  orderDateDp: any;

  editForm = this.fb.group({
    id: [],
    amount: [],
    orderDate: [],
    customer: []
  });

  constructor(
    protected orderInfoService: OrderInfoService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderInfo }) => {
      this.updateForm(orderInfo);

      this.customerService
        .query()
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICustomer[]) => (this.customers = resBody));
    });
  }

  updateForm(orderInfo: IOrderInfo): void {
    this.editForm.patchValue({
      id: orderInfo.id,
      amount: orderInfo.amount,
      orderDate: orderInfo.orderDate,
      customer: orderInfo.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderInfo = this.createFromForm();
    if (orderInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.orderInfoService.update(orderInfo));
    } else {
      this.subscribeToSaveResponse(this.orderInfoService.create(orderInfo));
    }
  }

  private createFromForm(): IOrderInfo {
    return {
      ...new OrderInfo(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderInfo>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
