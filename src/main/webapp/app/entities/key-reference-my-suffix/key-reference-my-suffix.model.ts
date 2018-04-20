import { BaseEntity } from './../../shared';

export class KeyReferenceMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public internalKey?: string,
        public ePostKeyName?: string,
        public ePostKeyValue?: string,
        public internalKeyId?: number,
    ) {
    }
}
