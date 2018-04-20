import { BaseEntity } from './../../shared';

export const enum Status {
    'CREATED',
    'ERROR'
}

export class DocumentsSendMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public correlationId?: string,
        public documentType?: number,
        public tag?: string,
        public internalKey?: string,
        public message?: string,
        public status?: Status,
        public internalKeyId?: number,
    ) {
    }
}
