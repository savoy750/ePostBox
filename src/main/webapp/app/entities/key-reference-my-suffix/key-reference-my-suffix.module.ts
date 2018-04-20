import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPostBoxSharedModule } from '../../shared';
import {
    KeyReferenceMySuffixService,
    KeyReferenceMySuffixPopupService,
    KeyReferenceMySuffixComponent,
    KeyReferenceMySuffixDetailComponent,
    KeyReferenceMySuffixDialogComponent,
    KeyReferenceMySuffixPopupComponent,
    KeyReferenceMySuffixDeletePopupComponent,
    KeyReferenceMySuffixDeleteDialogComponent,
    keyReferenceRoute,
    keyReferencePopupRoute,
} from './';

const ENTITY_STATES = [
    ...keyReferenceRoute,
    ...keyReferencePopupRoute,
];

@NgModule({
    imports: [
        EPostBoxSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        KeyReferenceMySuffixComponent,
        KeyReferenceMySuffixDetailComponent,
        KeyReferenceMySuffixDialogComponent,
        KeyReferenceMySuffixDeleteDialogComponent,
        KeyReferenceMySuffixPopupComponent,
        KeyReferenceMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        KeyReferenceMySuffixComponent,
        KeyReferenceMySuffixDialogComponent,
        KeyReferenceMySuffixPopupComponent,
        KeyReferenceMySuffixDeleteDialogComponent,
        KeyReferenceMySuffixDeletePopupComponent,
    ],
    providers: [
        KeyReferenceMySuffixService,
        KeyReferenceMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EPostBoxKeyReferenceMySuffixModule {}
