/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EPostBoxTestModule } from '../../../test.module';
import { KeyReferenceMySuffixComponent } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix.component';
import { KeyReferenceMySuffixService } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix.service';
import { KeyReferenceMySuffix } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix.model';

describe('Component Tests', () => {

    describe('KeyReferenceMySuffix Management Component', () => {
        let comp: KeyReferenceMySuffixComponent;
        let fixture: ComponentFixture<KeyReferenceMySuffixComponent>;
        let service: KeyReferenceMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [KeyReferenceMySuffixComponent],
                providers: [
                    KeyReferenceMySuffixService
                ]
            })
            .overrideTemplate(KeyReferenceMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KeyReferenceMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeyReferenceMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new KeyReferenceMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.keyReferences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
