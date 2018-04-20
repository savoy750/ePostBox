import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DocumentsSendMySuffix } from './documents-send-my-suffix.model';
import { DocumentsSendMySuffixPopupService } from './documents-send-my-suffix-popup.service';
import { DocumentsSendMySuffixService } from './documents-send-my-suffix.service';
import { KeyReferenceMySuffix, KeyReferenceMySuffixService } from '../key-reference-my-suffix';

@Component({
    selector: 'jhi-documents-send-my-suffix-dialog',
    templateUrl: './documents-send-my-suffix-dialog.component.html'
})
export class DocumentsSendMySuffixDialogComponent implements OnInit {

    documentsSend: DocumentsSendMySuffix;
    isSaving: boolean;

    keyreferences: KeyReferenceMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private documentsSendService: DocumentsSendMySuffixService,
        private keyReferenceService: KeyReferenceMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.keyReferenceService.query()
            .subscribe((res: HttpResponse<KeyReferenceMySuffix[]>) => { this.keyreferences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.documentsSend.id !== undefined) {
            this.subscribeToSaveResponse(
                this.documentsSendService.update(this.documentsSend));
        } else {
            this.subscribeToSaveResponse(
                this.documentsSendService.create(this.documentsSend));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DocumentsSendMySuffix>>) {
        result.subscribe((res: HttpResponse<DocumentsSendMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DocumentsSendMySuffix) {
        this.eventManager.broadcast({ name: 'documentsSendListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackKeyReferenceById(index: number, item: KeyReferenceMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-documents-send-my-suffix-popup',
    template: ''
})
export class DocumentsSendMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentsSendPopupService: DocumentsSendMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.documentsSendPopupService
                    .open(DocumentsSendMySuffixDialogComponent as Component, params['id']);
            } else {
                this.documentsSendPopupService
                    .open(DocumentsSendMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
