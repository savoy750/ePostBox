import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { RejectedRegistrationMySuffix } from './rejected-registration-my-suffix.model';
import { RejectedRegistrationMySuffixService } from './rejected-registration-my-suffix.service';

@Injectable()
export class RejectedRegistrationMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rejectedRegistrationService: RejectedRegistrationMySuffixService

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
                this.rejectedRegistrationService.find(id)
                    .subscribe((rejectedRegistrationResponse: HttpResponse<RejectedRegistrationMySuffix>) => {
                        const rejectedRegistration: RejectedRegistrationMySuffix = rejectedRegistrationResponse.body;
                        rejectedRegistration.dateDeNaissance = this.datePipe
                            .transform(rejectedRegistration.dateDeNaissance, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.rejectedRegistrationModalRef(component, rejectedRegistration);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.rejectedRegistrationModalRef(component, new RejectedRegistrationMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    rejectedRegistrationModalRef(component: Component, rejectedRegistration: RejectedRegistrationMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rejectedRegistration = rejectedRegistration;
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
