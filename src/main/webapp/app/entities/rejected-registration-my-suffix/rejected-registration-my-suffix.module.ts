import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPostBoxSharedModule } from '../../shared';
import {
    RejectedRegistrationMySuffixService,
    RejectedRegistrationMySuffixPopupService,
    RejectedRegistrationMySuffixComponent,
    RejectedRegistrationMySuffixDetailComponent,
    RejectedRegistrationMySuffixDialogComponent,
    RejectedRegistrationMySuffixPopupComponent,
    RejectedRegistrationMySuffixDeletePopupComponent,
    RejectedRegistrationMySuffixDeleteDialogComponent,
    rejectedRegistrationRoute,
    rejectedRegistrationPopupRoute,
    RejectedRegistrationMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rejectedRegistrationRoute,
    ...rejectedRegistrationPopupRoute,
];

@NgModule({
    imports: [
        EPostBoxSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RejectedRegistrationMySuffixComponent,
        RejectedRegistrationMySuffixDetailComponent,
        RejectedRegistrationMySuffixDialogComponent,
        RejectedRegistrationMySuffixDeleteDialogComponent,
        RejectedRegistrationMySuffixPopupComponent,
        RejectedRegistrationMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        RejectedRegistrationMySuffixComponent,
        RejectedRegistrationMySuffixDialogComponent,
        RejectedRegistrationMySuffixPopupComponent,
        RejectedRegistrationMySuffixDeleteDialogComponent,
        RejectedRegistrationMySuffixDeletePopupComponent,
    ],
    providers: [
        RejectedRegistrationMySuffixService,
        RejectedRegistrationMySuffixPopupService,
        RejectedRegistrationMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EPostBoxRejectedRegistrationMySuffixModule {}
