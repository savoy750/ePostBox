/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EPostBoxTestModule } from '../../../test.module';
import { DocumentsSendMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix-dialog.component';
import { DocumentsSendMySuffixService } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.service';
import { DocumentsSendMySuffix } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.model';
import { KeyReferenceMySuffixService } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix';

describe('Component Tests', () => {

    describe('DocumentsSendMySuffix Management Dialog Component', () => {
        let comp: DocumentsSendMySuffixDialogComponent;
        let fixture: ComponentFixture<DocumentsSendMySuffixDialogComponent>;
        let service: DocumentsSendMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [DocumentsSendMySuffixDialogComponent],
                providers: [
                    KeyReferenceMySuffixService,
                    DocumentsSendMySuffixService
                ]
            })
            .overrideTemplate(DocumentsSendMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentsSendMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentsSendMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DocumentsSendMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.documentsSend = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'documentsSendListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DocumentsSendMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.documentsSend = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'documentsSendListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
