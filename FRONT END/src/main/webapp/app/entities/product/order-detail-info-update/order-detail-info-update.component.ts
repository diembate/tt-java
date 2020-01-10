import { Component, OnInit, Input } from '@angular/core';
import { IOrderInfo } from 'app/shared/model/order-info.model';
import { IProduct } from 'app/shared/model/product.model';
import { OrderDetailInfoService } from 'app/entities/order-detail-info/order-detail-info.service';
import { OrderInfoService } from 'app/entities/order-info/order-info.service';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IOrderDetailInfo, OrderDetailInfo } from 'app/shared/model/order-detail-info.model';
import { Observable } from 'rxjs';
import { IReport } from 'app/shared/model/report.model';
import { ReportService } from 'app/entities/report/report.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
type SelectableEntity = IOrderInfo | IProduct;
@Component({
  selector: 'jhi-order-detail-info-update',
  templateUrl: './order-detail-info-update.component.html',
  styleUrls: ['./order-detail-info-update.component.scss']
})
export class OrderDetailInfoUpdateComponent implements OnInit {
  [x: string]: any;


  isSaving = false;

  orderinfos: IOrderInfo[] = [];

  products: IProduct[] = [];

  reports: IReport[] = [];
  orderDateDp: any;

  productId: any;



  editForm = this.fb.group({
    id: [],
    productName: [],
    priceProduct: [],
    quantityOrder: [],
    amount: [],
    orderDate: [],
    orderInfoId: [],
    productId: [],
    reportId: []
  });

  @Input() public id: any;


  constructor(
    protected orderDetailInfoService: OrderDetailInfoService,
    protected orderInfoService: OrderInfoService,
    protected productService: ProductService,
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderDetailInfo }) => {

      this.productId = this.id;
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

      this.reportService
        .query()
        .pipe(
          map((res: HttpResponse<IReport[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IReport[]) => (this.reports = resBody));
    });
  }




  save(): void {
    this.isSaving = true;
    const orderDetailInfo = this.createFromForm();
    this.subscribeToSaveResponse(this.orderDetailInfoService.create(orderDetailInfo));

    this.activeModal.close();
    this.router.navigate(['/product']);

  }





  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderDetailInfo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }


  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  previousState(): void {
    window.history.back();
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
      orderInfoId: this.editForm.get(['orderInfoId'])!.value,
      reportId: this.editForm.get(['reportId'])!.value,
      productId: this.editForm.get(['productId'])!.value || this.productId
    };
  }






}

