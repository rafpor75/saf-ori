import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    TutorService,
    TutorPopupService,
    TutorComponent,
    TutorDetailComponent,
    TutorDialogComponent,
    TutorPopupComponent,
    TutorDeletePopupComponent,
    TutorDeleteDialogComponent,
    tutorRoute,
    tutorPopupRoute,
    TutorResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...tutorRoute,
    ...tutorPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TutorComponent,
        TutorDetailComponent,
        TutorDialogComponent,
        TutorDeleteDialogComponent,
        TutorPopupComponent,
        TutorDeletePopupComponent,
    ],
    entryComponents: [
        TutorComponent,
        TutorDialogComponent,
        TutorPopupComponent,
        TutorDeleteDialogComponent,
        TutorDeletePopupComponent,
    ],
    providers: [
        TutorService,
        TutorPopupService,
        TutorResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriTutorModule {}
