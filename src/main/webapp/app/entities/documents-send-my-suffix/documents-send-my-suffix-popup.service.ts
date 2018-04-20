import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DocumentsSendMySuffix } from './documents-send-my-suffix.model';
import { DocumentsSendMySuffixService } from './documents-send-my-suffix.service';

@Injectable()
export class DocumentsSendMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private documentsSendService: DocumentsSendMySuffixService

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
                this.documentsSendService.find(id)
                    .subscribe((documentsSendResponse: HttpResponse<DocumentsSendMySuffix>) => {
                        const documentsSend: DocumentsSendMySuffix = documentsSendResponse.body;
                        this.ngbModalRef = this.documentsSendModalRef(component, documentsSend);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.documentsSendModalRef(component, new DocumentsSendMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    documentsSendModalRef(component: Component, documentsSend: DocumentsSendMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.documentsSend = documentsSend;
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
