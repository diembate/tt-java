import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ManagementWarehouseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { OrderDetailInfoDeleteDialogComponent } from 'app/entities/order-detail-info/order-detail-info-delete-dialog.component';
import { OrderDetailInfoService } from 'app/entities/order-detail-info/order-detail-info.service';

describe('Component Tests', () => {
  describe('OrderDetailInfo Management Delete Component', () => {
    let comp: OrderDetailInfoDeleteDialogComponent;
    let fixture: ComponentFixture<OrderDetailInfoDeleteDialogComponent>;
    let service: OrderDetailInfoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ManagementWarehouseTestModule],
        declarations: [OrderDetailInfoDeleteDialogComponent]
      })
        .overrideTemplate(OrderDetailInfoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderDetailInfoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderDetailInfoService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
