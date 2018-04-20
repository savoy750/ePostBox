import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { KeyReferenceMySuffix } from './key-reference-my-suffix.model';
import { KeyReferenceMySuffixPopupService } from './key-reference-my-suffix-popup.service';
import { KeyReferenceMySuffixService } from './key-reference-my-suffix.service';

@Component({
    selector: 'jhi-key-reference-my-suffix-delete-dialog',
    templateUrl: './key-reference-my-suffix-delete-dialog.component.html'
})
export class KeyReferenceMySuffixDeleteDialogComponent {

    keyReference: KeyReferenceMySuffix;

    constructor(
        private keyReferenceService: KeyReferenceMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.keyReferenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'keyReferenceListModification',
                content: 'Deleted an keyReference'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-key-reference-my-suffix-delete-popup',
    template: ''
})
export class KeyReferenceMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private keyReferencePopupService: KeyReferenceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.keyReferencePopupService
                .open(KeyReferenceMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
