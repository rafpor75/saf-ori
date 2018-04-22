import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    StudentiService,
    StudentiPopupService,
    StudentiComponent,
    StudentiDetailComponent,
    StudentiDialogComponent,
    StudentiPopupComponent,
    StudentiDeletePopupComponent,
    StudentiDeleteDialogComponent,
    studentiRoute,
    studentiPopupRoute,
    StudentiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...studentiRoute,
    ...studentiPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StudentiComponent,
        StudentiDetailComponent,
        StudentiDialogComponent,
        StudentiDeleteDialogComponent,
        StudentiPopupComponent,
        StudentiDeletePopupComponent,
    ],
    entryComponents: [
        StudentiComponent,
        StudentiDialogComponent,
        StudentiPopupComponent,
        StudentiDeleteDialogComponent,
        StudentiDeletePopupComponent,
    ],
    providers: [
        StudentiService,
        StudentiPopupService,
        StudentiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriStudentiModule {}
