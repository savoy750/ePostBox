import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { RejectedRegistrationMySuffixComponent } from './rejected-registration-my-suffix.component';
import { RejectedRegistrationMySuffixDetailComponent } from './rejected-registration-my-suffix-detail.component';
import { RejectedRegistrationMySuffixPopupComponent } from './rejected-registration-my-suffix-dialog.component';
import {
    RejectedRegistrationMySuffixDeletePopupComponent
} from './rejected-registration-my-suffix-delete-dialog.component';

@Injectable()
export class RejectedRegistrationMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const rejectedRegistrationRoute: Routes = [
    {
        path: 'rejected-registration-my-suffix',
        component: RejectedRegistrationMySuffixComponent,
        resolve: {
            'pagingParams': RejectedRegistrationMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RejectedRegistrations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rejected-registration-my-suffix/:id',
        component: RejectedRegistrationMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RejectedRegistrations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rejectedRegistrationPopupRoute: Routes = [
    {
        path: 'rejected-registration-my-suffix-new',
        component: RejectedRegistrationMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RejectedRegistrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rejected-registration-my-suffix/:id/edit',
        component: RejectedRegistrationMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RejectedRegistrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rejected-registration-my-suffix/:id/delete',
        component: RejectedRegistrationMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RejectedRegistrations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
