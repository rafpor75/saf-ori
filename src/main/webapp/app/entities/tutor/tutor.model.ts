import { BaseEntity } from './../../shared';

export class Tutor implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public cognome?: string,
        public email?: string,
        public abilitato?: boolean,
        public relTutMats?: BaseEntity[],
    ) {
        this.abilitato = false;
    }
}
