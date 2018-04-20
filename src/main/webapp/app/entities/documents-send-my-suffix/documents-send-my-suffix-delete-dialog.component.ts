import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DocumentsSendMySuffix } from './documents-send-my-suffix.model';
import { DocumentsSendMySuffixPopupService } from './documents-send-my-suffix-popup.service';
import { DocumentsSendMySuffixService } from './documents-send-my-suffix.service';

@Component({
    selector: 'jhi-documents-send-my-suffix-delete-dialog',
    templateUrl: './documents-send-my-suffix-delete-dialog.component.html'
})
export class DocumentsSendMySuffixDeleteDialogComponent {

    documentsSend: DocumentsSendMySuffix;

    constructor(
        private documentsSendService: DocumentsSendMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentsSendService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'documentsSendListModification',
                content: 'Deleted an documentsSend'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-documents-send-my-suffix-delete-popup',
    template: ''
})
export class DocumentsSendMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private documentsSendPopupService: DocumentsSendMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.documentsSendPopupService
                .open(DocumentsSendMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
