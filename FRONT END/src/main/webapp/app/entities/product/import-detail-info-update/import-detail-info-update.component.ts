import { Component, OnInit, Input } from '@angular/core';
import { IImportInfo } from 'app/shared/model/import-info.model';
import { IProduct } from 'app/shared/model/product.model';
import { ImportDetailInfoService } from 'app/entities/import-detail-info/import-detail-info.service';
import { ImportInfoService } from 'app/entities/import-info/import-info.service';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IImportDetailInfo, ImportDetailInfo } from 'app/shared/model/import-detail-info.model';
import { Observable } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
type SelectableEntity = IImportInfo | IProduct;
@Component({
  selector: 'jhi-import-detail-info-update',
  templateUrl: './import-detail-info-update.component.html',
  styleUrls: ['./import-detail-info-update.component.scss']
})
export class ImportDetailInfoUpdateComponent implements OnInit {
  @Input() public id: any
  [x: string]: any;
  
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
    importInfoId: [],
    productId: []
  });
  @Input() public : any
  constructor(
    protected importDetailInfoService: ImportDetailInfoService,
    protected importInfoService: ImportInfoService,
    protected productService: ProductService,
    public activeModal: NgbActiveModal,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}


    ngOnInit(): void {
      this.productId = this.id;
      this.activatedRoute.data.subscribe(({ importDetailInfo }) => {
    
  
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
  
 
    


  private createFromForm(): IImportDetailInfo {
    return {
      ...new ImportDetailInfo(),
      id: this.editForm.get(['id'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      quantityImport: this.editForm.get(['quantityImport'])!.value,
      importDate: this.editForm.get(['importDate'])!.value,
      priceImport: this.editForm.get(['priceImport'])!.value,
      importInfoId: this.editForm.get(['importInfoId'])!.value,
      productId: this.editForm.get(['productId'])!.value|| this.productId
    };
  }
  save(): void {
    this.isSaving = true;
    const importDetailInfo = this.createFromForm();
    this.subscribeToSaveResponse(this.importDetailInfoService.create(importDetailInfo));

    this.activeModal.close();
    this.router.navigate(['/product']);
  
  }

 


  previousState(): void {
    window.history.back();
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