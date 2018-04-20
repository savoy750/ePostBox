import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DataReferenceMySuffix } from './data-reference-my-suffix.model';
import { DataReferenceMySuffixPopupService } from './data-reference-my-suffix-popup.service';
import { DataReferenceMySuffixService } from './data-reference-my-suffix.service';

@Component({
    selector: 'jhi-data-reference-my-suffix-dialog',
    templateUrl: './data-reference-my-suffix-dialog.component.html'
})
export class DataReferenceMySuffixDialogComponent implements OnInit {

    dataReference: DataReferenceMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataReferenceService: DataReferenceMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dataReference.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dataReferenceService.update(this.dataReference));
        } else {
            this.subscribeToSaveResponse(
                this.dataReferenceService.create(this.dataReference));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DataReferenceMySuffix>>) {
        result.subscribe((res: HttpResponse<DataReferenceMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DataReferenceMySuffix) {
        this.eventManager.broadcast({ name: 'dataReferenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-data-reference-my-suffix-popup',
    template: ''
})
export class DataReferenceMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataReferencePopupService: DataReferenceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dataReferencePopupService
                    .open(DataReferenceMySuffixDialogComponent as Component, params['id']);
            } else {
                this.dataReferencePopupService
                    .open(DataReferenceMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
