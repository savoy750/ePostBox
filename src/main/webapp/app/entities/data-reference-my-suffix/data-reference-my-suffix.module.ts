import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EPostBoxSharedModule } from '../../shared';
import {
    DataReferenceMySuffixService,
    DataReferenceMySuffixPopupService,
    DataReferenceMySuffixComponent,
    DataReferenceMySuffixDetailComponent,
    DataReferenceMySuffixDialogComponent,
    DataReferenceMySuffixPopupComponent,
    DataReferenceMySuffixDeletePopupComponent,
    DataReferenceMySuffixDeleteDialogComponent,
    dataReferenceRoute,
    dataReferencePopupRoute,
} from './';

const ENTITY_STATES = [
    ...dataReferenceRoute,
    ...dataReferencePopupRoute,
];

@NgModule({
    imports: [
        EPostBoxSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DataReferenceMySuffixComponent,
        DataReferenceMySuffixDetailComponent,
        DataReferenceMySuffixDialogComponent,
        DataReferenceMySuffixDeleteDialogComponent,
        DataReferenceMySuffixPopupComponent,
        DataReferenceMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        DataReferenceMySuffixComponent,
        DataReferenceMySuffixDialogComponent,
        DataReferenceMySuffixPopupComponent,
        DataReferenceMySuffixDeleteDialogComponent,
        DataReferenceMySuffixDeletePopupComponent,
    ],
    providers: [
        DataReferenceMySuffixService,
        DataReferenceMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EPostBoxDataReferenceMySuffixModule {}
