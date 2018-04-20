import { BaseEntity } from './../../shared';

export class RejectedRegistrationMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public ePostKeyName?: string,
        public ePostKeyValue?: string,
        public noAVS?: string,
        public nom?: string,
        public prenom?: string,
        public dateDeNaissance?: any,
    ) {
    }
}
