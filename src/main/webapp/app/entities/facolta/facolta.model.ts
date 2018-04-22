import { BaseEntity } from './../../shared';

export class Facolta implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public dataModifica?: any,
        public abilitato?: boolean,
        public relFacCdls?: BaseEntity[],
    ) {
        this.abilitato = false;
    }
}
