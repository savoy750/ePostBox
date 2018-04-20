import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DocumentsSendMySuffix } from './documents-send-my-suffix.model';
import { DocumentsSendMySuffixService } from './documents-send-my-suffix.service';

@Component({
    selector: 'jhi-documents-send-my-suffix-detail',
    templateUrl: './documents-send-my-suffix-detail.component.html'
})
export class DocumentsSendMySuffixDetailComponent implements OnInit, OnDestroy {

    documentsSend: DocumentsSendMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private documentsSendService: DocumentsSendMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocumentsSends();
    }

    load(id) {
        this.documentsSendService.find(id)
            .subscribe((documentsSendResponse: HttpResponse<DocumentsSendMySuffix>) => {
                this.documentsSend = documentsSendResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocumentsSends() {
        this.eventSubscriber = this.eventManager.subscribe(
            'documentsSendListModification',
            (response) => this.load(this.documentsSend.id)
        );
    }
}
