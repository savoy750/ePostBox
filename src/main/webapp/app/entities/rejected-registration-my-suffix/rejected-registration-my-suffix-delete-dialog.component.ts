import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RejectedRegistrationMySuffix } from './rejected-registration-my-suffix.model';
import { RejectedRegistrationMySuffixPopupService } from './rejected-registration-my-suffix-popup.service';
import { RejectedRegistrationMySuffixService } from './rejected-registration-my-suffix.service';

@Component({
    selector: 'jhi-rejected-registration-my-suffix-delete-dialog',
    templateUrl: './rejected-registration-my-suffix-delete-dialog.component.html'
})
export class RejectedRegistrationMySuffixDeleteDialogComponent {

    rejectedRegistration: RejectedRegistrationMySuffix;

    constructor(
        private rejectedRegistrationService: RejectedRegistrationMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rejectedRegistrationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rejectedRegistrationListModification',
                content: 'Deleted an rejectedRegistration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rejected-registration-my-suffix-delete-popup',
    template: ''
})
export class RejectedRegistrationMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rejectedRegistrationPopupService: RejectedRegistrationMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rejectedRegistrationPopupService
                .open(RejectedRegistrationMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
