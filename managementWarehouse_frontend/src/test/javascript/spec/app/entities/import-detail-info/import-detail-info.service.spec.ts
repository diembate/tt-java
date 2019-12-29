import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ImportDetailInfoService } from 'app/entities/import-detail-info/import-detail-info.service';
import { IImportDetailInfo, ImportDetailInfo } from 'app/shared/model/import-detail-info.model';

describe('Service Tests', () => {
  describe('ImportDetailInfo Service', () => {
    let injector: TestBed;
    let service: ImportDetailInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IImportDetailInfo;
    let expectedResult: IImportDetailInfo | IImportDetailInfo[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ImportDetailInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ImportDetailInfo(0, 'AAAAAAA', 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            importDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ImportDetailInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            importDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            importDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ImportDetailInfo())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ImportDetailInfo', () => {
        const returnedFromService = Object.assign(
          {
            productName: 'BBBBBB',
            quantityImport: 1,
            importDate: currentDate.format(DATE_FORMAT),
            priceImport: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            importDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ImportDetailInfo', () => {
        const returnedFromService = Object.assign(
          {
            productName: 'BBBBBB',
            quantityImport: 1,
            importDate: currentDate.format(DATE_FORMAT),
            priceImport: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            importDate: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ImportDetailInfo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
