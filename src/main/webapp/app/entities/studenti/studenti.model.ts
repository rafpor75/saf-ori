import { BaseEntity } from './../../shared';

export class Studenti implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public cognome?: string,
        public email?: string,
        public telefono?: string,
        public indirizzo?: string,
        public citta?: string,
        public provincia?: string,
        public stato?: string,
        public cap?: string,
        public dataDiNascita?: any,
        public luogoDiNascita?: string,
        public matricola?: string,
        public tempoParziale?: boolean,
        public abilitato?: boolean,
        public dataModifica?: any,
        public relStuCdlId?: number,
    ) {
        this.tempoParziale = false;
        this.abilitato = false;
    }
}
