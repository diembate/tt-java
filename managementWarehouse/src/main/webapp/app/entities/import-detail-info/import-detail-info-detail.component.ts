import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImportDetailInfo } from 'app/shared/model/import-detail-info.model';

@Component({
  selector: 'jhi-import-detail-info-detail',
  templateUrl: './import-detail-info-detail.component.html'
})
export class ImportDetailInfoDetailComponent implements OnInit {
  importDetailInfo: IImportDetailInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ importDetailInfo }) => {
      this.importDetailInfo = importDetailInfo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
