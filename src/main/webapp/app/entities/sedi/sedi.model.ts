import { BaseEntity } from './../../shared';

export class Sedi implements BaseEntity {
    constructor(
        public id?: number,
        public sede?: string,
    ) {
    }
}
