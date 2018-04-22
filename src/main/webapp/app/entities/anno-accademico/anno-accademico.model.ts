import { BaseEntity } from './../../shared';

export class AnnoAccademico implements BaseEntity {
    constructor(
        public id?: number,
        public descrizione?: string,
    ) {
    }
}
