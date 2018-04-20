/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EPostBoxTestModule } from '../../../test.module';
import { DocumentsSendMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix-delete-dialog.component';
import { DocumentsSendMySuffixService } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.service';

describe('Component Tests', () => {

    describe('DocumentsSendMySuffix Management Delete Component', () => {
        let comp: DocumentsSendMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<DocumentsSendMySuffixDeleteDialogComponent>;
        let service: DocumentsSendMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [DocumentsSendMySuffixDeleteDialogComponent],
                providers: [
                    DocumentsSendMySuffixService
                ]
            })
            .overrideTemplate(DocumentsSendMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentsSendMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentsSendMySuffixService);
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
