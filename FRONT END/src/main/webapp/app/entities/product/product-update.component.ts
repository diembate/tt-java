import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { IBrand } from 'app/shared/model/brand.model';
import { BrandService } from 'app/entities/brand/brand.service';
import { IReport } from 'app/shared/model/report.model';
import { ReportService } from 'app/entities/report/report.service';

type SelectableEntity = ICategory | IBrand | IReport;

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;

  categories: ICategory[] = [];

  brands: IBrand[] = [];

  reports: IReport[] = [];

  editForm = this.fb.group({
    id: [],
    productName: [],
    priceProduct: ["", Validators.required],
    quantityProduct: [],
    categoryId: [],
    brandId: [],
    reportId: []
  });

  constructor(
    protected productService: ProductService,
    protected categoryService: CategoryService,
    protected brandService: BrandService,
    protected reportService: ReportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.categoryService
        .query()
        .pipe(
          map((res: HttpResponse<ICategory[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICategory[]) => (this.categories = resBody));

      this.brandService
        .query()
        .pipe(
          map((res: HttpResponse<IBrand[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBrand[]) => (this.brands = resBody));

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

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      productName: product.productName,
      priceProduct: product.priceProduct,
      quantityProduct: product.quantityProduct,
      categoryId: product.categoryId,
      brandId: product.brandId,
      reportId: product.reportId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      priceProduct: this.editForm.get(['priceProduct'])!.value,
      quantityProduct: this.editForm.get(['quantityProduct'])!.value,
      categoryId: this.editForm.get(['categoryId'])!.value,
      brandId: this.editForm.get(['brandId'])!.value,
      reportId: this.editForm.get(['reportId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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
