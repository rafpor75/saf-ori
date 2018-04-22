import { BaseEntity } from './../../shared';

export class Esami implements BaseEntity {
    constructor(
        public id?: number,
        public data?: any,
        public relEsamiSediSede?: string,
        public relEsamiSediId?: number,
        public relMatEsamiId?: number,
    ) {
    }
}
