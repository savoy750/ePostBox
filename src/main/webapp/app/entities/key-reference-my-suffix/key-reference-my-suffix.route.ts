import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { KeyReferenceMySuffixComponent } from './key-reference-my-suffix.component';
import { KeyReferenceMySuffixDetailComponent } from './key-reference-my-suffix-detail.component';
import { KeyReferenceMySuffixPopupComponent } from './key-reference-my-suffix-dialog.component';
import { KeyReferenceMySuffixDeletePopupComponent } from './key-reference-my-suffix-delete-dialog.component';

export const keyReferenceRoute: Routes = [
    {
        path: 'key-reference-my-suffix',
        component: KeyReferenceMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'KeyReferences'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'key-reference-my-suffix/:id',
        component: KeyReferenceMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'KeyReferences'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const keyReferencePopupRoute: Routes = [
    {
        path: 'key-reference-my-suffix-new',
        component: KeyReferenceMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'KeyReferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'key-reference-my-suffix/:id/edit',
        component: KeyReferenceMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'KeyReferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'key-reference-my-suffix/:id/delete',
        component: KeyReferenceMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'KeyReferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
