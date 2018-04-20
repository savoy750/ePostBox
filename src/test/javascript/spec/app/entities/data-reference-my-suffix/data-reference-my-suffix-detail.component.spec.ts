/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EPostBoxTestModule } from '../../../test.module';
import { DataReferenceMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/data-reference-my-suffix/data-reference-my-suffix-detail.component';
import { DataReferenceMySuffixService } from '../../../../../../main/webapp/app/entities/data-reference-my-suffix/data-reference-my-suffix.service';
import { DataReferenceMySuffix } from '../../../../../../main/webapp/app/entities/data-reference-my-suffix/data-reference-my-suffix.model';

describe('Component Tests', () => {

    describe('DataReferenceMySuffix Management Detail Component', () => {
        let comp: DataReferenceMySuffixDetailComponent;
        let fixture: ComponentFixture<DataReferenceMySuffixDetailComponent>;
        let service: DataReferenceMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [DataReferenceMySuffixDetailComponent],
                providers: [
                    DataReferenceMySuffixService
                ]
            })
            .overrideTemplate(DataReferenceMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DataReferenceMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataReferenceMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DataReferenceMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dataReference).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
