import { BaseEntity } from './../../shared';

export class Docenti implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public cognome?: string,
        public email?: string,
        public abilitato?: boolean,
        public relDocMats?: BaseEntity[],
    ) {
        this.abilitato = false;
    }
}
