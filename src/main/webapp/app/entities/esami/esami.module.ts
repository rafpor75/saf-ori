import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    EsamiService,
    EsamiPopupService,
    EsamiComponent,
    EsamiDetailComponent,
    EsamiDialogComponent,
    EsamiPopupComponent,
    EsamiDeletePopupComponent,
    EsamiDeleteDialogComponent,
    esamiRoute,
    esamiPopupRoute,
    EsamiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...esamiRoute,
    ...esamiPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EsamiComponent,
        EsamiDetailComponent,
        EsamiDialogComponent,
        EsamiDeleteDialogComponent,
        EsamiPopupComponent,
        EsamiDeletePopupComponent,
    ],
    entryComponents: [
        EsamiComponent,
        EsamiDialogComponent,
        EsamiPopupComponent,
        EsamiDeleteDialogComponent,
        EsamiDeletePopupComponent,
    ],
    providers: [
        EsamiService,
        EsamiPopupService,
        EsamiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriEsamiModule {}
