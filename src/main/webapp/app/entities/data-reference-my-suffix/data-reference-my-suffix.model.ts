import { BaseEntity } from './../../shared';

export class DataReferenceMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public internalKey?: string,
        public noAVS?: string,
        public nom?: string,
        public prenom?: string,
        public dateDeNaissance?: any,
    ) {
    }
}
