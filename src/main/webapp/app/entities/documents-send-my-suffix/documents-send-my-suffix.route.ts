import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DocumentsSendMySuffixComponent } from './documents-send-my-suffix.component';
import { DocumentsSendMySuffixDetailComponent } from './documents-send-my-suffix-detail.component';
import { DocumentsSendMySuffixPopupComponent } from './documents-send-my-suffix-dialog.component';
import { DocumentsSendMySuffixDeletePopupComponent } from './documents-send-my-suffix-delete-dialog.component';

export const documentsSendRoute: Routes = [
    {
        path: 'documents-send-my-suffix',
        component: DocumentsSendMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentsSends'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'documents-send-my-suffix/:id',
        component: DocumentsSendMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentsSends'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentsSendPopupRoute: Routes = [
    {
        path: 'documents-send-my-suffix-new',
        component: DocumentsSendMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentsSends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'documents-send-my-suffix/:id/edit',
        component: DocumentsSendMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentsSends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'documents-send-my-suffix/:id/delete',
        component: DocumentsSendMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentsSends'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
