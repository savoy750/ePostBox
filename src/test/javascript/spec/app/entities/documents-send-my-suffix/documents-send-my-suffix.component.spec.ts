/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EPostBoxTestModule } from '../../../test.module';
import { DocumentsSendMySuffixComponent } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.component';
import { DocumentsSendMySuffixService } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.service';
import { DocumentsSendMySuffix } from '../../../../../../main/webapp/app/entities/documents-send-my-suffix/documents-send-my-suffix.model';

describe('Component Tests', () => {

    describe('DocumentsSendMySuffix Management Component', () => {
        let comp: DocumentsSendMySuffixComponent;
        let fixture: ComponentFixture<DocumentsSendMySuffixComponent>;
        let service: DocumentsSendMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [DocumentsSendMySuffixComponent],
                providers: [
                    DocumentsSendMySuffixService
                ]
            })
            .overrideTemplate(DocumentsSendMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentsSendMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentsSendMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DocumentsSendMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.documentsSends[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
