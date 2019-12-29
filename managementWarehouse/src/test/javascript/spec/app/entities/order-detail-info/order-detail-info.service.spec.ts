import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { OrderDetailInfoService } from 'app/entities/order-detail-info/order-detail-info.service';
import { IOrderDetailInfo, OrderDetailInfo } from 'app/shared/model/order-detail-info.model';

describe('Service Tests', () => {
  describe('OrderDetailInfo Service', () => {
    let injector: TestBed;
    let service: OrderDetailInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderDetailInfo;
    let expectedResult: IOrderDetailInfo | IOrderDetailInfo[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OrderDetailInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OrderDetailInfo(0, 'AAAAAAA', 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            orderDate: currentDate.format(DATE_FORMAT)
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

      it('should create a OrderDetailInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            orderDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new OrderDetailInfo())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OrderDetailInfo', () => {
        const returnedFromService = Object.assign(
          {
            productName: 'BBBBBB',
            priceProduct: 1,
            quantityOrder: 1,
            amount: 1,
            orderDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            orderDate: currentDate
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

      it('should return a list of OrderDetailInfo', () => {
        const returnedFromService = Object.assign(
          {
            productName: 'BBBBBB',
            priceProduct: 1,
            quantityOrder: 1,
            amount: 1,
            orderDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            orderDate: currentDate
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

      it('should delete a OrderDetailInfo', () => {
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
