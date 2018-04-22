import { BaseEntity } from './../../shared';

export class Materie implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public cfu?: number,
        public abilitato?: boolean,
        public dataModifica?: any,
        public relMatsCdlNome?: string,
        public relMatsCdlId?: number,
        public relMatsTutCognome?: string,
        public relMatsTutId?: number,
        public relMatsDocCognome?: string,
        public relMatsDocId?: number,
    ) {
        this.abilitato = false;
    }
}
