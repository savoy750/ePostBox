/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EPostBoxTestModule } from '../../../test.module';
import { RejectedRegistrationMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix-detail.component';
import { RejectedRegistrationMySuffixService } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.service';
import { RejectedRegistrationMySuffix } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.model';

describe('Component Tests', () => {

    describe('RejectedRegistrationMySuffix Management Detail Component', () => {
        let comp: RejectedRegistrationMySuffixDetailComponent;
        let fixture: ComponentFixture<RejectedRegistrationMySuffixDetailComponent>;
        let service: RejectedRegistrationMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [RejectedRegistrationMySuffixDetailComponent],
                providers: [
                    RejectedRegistrationMySuffixService
                ]
            })
            .overrideTemplate(RejectedRegistrationMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RejectedRegistrationMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RejectedRegistrationMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RejectedRegistrationMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.rejectedRegistration).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
