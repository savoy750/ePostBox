import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EPostBoxKeyReferenceMySuffixModule } from './key-reference-my-suffix/key-reference-my-suffix.module';
import { EPostBoxDataReferenceMySuffixModule } from './data-reference-my-suffix/data-reference-my-suffix.module';
import { EPostBoxRejectedRegistrationMySuffixModule } from './rejected-registration-my-suffix/rejected-registration-my-suffix.module';
import { EPostBoxDocumentsSendMySuffixModule } from './documents-send-my-suffix/documents-send-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EPostBoxKeyReferenceMySuffixModule,
        EPostBoxDataReferenceMySuffixModule,
        EPostBoxRejectedRegistrationMySuffixModule,
        EPostBoxDocumentsSendMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EPostBoxEntityModule {}
