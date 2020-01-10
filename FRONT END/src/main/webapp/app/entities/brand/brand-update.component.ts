import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBrand, Brand } from 'app/shared/model/brand.model';
import { BrandService } from './brand.service';

@Component({
  selector: 'jhi-brand-update',
  templateUrl: './brand-update.component.html'
})
export class BrandUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    brandName: []
  });

  constructor(protected brandService: BrandService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ brand }) => {
      this.updateForm(brand);
    });
  }

  updateForm(brand: IBrand): void {
    this.editForm.patchValue({
      id: brand.id,
      brandName: brand.brandName
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const brand = this.createFromForm();
    if (brand.id !== undefined) {
      this.subscribeToSaveResponse(this.brandService.update(brand));
    } else {
      this.subscribeToSaveResponse(this.brandService.create(brand));
    }
  }

  private createFromForm(): IBrand {
    return {
      ...new Brand(),
      id: this.editForm.get(['id'])!.value,
      brandName: this.editForm.get(['brandName'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBrand>>): void {
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
}
