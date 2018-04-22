import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    DocentiService,
    DocentiPopupService,
    DocentiComponent,
    DocentiDetailComponent,
    DocentiDialogComponent,
    DocentiPopupComponent,
    DocentiDeletePopupComponent,
    DocentiDeleteDialogComponent,
    docentiRoute,
    docentiPopupRoute,
    DocentiResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...docentiRoute,
    ...docentiPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DocentiComponent,
        DocentiDetailComponent,
        DocentiDialogComponent,
        DocentiDeleteDialogComponent,
        DocentiPopupComponent,
        DocentiDeletePopupComponent,
    ],
    entryComponents: [
        DocentiComponent,
        DocentiDialogComponent,
        DocentiPopupComponent,
        DocentiDeleteDialogComponent,
        DocentiDeletePopupComponent,
    ],
    providers: [
        DocentiService,
        DocentiPopupService,
        DocentiResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriDocentiModule {}
