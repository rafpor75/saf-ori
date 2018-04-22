import { BaseEntity } from './../../shared';

export class NoteEsame implements BaseEntity {
    constructor(
        public id?: number,
        public appunti?: string,
        public dataDispensa?: any,
        public dataCorsi?: any,
        public dataABI?: any,
        public dataRiepilogo?: any,
        public oraEsame?: any,
        public costoEsame?: number,
        public fasce?: string,
        public piva?: string,
        public fattura?: boolean,
        public noteFattura?: string,
        public emailInviata?: boolean,
        public relNoteStudId?: number,
        public relNoteEsamiId?: number,
    ) {
        this.fattura = false;
        this.emailInviata = false;
    }
}
