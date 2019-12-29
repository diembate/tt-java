import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IImportInfo, ImportInfo } from 'app/shared/model/import-info.model';
import { ImportInfoService } from './import-info.service';

@Component({
  selector: 'jhi-import-info-update',
  templateUrl: './import-info-update.component.html'
})
export class ImportInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    deliverPerson: [],
    supplier: [],
    cost: []
  });

  constructor(protected importInfoService: ImportInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ importInfo }) => {
      this.updateForm(importInfo);
    });
  }

  updateForm(importInfo: IImportInfo): void {
    this.editForm.patchValue({
      id: importInfo.id,
      deliverPerson: importInfo.deliverPerson,
      supplier: importInfo.supplier,
      cost: importInfo.cost
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const importInfo = this.createFromForm();
    if (importInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.importInfoService.update(importInfo));
    } else {
      this.subscribeToSaveResponse(this.importInfoService.create(importInfo));
    }
  }

  private createFromForm(): IImportInfo {
    return {
      ...new ImportInfo(),
      id: this.editForm.get(['id'])!.value,
      deliverPerson: this.editForm.get(['deliverPerson'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
      cost: this.editForm.get(['cost'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImportInfo>>): void {
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
