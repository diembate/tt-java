import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IImportDetailInfo, ImportDetailInfo } from 'app/shared/model/import-detail-info.model';
import { ImportDetailInfoService } from './import-detail-info.service';
import { IImportInfo } from 'app/shared/model/import-info.model';
import { ImportInfoService } from 'app/entities/import-info/import-info.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IImportInfo | IProduct;

@Component({
  selector: 'jhi-import-detail-info-update',
  templateUrl: './import-detail-info-update.component.html'
})
export class ImportDetailInfoUpdateComponent implements OnInit {
  isSaving = false;

  importinfos: IImportInfo[] = [];

  products: IProduct[] = [];
  importDateDp: any;

  editForm = this.fb.group({
    id: [],
    productName: [],
    quantityImport: [],
    importDate: [],
    priceImport: [],
    importInfo: [],
    product: []
  });

  constructor(
    protected importDetailInfoService: ImportDetailInfoService,
    protected importInfoService: ImportInfoService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ importDetailInfo }) => {
      this.updateForm(importDetailInfo);

      this.importInfoService
        .query()
        .pipe(
          map((res: HttpResponse<IImportInfo[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IImportInfo[]) => (this.importinfos = resBody));

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

  updateForm(importDetailInfo: IImportDetailInfo): void {
    this.editForm.patchValue({
      id: importDetailInfo.id,
      productName: importDetailInfo.productName,
      quantityImport: importDetailInfo.quantityImport,
      importDate: importDetailInfo.importDate,
      priceImport: importDetailInfo.priceImport,
      importInfo: importDetailInfo.importInfo,
      product: importDetailInfo.product
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const importDetailInfo = this.createFromForm();
    if (importDetailInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.importDetailInfoService.update(importDetailInfo));
    } else {
      this.subscribeToSaveResponse(this.importDetailInfoService.create(importDetailInfo));
    }
  }

  private createFromForm(): IImportDetailInfo {
    return {
      ...new ImportDetailInfo(),
      id: this.editForm.get(['id'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      quantityImport: this.editForm.get(['quantityImport'])!.value,
      importDate: this.editForm.get(['importDate'])!.value,
      priceImport: this.editForm.get(['priceImport'])!.value,
      importInfo: this.editForm.get(['importInfo'])!.value,
      product: this.editForm.get(['product'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImportDetailInfo>>): void {
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
