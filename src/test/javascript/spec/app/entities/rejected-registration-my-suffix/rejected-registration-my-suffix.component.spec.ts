/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EPostBoxTestModule } from '../../../test.module';
import { RejectedRegistrationMySuffixComponent } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.component';
import { RejectedRegistrationMySuffixService } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.service';
import { RejectedRegistrationMySuffix } from '../../../../../../main/webapp/app/entities/rejected-registration-my-suffix/rejected-registration-my-suffix.model';

describe('Component Tests', () => {

    describe('RejectedRegistrationMySuffix Management Component', () => {
        let comp: RejectedRegistrationMySuffixComponent;
        let fixture: ComponentFixture<RejectedRegistrationMySuffixComponent>;
        let service: RejectedRegistrationMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EPostBoxTestModule],
                declarations: [RejectedRegistrationMySuffixComponent],
                providers: [
                    RejectedRegistrationMySuffixService
                ]
            })
            .overrideTemplate(RejectedRegistrationMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RejectedRegistrationMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RejectedRegistrationMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RejectedRegistrationMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.rejectedRegistrations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
