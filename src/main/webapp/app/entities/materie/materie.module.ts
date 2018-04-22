import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    MaterieService,
    MateriePopupService,
    MaterieComponent,
    MaterieDetailComponent,
    MaterieDialogComponent,
    MateriePopupComponent,
    MaterieDeletePopupComponent,
    MaterieDeleteDialogComponent,
    materieRoute,
    materiePopupRoute,
    MaterieResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...materieRoute,
    ...materiePopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MaterieComponent,
        MaterieDetailComponent,
        MaterieDialogComponent,
        MaterieDeleteDialogComponent,
        MateriePopupComponent,
        MaterieDeletePopupComponent,
    ],
    entryComponents: [
        MaterieComponent,
        MaterieDialogComponent,
        MateriePopupComponent,
        MaterieDeleteDialogComponent,
        MaterieDeletePopupComponent,
    ],
    providers: [
        MaterieService,
        MateriePopupService,
        MaterieResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriMaterieModule {}
