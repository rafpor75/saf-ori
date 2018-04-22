import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    CdlService,
    CdlPopupService,
    CdlComponent,
    CdlDetailComponent,
    CdlDialogComponent,
    CdlPopupComponent,
    CdlDeletePopupComponent,
    CdlDeleteDialogComponent,
    cdlRoute,
    cdlPopupRoute,
    CdlResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...cdlRoute,
    ...cdlPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CdlComponent,
        CdlDetailComponent,
        CdlDialogComponent,
        CdlDeleteDialogComponent,
        CdlPopupComponent,
        CdlDeletePopupComponent,
    ],
    entryComponents: [
        CdlComponent,
        CdlDialogComponent,
        CdlPopupComponent,
        CdlDeleteDialogComponent,
        CdlDeletePopupComponent,
    ],
    providers: [
        CdlService,
        CdlPopupService,
        CdlResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriCdlModule {}
