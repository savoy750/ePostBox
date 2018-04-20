import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { KeyReferenceMySuffix } from './key-reference-my-suffix.model';
import { KeyReferenceMySuffixPopupService } from './key-reference-my-suffix-popup.service';
import { KeyReferenceMySuffixService } from './key-reference-my-suffix.service';
import { DataReferenceMySuffix, DataReferenceMySuffixService } from '../data-reference-my-suffix';

@Component({
    selector: 'jhi-key-reference-my-suffix-dialog',
    templateUrl: './key-reference-my-suffix-dialog.component.html'
})
export class KeyReferenceMySuffixDialogComponent implements OnInit {

    keyReference: KeyReferenceMySuffix;
    isSaving: boolean;

    internalkeys: DataReferenceMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private keyReferenceService: KeyReferenceMySuffixService,
        private dataReferenceService: DataReferenceMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dataReferenceService
            .query({filter: 'keyreference-is-null'})
            .subscribe((res: HttpResponse<DataReferenceMySuffix[]>) => {
                if (!this.keyReference.internalKeyId) {
                    this.internalkeys = res.body;
                } else {
                    this.dataReferenceService
                        .find(this.keyReference.internalKeyId)
                        .subscribe((subRes: HttpResponse<DataReferenceMySuffix>) => {
                            this.internalkeys = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.keyReference.id !== undefined) {
            this.subscribeToSaveResponse(
                this.keyReferenceService.update(this.keyReference));
        } else {
            this.subscribeToSaveResponse(
                this.keyReferenceService.create(this.keyReference));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<KeyReferenceMySuffix>>) {
        result.subscribe((res: HttpResponse<KeyReferenceMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: KeyReferenceMySuffix) {
        this.eventManager.broadcast({ name: 'keyReferenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDataReferenceById(index: number, item: DataReferenceMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-key-reference-my-suffix-popup',
    template: ''
})
export class KeyReferenceMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private keyReferencePopupService: KeyReferenceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.keyReferencePopupService
                    .open(KeyReferenceMySuffixDialogComponent as Component, params['id']);
            } else {
                this.keyReferencePopupService
                    .open(KeyReferenceMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
