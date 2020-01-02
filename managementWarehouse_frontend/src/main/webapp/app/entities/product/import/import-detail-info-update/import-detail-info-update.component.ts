import { Component, OnInit, Input } from '@angular/core';




import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IImportDetailInfo, ImportDetailInfo } from 'app/shared/model/import-detail-info.model';

import { IImportInfo } from 'app/shared/model/import-info.model';
import { ImportInfoService } from 'app/entities/import-info/import-info.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { ImportDetailInfoService } from 'app/entities/import-detail-info/import-detail-info.service';

type SelectableEntity = IImportInfo | IProduct;

@Component({
  selector: 'jhi-import-detail-info-update',
  templateUrl: './import-detail-info-update.component.html',
  styleUrls: ['./import-detail-info-update.component.scss']
})
export class ImportDetailInfoUpdateComponent implements OnInit {
  [x: string]: any;
  

  isSaving = false;

  importinfos: IImportInfo[] = [];

  products: IProduct[] = [];
  importDateDp: any;
  productId: any;

  editForm = this.fb.group({
    id: [],
    productName: [],
    quantityImport: [],
    importDate: [],
    priceImport: [],
    importInfo: [],
    product: []
  });
  @Input() public id: any
  constructor(
    protected importDetailInfoService: ImportDetailInfoService,
    protected importInfoService: ImportInfoService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
  ) { 

  }

  ngOnInit(): void {
    this.productId = this.id;
    // this.editForm.patchValue( {id : this.id});
    this.activatedRoute.data.subscribe(({ importDetailInfo }) => {
      // console.log(this.id);
     

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
 

  save(): void {
    this.isSaving = true;
    const importDetailInfo = this.createFromForm();
  //  console.log( importDetailInfo);
    this.subscribeToSaveResponse(this.importDetailInfoService.create(importDetailInfo));
 
  }


  previousState(): void {
    window.history.back();
  }
  


  


  private createFromForm(): IImportDetailInfo {
    return {
      ...new ImportDetailInfo(),
      id: this.editForm.get(['id'])!.value ,
      productName: this.editForm.get(['productName'])!.value,
      quantityImport: this.editForm.get(['quantityImport'])!.value,
      importDate: this.editForm.get(['importDate'])!.value,
      priceImport: this.editForm.get(['priceImport'])!.value,
      importInfo: this.editForm.get(['product'])!.value,
      productId: this.editForm.get(['product'])!.value|| this.productId
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
  };

}

