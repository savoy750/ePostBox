import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DataReferenceMySuffix } from './data-reference-my-suffix.model';
import { DataReferenceMySuffixPopupService } from './data-reference-my-suffix-popup.service';
import { DataReferenceMySuffixService } from './data-reference-my-suffix.service';

@Component({
    selector: 'jhi-data-reference-my-suffix-delete-dialog',
    templateUrl: './data-reference-my-suffix-delete-dialog.component.html'
})
export class DataReferenceMySuffixDeleteDialogComponent {

    dataReference: DataReferenceMySuffix;

    constructor(
        private dataReferenceService: DataReferenceMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataReferenceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dataReferenceListModification',
                content: 'Deleted an dataReference'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-data-reference-my-suffix-delete-popup',
    template: ''
})
export class DataReferenceMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataReferencePopupService: DataReferenceMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dataReferencePopupService
                .open(DataReferenceMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
