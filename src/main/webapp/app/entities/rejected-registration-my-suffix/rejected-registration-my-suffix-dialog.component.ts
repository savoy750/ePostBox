import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RejectedRegistrationMySuffix } from './rejected-registration-my-suffix.model';
import { RejectedRegistrationMySuffixPopupService } from './rejected-registration-my-suffix-popup.service';
import { RejectedRegistrationMySuffixService } from './rejected-registration-my-suffix.service';

@Component({
    selector: 'jhi-rejected-registration-my-suffix-dialog',
    templateUrl: './rejected-registration-my-suffix-dialog.component.html'
})
export class RejectedRegistrationMySuffixDialogComponent implements OnInit {

    rejectedRegistration: RejectedRegistrationMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private rejectedRegistrationService: RejectedRegistrationMySuffixService,
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
        if (this.rejectedRegistration.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rejectedRegistrationService.update(this.rejectedRegistration));
        } else {
            this.subscribeToSaveResponse(
                this.rejectedRegistrationService.create(this.rejectedRegistration));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RejectedRegistrationMySuffix>>) {
        result.subscribe((res: HttpResponse<RejectedRegistrationMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RejectedRegistrationMySuffix) {
        this.eventManager.broadcast({ name: 'rejectedRegistrationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-rejected-registration-my-suffix-popup',
    template: ''
})
export class RejectedRegistrationMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rejectedRegistrationPopupService: RejectedRegistrationMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rejectedRegistrationPopupService
                    .open(RejectedRegistrationMySuffixDialogComponent as Component, params['id']);
            } else {
                this.rejectedRegistrationPopupService
                    .open(RejectedRegistrationMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
