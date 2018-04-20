import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DataReferenceMySuffix } from './data-reference-my-suffix.model';
import { DataReferenceMySuffixService } from './data-reference-my-suffix.service';

@Component({
    selector: 'jhi-data-reference-my-suffix-detail',
    templateUrl: './data-reference-my-suffix-detail.component.html'
})
export class DataReferenceMySuffixDetailComponent implements OnInit, OnDestroy {

    dataReference: DataReferenceMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataReferenceService: DataReferenceMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDataReferences();
    }

    load(id) {
        this.dataReferenceService.find(id)
            .subscribe((dataReferenceResponse: HttpResponse<DataReferenceMySuffix>) => {
                this.dataReference = dataReferenceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDataReferences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dataReferenceListModification',
            (response) => this.load(this.dataReference.id)
        );
    }
}
