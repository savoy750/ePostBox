import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPostBoxSharedModule } from '../../shared';
import {
    DocumentsSendMySuffixService,
    DocumentsSendMySuffixPopupService,
    DocumentsSendMySuffixComponent,
    DocumentsSendMySuffixDetailComponent,
    DocumentsSendMySuffixDialogComponent,
    DocumentsSendMySuffixPopupComponent,
    DocumentsSendMySuffixDeletePopupComponent,
    DocumentsSendMySuffixDeleteDialogComponent,
    documentsSendRoute,
    documentsSendPopupRoute,
} from './';

const ENTITY_STATES = [
    ...documentsSendRoute,
    ...documentsSendPopupRoute,
];

@NgModule({
    imports: [
        EPostBoxSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DocumentsSendMySuffixComponent,
        DocumentsSendMySuffixDetailComponent,
        DocumentsSendMySuffixDialogComponent,
        DocumentsSendMySuffixDeleteDialogComponent,
        DocumentsSendMySuffixPopupComponent,
        DocumentsSendMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        DocumentsSendMySuffixComponent,
        DocumentsSendMySuffixDialogComponent,
        DocumentsSendMySuffixPopupComponent,
        DocumentsSendMySuffixDeleteDialogComponent,
        DocumentsSendMySuffixDeletePopupComponent,
    ],
    providers: [
        DocumentsSendMySuffixService,
        DocumentsSendMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EPostBoxDocumentsSendMySuffixModule {}
