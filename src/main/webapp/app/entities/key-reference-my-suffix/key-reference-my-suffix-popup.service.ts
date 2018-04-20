import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { KeyReferenceMySuffix } from './key-reference-my-suffix.model';
import { KeyReferenceMySuffixService } from './key-reference-my-suffix.service';

@Injectable()
export class KeyReferenceMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private keyReferenceService: KeyReferenceMySuffixService

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
                this.keyReferenceService.find(id)
                    .subscribe((keyReferenceResponse: HttpResponse<KeyReferenceMySuffix>) => {
                        const keyReference: KeyReferenceMySuffix = keyReferenceResponse.body;
                        this.ngbModalRef = this.keyReferenceModalRef(component, keyReference);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.keyReferenceModalRef(component, new KeyReferenceMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    keyReferenceModalRef(component: Component, keyReference: KeyReferenceMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.keyReference = keyReference;
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
