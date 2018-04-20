import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { DataReferenceMySuffix } from './data-reference-my-suffix.model';
import { DataReferenceMySuffixService } from './data-reference-my-suffix.service';

@Injectable()
export class DataReferenceMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private dataReferenceService: DataReferenceMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.dataReferenceService.find(id)
                    .subscribe((dataReferenceResponse: HttpResponse<DataReferenceMySuffix>) => {
                        const dataReference: DataReferenceMySuffix = dataReferenceResponse.body;
                        dataReference.dateDeNaissance = this.datePipe
                            .transform(dataReference.dateDeNaissance, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.dataReferenceModalRef(component, dataReference);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dataReferenceModalRef(component, new DataReferenceMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dataReferenceModalRef(component: Component, dataReference: DataReferenceMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dataReference = dataReference;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
