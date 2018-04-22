import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    FacoltaService,
    FacoltaPopupService,
    FacoltaComponent,
    FacoltaDetailComponent,
    FacoltaDialogComponent,
    FacoltaPopupComponent,
    FacoltaDeletePopupComponent,
    FacoltaDeleteDialogComponent,
    facoltaRoute,
    facoltaPopupRoute,
    FacoltaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...facoltaRoute,
    ...facoltaPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FacoltaComponent,
        FacoltaDetailComponent,
        FacoltaDialogComponent,
        FacoltaDeleteDialogComponent,
        FacoltaPopupComponent,
        FacoltaDeletePopupComponent,
    ],
    entryComponents: [
        FacoltaComponent,
        FacoltaDialogComponent,
        FacoltaPopupComponent,
        FacoltaDeleteDialogComponent,
        FacoltaDeletePopupComponent,
    ],
    providers: [
        FacoltaService,
        FacoltaPopupService,
        FacoltaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriFacoltaModule {}
