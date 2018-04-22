import { BaseEntity } from './../../shared';

export class Cdl implements BaseEntity {
    constructor(
        public id?: number,
        public codice?: string,
        public nome?: string,
        public abilitato?: boolean,
        public relCdlMats?: BaseEntity[],
        public relCdlsFacNome?: string,
        public relCdlsFacId?: number,
    ) {
        this.abilitato = false;
    }
}
