/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EPostBoxTestModule } from '../../../test.module';
import { KeyReferenceMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix-delete-dialog.component';
import { KeyReferenceMySuffixService } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix.service';

describe('Component Tests', () => {

    describe('KeyReferenceMySuffix Management Delete Component', () => {
        let comp: KeyReferenceMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<KeyReferenceMySuffixDeleteDialogComponent>;
        let service: KeyReferenceMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [KeyReferenceMySuffixDeleteDialogComponent],
                providers: [
                    KeyReferenceMySuffixService
                ]
            })
            .overrideTemplate(KeyReferenceMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KeyReferenceMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeyReferenceMySuffixService);
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
