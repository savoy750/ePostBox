import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DataReferenceMySuffixComponent } from './data-reference-my-suffix.component';
import { DataReferenceMySuffixDetailComponent } from './data-reference-my-suffix-detail.component';
import { DataReferenceMySuffixPopupComponent } from './data-reference-my-suffix-dialog.component';
import { DataReferenceMySuffixDeletePopupComponent } from './data-reference-my-suffix-delete-dialog.component';

export const dataReferenceRoute: Routes = [
    {
        path: 'data-reference-my-suffix',
        component: DataReferenceMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataReferences'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'data-reference-my-suffix/:id',
        component: DataReferenceMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataReferences'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataReferencePopupRoute: Routes = [
    {
        path: 'data-reference-my-suffix-new',
        component: DataReferenceMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataReferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-reference-my-suffix/:id/edit',
        component: DataReferenceMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataReferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-reference-my-suffix/:id/delete',
        component: DataReferenceMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DataReferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
