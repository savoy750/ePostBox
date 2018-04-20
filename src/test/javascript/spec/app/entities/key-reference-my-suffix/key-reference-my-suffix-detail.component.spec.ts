/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EPostBoxTestModule } from '../../../test.module';
import { KeyReferenceMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix-detail.component';
import { KeyReferenceMySuffixService } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix.service';
import { KeyReferenceMySuffix } from '../../../../../../main/webapp/app/entities/key-reference-my-suffix/key-reference-my-suffix.model';

describe('Component Tests', () => {

    describe('KeyReferenceMySuffix Management Detail Component', () => {
        let comp: KeyReferenceMySuffixDetailComponent;
        let fixture: ComponentFixture<KeyReferenceMySuffixDetailComponent>;
        let service: KeyReferenceMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [KeyReferenceMySuffixDetailComponent],
                providers: [
                    KeyReferenceMySuffixService
                ]
            })
            .overrideTemplate(KeyReferenceMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KeyReferenceMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KeyReferenceMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new KeyReferenceMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.keyReference).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
