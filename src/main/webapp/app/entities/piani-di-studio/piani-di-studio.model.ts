import { BaseEntity } from './../../shared';

export class PianiDiStudio implements BaseEntity {
    constructor(
        public id?: number,
        public abilitato?: boolean,
        public dataModifica?: any,
        public relAnnoAccademicoId?: number,
        public relPdsCdlId?: number,
        public relPdsMats?: BaseEntity[],
    ) {
        this.abilitato = false;
    }
}
