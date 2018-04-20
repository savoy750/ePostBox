/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EPostBoxTestModule } from '../../../test.module';
import { DocumentsSendMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix-detail.component';
import { DocumentsSendMySuffixService } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.service';
import { DocumentsSendMySuffix } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.model';

describe('Component Tests', () => {

    describe('DocumentsSendMySuffix Management Detail Component', () => {
        let comp: DocumentsSendMySuffixDetailComponent;
        let fixture: ComponentFixture<DocumentsSendMySuffixDetailComponent>;
        let service: DocumentsSendMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [DocumentsSendMySuffixDetailComponent],
                providers: [
                    DocumentsSendMySuffixService
                ]
            })
            .overrideTemplate(DocumentsSendMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentsSendMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentsSendMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DocumentsSendMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.documentsSend).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
