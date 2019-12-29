import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImportInfo } from 'app/shared/model/import-info.model';

@Component({
  selector: 'jhi-import-info-detail',
  templateUrl: './import-info-detail.component.html'
})
export class ImportInfoDetailComponent implements OnInit {
  importInfo: IImportInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ importInfo }) => {
      this.importInfo = importInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
