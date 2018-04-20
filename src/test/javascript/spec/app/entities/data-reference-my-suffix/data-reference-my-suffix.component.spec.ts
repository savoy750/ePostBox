/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EPostBoxTestModule } from '../../../test.module';
import { DataReferenceMySuffixComponent } from '../../../../../../main/webapp/app/entities/data-reference-my-suffix/data-reference-my-suffix.component';
import { DataReferenceMySuffixService } from '../../../../../../main/webapp/app/entities/data-reference-my-suffix/data-reference-my-suffix.service';
import { DataReferenceMySuffix } from '../../../../../../main/webapp/app/entities/data-reference-my-suffix/data-reference-my-suffix.model';

describe('Component Tests', () => {

    describe('DataReferenceMySuffix Management Component', () => {
        let comp: DataReferenceMySuffixComponent;
        let fixture: ComponentFixture<DataReferenceMySuffixComponent>;
        let service: DataReferenceMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [DataReferenceMySuffixComponent],
                providers: [
                    DataReferenceMySuffixService
                ]
            })
            .overrideTemplate(DataReferenceMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataReferenceMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataReferenceMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DataReferenceMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dataReferences[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
