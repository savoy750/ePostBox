/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EPostBoxTestModule } from '../../../test.module';
import { RejectedRegistrationMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix-delete-dialog.component';
import { RejectedRegistrationMySuffixService } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.service';

describe('Component Tests', () => {

    describe('RejectedRegistrationMySuffix Management Delete Component', () => {
        let comp: RejectedRegistrationMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<RejectedRegistrationMySuffixDeleteDialogComponent>;
        let service: RejectedRegistrationMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [RejectedRegistrationMySuffixDeleteDialogComponent],
                providers: [
                    RejectedRegistrationMySuffixService
                ]
            })
            .overrideTemplate(RejectedRegistrationMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RejectedRegistrationMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RejectedRegistrationMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
