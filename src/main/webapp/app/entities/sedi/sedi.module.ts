import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    SediService,
    SediPopupService,
    SediComponent,
    SediDetailComponent,
    SediDialogComponent,
    SediPopupComponent,
    SediDeletePopupComponent,
    SediDeleteDialogComponent,
    sediRoute,
    sediPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sediRoute,
    ...sediPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SediComponent,
        SediDetailComponent,
        SediDialogComponent,
        SediDeleteDialogComponent,
        SediPopupComponent,
        SediDeletePopupComponent,
    ],
    entryComponents: [
        SediComponent,
        SediDialogComponent,
        SediPopupComponent,
        SediDeleteDialogComponent,
        SediDeletePopupComponent,
    ],
    providers: [
        SediService,
        SediPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriSediModule {}
