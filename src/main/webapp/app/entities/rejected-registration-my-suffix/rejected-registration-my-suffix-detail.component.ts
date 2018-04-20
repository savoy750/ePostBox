import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RejectedRegistrationMySuffix } from './rejected-registration-my-suffix.model';
import { RejectedRegistrationMySuffixService } from './rejected-registration-my-suffix.service';

@Component({
    selector: 'jhi-rejected-registration-my-suffix-detail',
    templateUrl: './rejected-registration-my-suffix-detail.component.html'
})
export class RejectedRegistrationMySuffixDetailComponent implements OnInit, OnDestroy {

    rejectedRegistration: RejectedRegistrationMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rejectedRegistrationService: RejectedRegistrationMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRejectedRegistrations();
    }

    load(id) {
        this.rejectedRegistrationService.find(id)
            .subscribe((rejectedRegistrationResponse: HttpResponse<RejectedRegistrationMySuffix>) => {
                this.rejectedRegistration = rejectedRegistrationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRejectedRegistrations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rejectedRegistrationListModification',
            (response) => this.load(this.rejectedRegistration.id)
        );
    }
}
