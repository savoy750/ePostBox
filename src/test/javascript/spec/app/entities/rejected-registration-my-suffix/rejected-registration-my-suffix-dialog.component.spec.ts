/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EPostBoxTestModule } from '../../../test.module';
import { RejectedRegistrationMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix-dialog.component';
import { RejectedRegistrationMySuffixService } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.service';
import { RejectedRegistrationMySuffix } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.model';

describe('Component Tests', () => {

    describe('RejectedRegistrationMySuffix Management Dialog Component', () => {
        let comp: RejectedRegistrationMySuffixDialogComponent;
        let fixture: ComponentFixture<RejectedRegistrationMySuffixDialogComponent>;
        let service: RejectedRegistrationMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [RejectedRegistrationMySuffixDialogComponent],
                providers: [
                    RejectedRegistrationMySuffixService
                ]
            })
            .overrideTemplate(RejectedRegistrationMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RejectedRegistrationMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RejectedRegistrationMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RejectedRegistrationMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.rejectedRegistration = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'rejectedRegistrationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RejectedRegistrationMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.rejectedRegistration = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'rejectedRegistrationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
