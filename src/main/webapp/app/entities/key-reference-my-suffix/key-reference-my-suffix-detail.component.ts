import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { KeyReferenceMySuffix } from './key-reference-my-suffix.model';
import { KeyReferenceMySuffixService } from './key-reference-my-suffix.service';

@Component({
    selector: 'jhi-key-reference-my-suffix-detail',
    templateUrl: './key-reference-my-suffix-detail.component.html'
})
export class KeyReferenceMySuffixDetailComponent implements OnInit, OnDestroy {

    keyReference: KeyReferenceMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private keyReferenceService: KeyReferenceMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKeyReferences();
    }

    load(id) {
        this.keyReferenceService.find(id)
            .subscribe((keyReferenceResponse: HttpResponse<KeyReferenceMySuffix>) => {
                this.keyReference = keyReferenceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKeyReferences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'keyReferenceListModification',
            (response) => this.load(this.keyReference.id)
        );
    }
}
